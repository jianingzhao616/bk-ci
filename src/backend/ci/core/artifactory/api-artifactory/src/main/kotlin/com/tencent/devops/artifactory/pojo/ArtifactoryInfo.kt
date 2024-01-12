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

package com.tencent.devops.artifactory.pojo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "版本仓库-构建信息")
data class ArtifactoryInfo(
    @Schema(name = "流水线号", required = true)
    val pipelineId: String,
    @Schema(name = "项目ID", required = true)
    val projectId: String,
    @Schema(name = "构建号", required = true)
    val buildNum: Int,
    @Schema(name = "包ID", required = true)
    val bundleId: String,
    @Schema(name = "产物信息", required = true)
    val fileInfo: FileInfo?,
    @Schema(name = "包名", required = true)
    val name: String,
    @Schema(name = "包全名", required = true)
    val fullName: String,
    @Schema(name = "包大小", required = true)
    val size: Long,
    @Schema(name = "添加时间", required = true)
    val modifiedTime: Long,
    @Schema(name = "app版本", required = true)
    val appVersion: String? = null,
    @Schema(name = "数据来源：0-自然数据 1-补偿数据", required = true)
    val dataForm: Int
)
