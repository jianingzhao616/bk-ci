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

package com.tencent.devops.project.pojo

import com.tencent.devops.common.auth.api.pojo.SubjectScopeInfo
import com.tencent.devops.project.pojo.enums.ProjectTipsStatus
import io.swagger.v3.oas.annotations.media.Schema

@Suppress("ALL")
@Schema(name = "项目-显示模型")
data class ProjectVO(
    @Schema(name = "主键ID")
    val id: Long,
    @Schema(name = "项目ID（很少使用）")
    val projectId: String,
    @Schema(name = "项目名称")
    val projectName: String,
    @Schema(name = "项目代码（蓝盾项目Id）")
    val projectCode: String,
    @Schema(name = "项目类型")
    val projectType: Int?,
    @Schema(name = "审批状态")
    val approvalStatus: Int?,
    @Schema(name = "审批时间")
    val approvalTime: String?,
    @Schema(name = "审批人")
    val approver: String?,
    @Schema(name = "cc业务ID")
    val ccAppId: Long?,
    @Schema(name = "cc业务名称")
    val ccAppName: String?,
    @Schema(name = "创建时间")
    val createdAt: String?,
    @Schema(name = "创建人")
    val creator: String?,
    @Schema(name = "数据ID")
    val dataId: Long?,
    @Schema(name = "部署类型")
    val deployType: String?,
    @Schema(name = "事业群ID")
    val bgId: String?,
    @Schema(name = "事业群名字")
    val bgName: String?,
    @Schema(name = "中心ID")
    val centerId: String?,
    @Schema(name = "中心名称")
    val centerName: String?,
    @Schema(name = "部门ID")
    val deptId: String?,
    @Schema(name = "部门名称")
    val deptName: String?,
    @Schema(name = "业务线ID")
    val businessLineId: String?,
    @Schema(name = "业务线名称")
    val businessLineName: String?,
    @Schema(name = "描述")
    val description: String?,
    @Schema(name = "英文缩写")
    val englishName: String,
    @Schema(name = "extra")
    val extra: String?,
    @Schema(name = "是否离线")
    val offlined: Boolean?,
    @Schema(name = "是否保密")
    val secrecy: Boolean?,
    @Schema(name = "是否启用图表激活")
    val helmChartEnabled: Boolean?,
    @Schema(name = "kind")
    val kind: Int?,
    @Schema(name = "logo地址")
    val logoAddr: String?,
    @Schema(name = "评论")
    val remark: String?,
    @Schema(name = "修改时间")
    val updatedAt: String?,
    @Schema(name = "修改人")
    val updator: String?,
    @Schema(name = "useBK")
    val useBk: Boolean?,
    @Schema(name = "启用")
    val enabled: Boolean?,
    @Schema(name = "是否灰度")
    val gray: Boolean,
    @Schema(name = "混合云CC业务ID")
    val hybridCcAppId: Long?,
    @Schema(name = "支持构建机访问外网")
    val enableExternal: Boolean?,
    @Schema(name = "支持IDC构建机")
    val enableIdc: Boolean? = false,
    @Schema(name = "流水线数量上限")
    val pipelineLimit: Int? = 500,
    @Deprecated("即将作废，兼容插件中被引用到的旧的字段命名，请用hybridCcAppId代替")
    @Schema(name = "混合云CC业务ID(即将作废，兼容插件中被引用到的旧的字段命名，请用hybridCcAppId代替)")
    val hybrid_cc_app_id: Long?,
    @Deprecated("即将作废，兼容插件中被引用到的旧的字段命名，请用projectId代替")
    @Schema(name = "项目ID(即将作废，兼容插件中被引用到的旧的字段命名，请用projectId代替)")
    val project_id: String?,
    @Deprecated("即将作废，兼容插件中被引用到的旧的字段命名，请用projectName代替")
    @Schema(name = "旧版项目名称(即将作废，兼容插件中被引用到的旧的字段命名，请用projectName代替)")
    val project_name: String?,
    @Deprecated("即将作废，兼容插件中被引用到的旧的字段命名，请用projectCode代替")
    @Schema(name = "旧版项目代码(即将作废，兼容插件中被引用到的旧的字段命名，请用projectCode代替)")
    val project_code: String?,
    @Deprecated("即将作废，兼容插件中被引用到的旧的字段命名，请用ccAppId代替")
    @Schema(name = "旧版cc业务ID(即将作废，兼容插件中被引用到的旧的字段命名，请用ccAppId代替)")
    val cc_app_id: Long?,
    @Schema(name = "旧版cc业务名称(即将作废，兼容插件中被引用到的旧的字段命名，请用ccAppName代替)")
    @Deprecated("即将作废，兼容插件中被引用到的旧的字段命名，请用ccAppName代替")
    val cc_app_name: String?,
    @Schema(name = "项目路由指向")
    val routerTag: String?,
    @Schema(name = "关联系统Id")
    val relationId: String?,
    @Schema(name = "项目其他配置")
    val properties: ProjectProperties?,
    @Schema(name = "项目最大可授权人员范围")
    val subjectScopes: List<SubjectScopeInfo>?,
    @Schema(name = "是否权限私密")
    val authSecrecy: Int?,
    @Schema(name = "项目提示状态,0-不展示,1-展示创建成功,2-展示编辑成功")
    val tipsStatus: Int? = ProjectTipsStatus.NOT_SHOW.status,
    @Schema(name = "项目审批message")
    val approvalMsg: String? = "",
    @Schema(name = "是否拥有新版权限中心项目管理权限")
    val managePermission: Boolean? = null,
    @Schema(name = "是否展示用户管理图标")
    val showUserManageIcon: Boolean? = null,
    @Schema(name = "渠道")
    val channelCode: String? = null,
    @Schema(name = "运营产品ID")
    val productId: Int? = null,
    @Schema(name = "是否可以查看")
    val canView: Boolean? = null,
    @Schema(name = "安装模板权限")
    val pipelineTemplateInstallPerm: Boolean? = null
)
