package kr.co.rur.core.api.service

import jakarta.persistence.EntityNotFoundException
import kr.co.rur.core.api.controller.member.response.Record
import kr.co.rur.core.api.controller.member.response.RecordResponse
import kr.co.rur.storage.db.core.entity.RecordEntity
import kr.co.rur.storage.db.core.repository.GroupInfoEntityRepository
import kr.co.rur.storage.db.core.repository.MemberEntityRepository
import kr.co.rur.storage.db.core.repository.RecordEntityRepository
import org.springframework.stereotype.Service

@Service
class RecordService(
    private val memberEntityRepository : MemberEntityRepository,
    private val groupInfoEntityRepository: GroupInfoEntityRepository,
    private val recordEntityRepository: RecordEntityRepository
) {
    fun findByGroup(groupId: Long): List<RecordResponse> {
        val groupEntity = groupInfoEntityRepository.findById(groupId)
            .orElseThrow { RuntimeException("Group entity not found") }

        val recordEntity = recordEntityRepository.findByGroupInfoEntityId(groupEntity.getId()!!)

        if (recordEntity.isEmpty()) {
            return emptyList()
        }

        val grouped: Map<String, List<RecordEntity>> = recordEntity.groupBy { it.memberEntity!!.nickNm ?: "이름없음" }

        return grouped.map {
            RecordResponse(
                it.key,
                it.value.map {
                    Record(
                        it.getId()!!,
                        it.price,
                        it.reason
                    )
                }
            )
        }
    }
}