package com.tencent.devops.auth.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.tencent.devops.auth.constant.AuthMessageCode
import com.tencent.devops.auth.pojo.ItsmCancelApplicationInfo
import com.tencent.devops.auth.pojo.ItsmResponseDTO
import com.tencent.devops.common.api.exception.ErrorCodeException
import com.tencent.devops.common.api.exception.RemoteServiceException
import com.tencent.devops.common.api.util.OkhttpUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

class ItsmService @Autowired constructor(
    val objectMapper: ObjectMapper
) {
    @Value("\${auth.appCode:}")
    private val appCode = ""

    @Value("\${auth.appSecret:}")
    private val appSecret = ""

    @Value("\${itsm.url:#{null}}")
    private val itsmUrlPrefix: String = ""

    fun cancelItsmApplication(itsmCancelApplicationInfo: ItsmCancelApplicationInfo): Boolean {
        val itsmResponseDTO = doHttpPost(
            uri = ITSM_APPLICATION_CANCEL_URL_SUFFIX,
            body = itsmCancelApplicationInfo
        )
        if (itsmResponseDTO.message != "success") {
            logger.warn("cancel itsm application failed!$itsmCancelApplicationInfo")
            throw ErrorCodeException(
                errorCode = AuthMessageCode.ERROR_ITSM_APPLICATION_CANCEL_FAIL,
                params = arrayOf(itsmCancelApplicationInfo.sn),
                defaultMessage = "cancel itsm application failed!sn(${itsmCancelApplicationInfo.sn})"
            )
        }
        return true
    }

    fun verifyItsmToken(token: String) {
        val param: MutableMap<String, String> = mutableMapOf()
        param["token"] = token
        logger.info("param:${param["token"]}")
        val itsmResponseDTO = doHttpPost(
            uri = ITSM_TOKEN_VERITY_URL_SUFFIX,
            body = param
        )
        val itsmApiResData = itsmResponseDTO.data as HashMap<String, Boolean>
        logger.info("itsmApiResData:$itsmApiResData")
        val isPassed = itsmApiResData["is_passed"]
        if (!isPassed!!) {
            logger.warn("verify itsm token failed!$token")
            throw ErrorCodeException(
                errorCode = AuthMessageCode.ERROR_ITSM_APPLICATION_CANCEL_FAIL,
                defaultMessage = "verify itsm token failed!"
            )
        }
    }

    private fun doHttpPost(uri: String, body: Any): ItsmResponseDTO {
        val header: MutableMap<String, String> = HashMap()
        header["bk_app_code"] = appCode
        header["bk_app_secret"] = appSecret
        val headerStr = objectMapper.writeValueAsString(header).replace("\\s".toRegex(), "")
        val jsonBody = objectMapper.writeValueAsString(body)
        logger.info("jsonBody:$jsonBody")
        val requestBody = jsonBody.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        logger.info("headerStr:$headerStr")
        val url = itsmUrlPrefix + uri
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("x-bkapi-authorization", headerStr)
            .build()
        return doRequest(url, request)
    }

    private fun doRequest(
        url: String,
        request: Request
    ): ItsmResponseDTO {
        OkhttpUtils.doHttp(request).use {
            if (!it.isSuccessful) {
                logger.warn("itsm request failed, uri:($url)|response: ($it)")
                throw RemoteServiceException("itsm request failed, response:($it)")
            }
            val responseStr = it.body!!.string()
            val responseDTO = objectMapper.readValue<ItsmResponseDTO>(responseStr)
            if (responseDTO.code != 0L || responseDTO.result == false) {
                // 请求错误
                logger.warn("itsm request failed, url:($url)|response:($it)")
                throw RemoteServiceException("itsm request failed, response:(${responseDTO.message})")
            }
            logger.info("itsm request response：${objectMapper.writeValueAsString(responseDTO.data)}")
            return responseDTO
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ItsmService::class.java)
        private const val ITSM_APPLICATION_CANCEL_URL_SUFFIX = "/operate_ticket/"
        private const val ITSM_TOKEN_VERITY_URL_SUFFIX = "/token/verify/"
    }
}