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

package com.tencent.devops.metrics.pojo.po

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(name = "保存流水线概览数据")
data class SavePipelineOverviewDataPO(
    @Schema(name = "主键ID")
    val id: Long,
    @Schema(name = "项目ID")
    val projectId: String,
    @Schema(name = "流水线ID")
    val pipelineId: String,
    @Schema(name = "流水线名称")
    val pipelineName: String,
    @Schema(name = "渠道代码")
    val channelCode: String,
    @Schema(name = "总平均耗时，单位：毫秒")
    val totalAvgCostTime: Long,
    @Schema(name = "成功平均耗时，单位：毫秒")
    val successAvgCostTime: Long? = null,
    @Schema(name = "失败平均耗时，单位：毫秒")
    val failAvgCostTime: Long? = null,
    @Schema(name = "总执行次数")
    val totalExecuteCount: Long,
    @Schema(name = "成功执行次数")
    val successExecuteCount: Long,
    @Schema(name = "失败执行次数")
    val failExecuteCount: Long,
    @Schema(name = "统计时间")
    val statisticsTime: LocalDateTime,
    @Schema(name = "创建人")
    val creator: String,
    @Schema(name = "修改人")
    val modifier: String,
    @Schema(name = "创建时间")
    val createTime: LocalDateTime,
    @Schema(name = "更新时间")
    val updateTime: LocalDateTime
)
