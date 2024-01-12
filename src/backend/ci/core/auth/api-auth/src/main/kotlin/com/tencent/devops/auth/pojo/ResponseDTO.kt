package com.tencent.devops.auth.pojo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "请求返回实体")
data class ResponseDTO<T>(
    @Schema(name = "返回码")
    val code: Long,
    @Schema(name = "返回信息")
    val message: String,
    @Schema(name = "请求返回结果")
    val result: Boolean,
    @Schema(name = "请求返回数据")
    val data: T?
)
