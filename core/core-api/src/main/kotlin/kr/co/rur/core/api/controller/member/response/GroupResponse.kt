package kr.co.rur.core.api.controller.member.response

import kr.co.rur.core.enum.GroupRole

data class GroupResponse (
    var id : Long?,
    var name: String?,
    var roles : GroupRole
)