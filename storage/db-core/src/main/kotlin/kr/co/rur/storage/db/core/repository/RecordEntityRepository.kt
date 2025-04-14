package kr.co.rur.storage.db.core.repository

import kr.co.rur.storage.db.core.entity.RecordEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface RecordEntityRepository : JpaRepository<RecordEntity, Long> {
    @EntityGraph(attributePaths = ["memberEntity"])
    fun findByGroupInfoEntityId(groupId: Long): List<RecordEntity>
}