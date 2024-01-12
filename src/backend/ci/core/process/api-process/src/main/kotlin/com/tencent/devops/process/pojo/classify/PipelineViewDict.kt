package com.tencent.devops.process.pojo.classify

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "流水线组与流水线的对应关系")
data class PipelineViewDict(
    @Schema(name = "个人流水线组列表")
    val personalViewList: List<ViewInfo>,
    @Schema(name = "项目流水线列表")
    val projectViewList: List<ViewInfo>
) {
    @Schema(name = "流水线组信息")
    data class ViewInfo(
        @Schema(name = "流水线组ID")
        val viewId: String,
        @Schema(name = "流水线组名")
        val viewName: String,
        @Schema(name = "流水线列表")
        val pipelineList: List<PipelineInfo>
    ) {
        @Schema(name = "流水线信息")
        data class PipelineInfo(
            @Schema(name = "流水线ID")
            val pipelineId: String,
            @Schema(name = "流水线名称")
            val pipelineName: String,
            @Schema(name = "流水线组ID")
            val viewId: String,
            @Schema(name = "是否删除")
            val delete: Boolean
        )
    }

    companion object {
        val EMPTY = PipelineViewDict(emptyList(), emptyList())
    }
}
