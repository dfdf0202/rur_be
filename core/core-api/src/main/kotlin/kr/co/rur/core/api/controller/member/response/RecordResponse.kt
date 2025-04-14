package kr.co.rur.core.api.controller.member.response

data class RecordResponse(
    var name: String,
    var record: List<Record>
)

data class Record(
    val id: Long,
    val price: Int,
    var reason: String,
)