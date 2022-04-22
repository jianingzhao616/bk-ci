/*
 * Tencent is pleased to support the open source community by making BK-CI 蓝鲸持续集成平台 available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * BK-CI 蓝鲸持续集成平台 is licensed under the MIT license.
 *
 * A copy of the MIT License is included in this file.
 *
 *
 * Terms of the MIT License:
 * ---------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tencent.devops.stream.trigger.service

import com.tencent.devops.common.api.enums.RepositoryConfig
import com.tencent.devops.common.api.enums.RepositoryType
import com.tencent.devops.common.api.enums.ScmType
import com.tencent.devops.common.client.Client
import com.tencent.devops.common.pipeline.enums.ChannelCode
import com.tencent.devops.common.redis.RedisLock
import com.tencent.devops.common.redis.RedisOperation
import com.tencent.devops.process.api.service.ServiceBuildResource
import com.tencent.devops.process.utils.PIPELINE_BUILD_NUM
import com.tencent.devops.repository.api.ServiceRepositoryGitCheckResource
import com.tencent.devops.repository.api.scm.ServiceScmOauthResource
import com.tencent.devops.repository.pojo.ExecuteSource
import com.tencent.devops.repository.pojo.RepositoryGitCheck
import com.tencent.devops.scm.pojo.CommitCheckRequest
import com.tencent.devops.stream.util.GitCommonUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GitCheckService @Autowired constructor(
    private val client: Client,
    private val redisOperation: RedisOperation
) {

    companion object {
        private val logger = LoggerFactory.getLogger(GitCheckService::class.java)
    }

    fun pushCommitCheck(
        userId: String,
        projectCode: String,
        buildId: String,
        gitProjectId: String,
        gitProjectName: String,
        pipelineId: String,
        commitId: String,
        gitHttpUrl: String,
        scmType: ScmType,
        token: String,
        state: String,
        block: Boolean,
        context: String,
        targetUrl: String,
        description: String,
        mrId: Long?,
        reportData: Pair<List<String>, MutableMap<String, MutableList<List<String>>>>
    ) {
        logger.info(
            "Code web hook add commit check [projectId=$projectCode, pipelineId=$pipelineId, buildId=$buildId, " +
                " commitId=$commitId, state=$state, block=$block]"
        )

        val buildHistoryResult = client.get(ServiceBuildResource::class).getBuildVars(
            userId = userId,
            projectId = projectCode,
            pipelineId = pipelineId,
            buildId = buildId,
            channelCode = ChannelCode.GIT
        )
        if (buildHistoryResult.isNotOk() || buildHistoryResult.data == null) {
            logger.warn("Process instance($buildId) not exist: ${buildHistoryResult.message}")
            return
        }
        val buildInfo = buildHistoryResult.data!!

        val variables = buildInfo.variables
        if (variables.isEmpty()) {
            logger.warn("Process instance($buildId) variables is empty")
            return
        }

        val buildNum = variables[PIPELINE_BUILD_NUM]

        if (buildNum == null) {
            logger.warn("Build($buildId) number is null")
            return
        }

        tryAddCommitCheck(
            gitProjectId = gitProjectId,
            gitProjectName = gitProjectName,
            pipelineId = pipelineId,
            commitId = commitId,
            gitHttpUrl = gitHttpUrl,
            scmType = scmType,
            token = token,
            state = state,
            block = block,
            context = context,
            targetUrl = targetUrl,
            description = description,
            mrId = mrId,
            buildNum = buildNum,
            reportData = reportData
        )
    }

    @Suppress("NestedBlockDepth")
    private fun tryAddCommitCheck(
        gitProjectId: String,
        gitProjectName: String,
        pipelineId: String,
        commitId: String,
        gitHttpUrl: String,
        scmType: ScmType,
        token: String,
        state: String,
        block: Boolean,
        context: String,
        targetUrl: String,
        description: String,
        mrId: Long?,
        buildNum: String,
        reportData: Pair<List<String>, MutableMap<String, MutableList<List<String>>>>
    ) {
        val gitCheckClient = client.get(ServiceRepositoryGitCheckResource::class)

        val repositoryConfig = RepositoryConfig(
            repositoryHashId = gitProjectId,
            repositoryName = null,
            repositoryType = RepositoryType.ID
        )

        while (true) {
            val lockKey = "code_git_commit_check_lock_$pipelineId"
            val redisLock = RedisLock(redisOperation, lockKey, 60)
            redisLock.use {
                if (!redisLock.tryLock()) {
                    logger.info("Code web hook commit check try lock($lockKey) fail")
                    Thread.sleep(100)
                    return@use
                }

                val record = gitCheckClient.getGitCheck(
                    pipelineId = pipelineId,
                    repositoryConfig = repositoryConfig,
                    commitId = commitId,
                    context = context
                ).data

                if (record == null) {
                    addCommitCheck(
                        gitProjectId = gitProjectId,
                        commitId = commitId,
                        gitHttpUrl = gitHttpUrl,
                        scmType = scmType,
                        token = token,
                        state = state,
                        block = block,
                        targetUrl = targetUrl,
                        context = context,
                        description = description,
                        mrId = mrId
                    )
                    gitCheckClient.createGitCheck(
                        gitCheck = RepositoryGitCheck(
                            gitCheckId = -1,
                            pipelineId = pipelineId,
                            buildNumber = buildNum.toInt(),
                            repositoryId = gitProjectId,
                            repositoryName = getProjectName(gitProjectId, gitProjectName),
                            commitId = commitId,
                            context = context,
                            source = ExecuteSource.STREAM
                        )
                    )
                } else {
                    if (buildNum.toInt() >= record.buildNumber) {
                        addCommitCheck(
                            gitProjectId = gitProjectId,
                            commitId = commitId,
                            gitHttpUrl = gitHttpUrl,
                            scmType = scmType,
                            token = token,
                            state = state,
                            block = block,
                            targetUrl = targetUrl,
                            context = record.context,
                            description = description,
                            mrId = mrId,
                            reportData = reportData
                        )
                        gitCheckClient.updateGitCheck(
                            gitCheckId = record.gitCheckId,
                            buildNumber = buildNum.toInt()
                        )
                    } else {
                        logger.info("Code web hook commit check has bigger build number(${record.buildNumber})")
                    }
                }
                return
            }
        }
    }

    fun addCommitCheck(
        gitProjectId: String,
        commitId: String,
        gitHttpUrl: String,
        scmType: ScmType,
        token: String,
        state: String,
        block: Boolean,
        targetUrl: String,
        context: String,
        description: String,
        mrId: Long?,
        reportData: Pair<List<String>, MutableMap<String, MutableList<List<String>>>> = Pair(listOf(), mutableMapOf())
    ) {
        logger.info("Project($$gitProjectId) add git commit($commitId) commit check.")

        val request = CommitCheckRequest(
            projectName = gitProjectId,
            url = gitHttpUrl,
            type = scmType,
            privateKey = null,
            passPhrase = null,
            token = token,
            region = null,
            commitId = commitId,
            state = state,
            targetUrl = targetUrl,
            context = context,
            description = description,
            block = block,
            mrRequestId = mrId,
            reportData = reportData
        )
        client.get(ServiceScmOauthResource::class).addCommitCheck(request)
    }

    private fun getProjectName(gitHttpUrl: String, name: String): String {
        return try {
            GitCommonUtils.getRepoName(
                httpUrl = gitHttpUrl,
                name = name
            )
        } catch (e: java.lang.Exception) {
            name
        }
    }
}