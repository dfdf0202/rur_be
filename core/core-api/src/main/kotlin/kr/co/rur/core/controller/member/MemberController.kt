package kr.co.rur.core.controller.member

import kr.co.rur.core.controller.member.request.LoginRequest
import kr.co.rur.core.controller.member.response.LoginResponse
import kr.co.rur.core.service.MemberService
import kr.co.rur.core.support.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/member"])
class MemberController (
    private val memberService: MemberService
) {

    @GetMapping("/auth-path")
    fun getAuthPath() : ApiResponse<String> {
        return ApiResponse.success(memberService.getAuthPath())
    }

    @GetMapping("/login")
    fun login(loginRequest: LoginRequest) : ApiResponse<LoginResponse> {
        var response = memberService.login(loginRequest)
        return ApiResponse.success(response);
    }
}