package kr.co.rur.core.api.controller.member

import kr.co.rur.core.api.controller.member.request.LoginRequest
import kr.co.rur.core.api.controller.member.request.MemberGroupUpdateRequest
import kr.co.rur.core.api.controller.member.response.LoginResponse
import kr.co.rur.core.api.service.MemberService
import kr.co.rur.core.api.support.response.ApiResponse
import kr.co.rur.core.api.support.utils.RequestUtils
import org.springframework.web.bind.annotation.*

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
        return ApiResponse.success(response)
    }

    @PutMapping("/invite-code")
    fun inviteCode(@RequestBody groupRequest: MemberGroupUpdateRequest) : ApiResponse<Boolean> {
        val memberId: Long? = RequestUtils.getId()
        var response = memberService.updateMemberGroup(memberId, groupRequest)
        return ApiResponse.success(true)
    }
}