package kr.co.rur.core.service.oauth

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.rur.core.controller.member.request.LoginRequest
import kr.co.rur.core.controller.member.response.LoginResponse
import kr.co.rur.core.model.oauth.conf.AuthConfig
import kr.co.rur.core.model.oauth.model.AuthToken
import kr.co.rur.core.model.oauth.model.KakaoResponseWrapper
import kr.co.rur.core.support.utils.HttpClientUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.util.UriComponentsBuilder

@Service
class KakaoSnsService(
    @Value("\${auth.kakao.client_secret}")
    private var kakaoClientSecret: String,
    @Value("\${auth.kakao.callback_url}")
    private var kakaoCallbackUrl: String,
) {

    fun getAuthPath() : String {

        val uri = UriComponentsBuilder
            .fromUriString("https://kauth.kakao.com/oauth/authorize")
            .queryParam("client_id", kakaoClientSecret)
            .queryParam("redirect_uri", kakaoCallbackUrl)
            .queryParam("response_type", "code")
            .queryParam("prompt", "login")
            .build()
            .toUriString()

        return uri
    }

    fun getSnsId(loginRequest: LoginRequest): String {

        var authConfig = AuthConfig(loginRequest.code, loginRequest.callbackUrl)

        // kakao access token 가져오기
        var authToken = this.getFirstRequestToken(authConfig)

        var snsId = "";
        if (StringUtils.hasText(authToken?.accessToken)) {
            // sns id 가져오기
            snsId = this.getUserInfo(authToken)?.id?: ""
        }

        return snsId;
    }

    fun getFirstRequestToken(authConfig: AuthConfig): AuthToken? {
        var url = "https://kauth.kakao.com/oauth/token"

        val param: MutableMap<String, String> = java.util.HashMap()
        param["grant_type"] = "authorization_code"
        param["client_id"] = kakaoClientSecret
        param["redirect_uri"] = authConfig.redirectUri!!
        param["code"] = authConfig.code!!

        var response = HttpClientUtils.doPost(url, param)
        val mapper = ObjectMapper()
        var authToken: AuthToken? = null

        try {
            authToken = mapper.readValue<AuthToken>(response, AuthToken::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return authToken
    }

    fun getUserInfo(authToken: AuthToken?): KakaoResponseWrapper? {
        var wrapper: KakaoResponseWrapper? = null

        if (authToken == null) {
            return wrapper
        }

        var profileUrl = "https://kapi.kakao.com/v2/user/me"

        val mapper = ObjectMapper()
        val response: String = HttpClientUtils.doGet(
            profileUrl,
            mapOf("Authorization" to "Bearer ${authToken?.accessToken}")
        )

        try {
            // JSON 데이터를 KakaoResponseWrapper 객체로 매핑
            wrapper = mapper.readValue(response, KakaoResponseWrapper::class.java)
            return wrapper
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return wrapper;
    }
}