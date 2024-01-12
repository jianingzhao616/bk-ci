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

package com.tencent.devops.process.pojo.setting

import com.tencent.devops.common.api.exception.InvalidParamException
import com.tencent.devops.common.api.pojo.PipelineAsCodeSettings
import com.tencent.devops.common.web.annotation.BkField
import com.tencent.devops.common.web.constant.BkStyleEnum
import com.tencent.devops.common.web.utils.I18nUtil
import com.tencent.devops.process.constant.ProcessMessageCode.MAXIMUM_NUMBER_CONCURRENCY_ILLEGAL
import com.tencent.devops.process.constant.ProcessMessageCode.MAXIMUM_NUMBER_QUEUES_ILLEGAL
import com.tencent.devops.process.constant.ProcessMessageCode.MAXIMUM_QUEUE_LENGTH_ILLEGAL
import com.tencent.devops.process.constant.ProcessMessageCode.PIPELINE_ORCHESTRATIONS_NUMBER_ILLEGAL
import com.tencent.devops.process.utils.PIPELINE_RES_NUM_MIN
import com.tencent.devops.process.utils.PIPELINE_SETTING_CONCURRENCY_GROUP_DEFAULT
import com.tencent.devops.process.utils.PIPELINE_SETTING_MAX_CON_QUEUE_SIZE_MAX
import com.tencent.devops.process.utils.PIPELINE_SETTING_MAX_QUEUE_SIZE_DEFAULT
import com.tencent.devops.process.utils.PIPELINE_SETTING_MAX_QUEUE_SIZE_MAX
import com.tencent.devops.process.utils.PIPELINE_SETTING_MAX_QUEUE_SIZE_MIN
import com.tencent.devops.process.utils.PIPELINE_SETTING_WAIT_QUEUE_TIME_MINUTE_DEFAULT
import com.tencent.devops.process.utils.PIPELINE_SETTING_WAIT_QUEUE_TIME_MINUTE_MAX
import com.tencent.devops.process.utils.PIPELINE_SETTING_WAIT_QUEUE_TIME_MINUTE_MIN
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "")
data class PipelineSetting(
    @Schema(name = "项目id", required = false, readOnly = true)
    var projectId: String = "",
    @Schema(name = "流水线id", required = false, readOnly = true)
    var pipelineId: String = "",
    @Schema(name = "流水线名称", required = false)
    var pipelineName: String = "",
    @Schema(name = "描述", required = false)
    val desc: String = "",
    @Schema(name = "Lock 类型", required = false)
    val runLockType: PipelineRunLockType = PipelineRunLockType.SINGLE_LOCK,
    @Schema(name = "订阅成功相关", required = false)
    var successSubscription: Subscription = Subscription(),
    @Schema(name = "订阅失败相关", required = false)
    var failSubscription: Subscription = Subscription(),
    @Schema(name = "标签列表", required = false)
    var labels: List<String> = emptyList(),
    @Schema(name = "最大排队时长", required = false)
    val waitQueueTimeMinute: Int = PIPELINE_SETTING_WAIT_QUEUE_TIME_MINUTE_DEFAULT,
    @Schema(name = "最大排队数量", required = false)
    val maxQueueSize: Int = PIPELINE_SETTING_MAX_QUEUE_SIZE_DEFAULT,
    @Schema(name = "并发时,设定的group", required = false)
    var concurrencyGroup: String? = PIPELINE_SETTING_CONCURRENCY_GROUP_DEFAULT,
    @Schema(name = "并发时,是否相同group取消正在执行的流水线", required = false)
    val concurrencyCancelInProgress: Boolean = false,
    @Schema(name = "是否有操作权限", required = false)
    var hasPermission: Boolean? = null,
    @Schema(name = "保存流水线编排的最大个数", required = false)
    val maxPipelineResNum: Int = PIPELINE_RES_NUM_MIN, // 保存流水线编排的最大个数
    @Schema(name = "并发构建数量限制", required = false)
    val maxConRunningQueueSize: Int? = PIPELINE_SETTING_MAX_CON_QUEUE_SIZE_MAX, // MULTIPLE类型时，并发构建数量限制
    @Schema(name = "版本", required = false)
    var version: Int = 0,
    @field:BkField(patternStyle = BkStyleEnum.BUILD_NUM_RULE_STYLE, required = false)
    @Schema(name = "构建号生成规则", required = false)
    val buildNumRule: String? = null, // 构建号生成规则
    @Schema(name = "重试时清理引擎变量表", required = false)
    val cleanVariablesWhenRetry: Boolean? = false,
    @Schema(name = "YAML流水线特殊配置", required = false)
    val pipelineAsCodeSettings: PipelineAsCodeSettings? = null
) {

    @Suppress("ALL")
    fun checkParam() {
        if (maxPipelineResNum < 1) {
            throw InvalidParamException(
                message = I18nUtil.getCodeLanMessage(PIPELINE_ORCHESTRATIONS_NUMBER_ILLEGAL),
                params = arrayOf("maxPipelineResNum")
            )
        }
        if (runLockType == PipelineRunLockType.SINGLE ||
            runLockType == PipelineRunLockType.SINGLE_LOCK || runLockType == PipelineRunLockType.GROUP_LOCK
        ) {
            if (waitQueueTimeMinute < PIPELINE_SETTING_WAIT_QUEUE_TIME_MINUTE_MIN ||
                waitQueueTimeMinute > PIPELINE_SETTING_WAIT_QUEUE_TIME_MINUTE_MAX
            ) {
                throw InvalidParamException(
                    I18nUtil.getCodeLanMessage(MAXIMUM_QUEUE_LENGTH_ILLEGAL),
                    params = arrayOf("waitQueueTimeMinute")
                )
            }
            if (maxQueueSize < PIPELINE_SETTING_MAX_QUEUE_SIZE_MIN ||
                maxQueueSize > PIPELINE_SETTING_MAX_QUEUE_SIZE_MAX
            ) {
                throw InvalidParamException(
                    I18nUtil.getCodeLanMessage(MAXIMUM_NUMBER_QUEUES_ILLEGAL),
                    params = arrayOf("maxQueueSize")
                )
            }
        }
        if (maxConRunningQueueSize != null && (
            maxConRunningQueueSize <= PIPELINE_SETTING_MAX_QUEUE_SIZE_MIN ||
                maxConRunningQueueSize > PIPELINE_SETTING_MAX_CON_QUEUE_SIZE_MAX
            )
        ) {
            throw InvalidParamException(
                I18nUtil.getCodeLanMessage(MAXIMUM_NUMBER_CONCURRENCY_ILLEGAL),
                params = arrayOf("maxConRunningQueueSize")
            )
        }
    }
}
