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

package com.tencent.devops.process.pojo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "流水线插件替换历史")
data class PipelineAtomReplaceHistory(
    @Schema(name = "项目ID", required = true)
    val projectId: String,
    @Schema(name = "业务ID", required = true)
    val busId: String,
    @Schema(name = "业务类型", required = true)
    val busType: String,
    @Schema(name = "源版本号", required = true)
    val sourceVersion: Int,
    @Schema(name = "目标版本号", required = false)
    val targetVersion: Int? = null,
    @Schema(name = "状态", required = true)
    val status: String,
    @Schema(name = "插件替换基本信息ID", required = true)
    val baseId: String,
    @Schema(name = "插件替换项信息ID", required = true)
    val itemId: String,
    @Schema(name = "用户ID", required = true)
    val userId: String,
    @Schema(name = "日志", required = false)
    val log: String? = null
)
