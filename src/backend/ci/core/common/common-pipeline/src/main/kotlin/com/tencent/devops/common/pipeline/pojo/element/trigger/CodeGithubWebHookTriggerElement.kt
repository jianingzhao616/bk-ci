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

package com.tencent.devops.common.pipeline.pojo.element.trigger

import com.tencent.devops.common.api.enums.RepositoryType
import com.tencent.devops.common.pipeline.enums.StartType
import com.tencent.devops.common.pipeline.pojo.element.ElementProp
import com.tencent.devops.common.pipeline.pojo.element.trigger.enums.CodeEventType
import com.tencent.devops.common.pipeline.utils.TriggerElementPropUtils
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Github事件触发", description = CodeGithubWebHookTriggerElement.classType)
data class CodeGithubWebHookTriggerElement(
    @Schema(name = "任务名称", required = true)
    override val name: String = "Git变更触发",
    @Schema(name = "id", required = false)
    override var id: String? = null,
    @Schema(name = "状态", required = false)
    override var status: String? = null,
    @Schema(name = "仓库ID", required = true)
    val repositoryHashId: String?,
    @Schema(name = "分支名称", required = false)
    val branchName: String?,
    @Schema(name = "用于排除的分支名称", required = false)
    val excludeBranchName: String?,
    @Schema(name = "用于排除的user id", required = false)
    val excludeUsers: String?,
    @Schema(name = "事件类型", required = false)
    val eventType: CodeEventType?,
    @Schema(name = "新版的github原子的类型")
    val repositoryType: RepositoryType? = null,
    @Schema(name = "新版的github代码库名")
    val repositoryName: String? = null,
    @Schema(name = "code review 状态", required = false)
    val includeCrState: List<String>? = null,
    @Schema(name = "code note comment", required = false)
    val includeNoteComment: String? = null,
    @Schema(name = "code note 类型", required = false)
    val includeNoteTypes: List<String>? = null,
    @Schema(name = "issue事件action")
    val includeIssueAction: List<String>? = null
) : WebHookTriggerElement(name, id, status) {
    companion object {
        const val classType = "codeGithubWebHookTrigger"
    }

    override fun getClassType() = classType

    override fun findFirstTaskIdByStartType(startType: StartType): String {
        return if (startType.name == StartType.WEB_HOOK.name) {
            this.id!!
        } else {
            super.findFirstTaskIdByStartType(startType)
        }
    }

    // 增加条件这里也要补充上,不然代码库触发器列表展示会不对
    override fun triggerCondition(): List<ElementProp> {
        val props = when (eventType) {
            CodeEventType.PUSH, CodeEventType.CREATE, CodeEventType.PULL_REQUEST -> {
                listOf(
                    TriggerElementPropUtils.vuexInput(name = "branchName", value = branchName),
                    TriggerElementPropUtils.vuexInput(name = "excludeBranchName", value = excludeBranchName),
                    TriggerElementPropUtils.vuexInput(name = "excludeUsers", value = excludeUsers)
                )
            }

            CodeEventType.REVIEW -> {
                listOf(
                    TriggerElementPropUtils.selector(name = "includeCrState", value = includeCrState)
                )
            }

            CodeEventType.ISSUES -> {
                listOf(
                    TriggerElementPropUtils.selector(name = "includeIssueAction", value = includeIssueAction)
                )
            }

            CodeEventType.NOTE -> {
                listOf(
                    TriggerElementPropUtils.selector(name = "includeNoteTypes", value = includeNoteTypes),
                    TriggerElementPropUtils.vuexInput(name = "includeNoteComment", value = includeNoteComment)
                )
            }

            else -> emptyList()
        }
        return props.filterNotNull()
    }
}
