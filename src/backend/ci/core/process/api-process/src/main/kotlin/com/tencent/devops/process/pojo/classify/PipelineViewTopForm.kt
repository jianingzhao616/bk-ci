package com.tencent.devops.process.pojo.classify

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "流水线视图表单")
data class PipelineViewTopForm(
    @Schema(name = "是否生效", required = true)
    val enabled: Boolean
)
