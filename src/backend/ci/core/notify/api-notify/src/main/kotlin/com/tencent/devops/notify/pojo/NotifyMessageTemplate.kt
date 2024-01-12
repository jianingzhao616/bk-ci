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
package com.tencent.devops.notify.pojo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "消息通知")
data class NotifyMessageTemplate(
    @Schema(name = "ID", required = true)
    val id: String,
    @Schema(name = "模板代码", required = true)
    val templateCode: String,
    @Schema(name = "模板名称", required = true)
    val templateName: String,
    @Schema(name = "适用的通知类型（EMAIL:邮件 RTX:企业微信 WECHAT:微信 SMS:短信）", required = true)
    val notifyTypeScope: List<String>,
    @Schema(name = "标题（邮件和RTX方式必填）", required = false)
    val title: String? = "",
    @Schema(name = "消息内容", required = true)
    val body: String,
    @Schema(name = "优先级别", required = true)
    val priority: String,
    @Schema(name = "通知来源", required = true)
    val source: Int,
    @Schema(name = "邮件格式（邮件方式必填）", required = false)
    val bodyFormat: Int? = null,
    @Schema(name = "邮件类型（邮件方式必填）", required = false)
    val emailType: Int? = null,
    @Schema(name = "创建人", required = true)
    val creator: String,
    @Schema(name = "修改人", required = true)
    val modifier: String,
    @Schema(name = "创建日期", required = true)
    val createTime: Long = 0,
    @Schema(name = "更新日期", required = true)
    val updateTime: Long = 0
)
