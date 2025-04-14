package kr.co.rur.core.api.controller.record

import kr.co.rur.core.api.controller.member.response.RecordResponse
import kr.co.rur.core.api.service.RecordService
import kr.co.rur.core.api.support.response.ApiResponse
import kr.co.rur.core.api.support.utils.RequestUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/record"])
class RecordController(
    private val recordService: RecordService
) {

    @GetMapping("/{groupId}")
    fun getRecords(@PathVariable groupId: Long): ApiResponse<List<RecordResponse>> {
        var response = recordService.findByGroup(groupId)
        return ApiResponse.success(response)
    }

}