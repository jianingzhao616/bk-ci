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

package com.tencent.devops.repository.pojo

import com.tencent.devops.common.api.enums.ScmType
import io.swagger.v3.oas.annotations.media.Schema

@SuppressWarnings("ObjectPropertyNaming")
@Schema(name = "代码库模型-GitHub代码库")
data class GithubRepository(
    @Schema(name = "代码库别名", required = true)
    override val aliasName: String,
    @Schema(name = "URL", required = true)
    override val url: String,
    @Schema(name = "用户名", required = true)
    override var userName: String = "",
    @Schema(name = "github项目名称", example = "Tencent/bkci", required = true)
    override val projectName: String,
    @Schema(name = "项目id", required = true)
    override val projectId: String = "",
    @Schema(name = "仓库hash id", required = false)
    override val repoHashId: String?,
    @Schema(name = "Git仓库ID", required = false)
    val gitProjectId: Long? = null,
    @Schema(name = "仓库凭证ID", required = false, hidden = true, nullable = true)
    override val credentialId: String = ""
) : Repository {
    companion object {
        const val classType = "github"
    }

    override fun getStartPrefix() = "https://github.com/"

    override fun getScmType() = ScmType.GITHUB

    override fun getExternalId(): String = gitProjectId?.toString() ?: ""
}
