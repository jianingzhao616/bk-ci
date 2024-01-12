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

package com.tencent.devops.store.pojo.common

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "logo信息")
data class Logo(
    @Schema(name = "logoID", required = true)
    val id: String,
    @Schema(name = "logo链接", required = true)
    val logoUrl: String,
    @Schema(name = "类别 ATOM:原子 TEMPLATE:模板 BANNER:banner", required = true)
    val logoType: String,
    @Schema(name = "展示顺序", required = true)
    val order: Int,
    @Schema(name = "点击logo后的跳转链接")
    val link: String?,
    @Schema(name = "创建日期")
    val createTime: String,
    @Schema(name = "更新日期")
    val updateTime: String,
    @Schema(name = "创建人", required = true)
    val creator: String,
    @Schema(name = "最近修改人", required = true)
    val modifier: String
)
