package com.tencent.devops.process.pojo.pipeline

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "流水线数量相关")
data class PipelineCount(
    @Schema(name = "全部流水线个数", required = true)
    var totalCount: Int,
    @Schema(name = "我的收藏个数", required = true)
    var myFavoriteCount: Int,
    @Schema(name = "我的流水线的个数", required = true)
    var myPipelineCount: Int,
    @Schema(name = "回收站流水线的个数", required = true)
    var recycleCount: Int,
    @Schema(name = "最近使用的流水线的个数", required = true)
    val recentUseCount: Int
)
