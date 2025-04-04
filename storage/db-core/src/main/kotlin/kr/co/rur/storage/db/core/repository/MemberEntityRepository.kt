package kr.co.rur.storage.db.core.repository

import kr.co.rur.storage.db.core.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberEntityRepository : JpaRepository<MemberEntity, Long> {
    fun findBySnsId(snsId: String): MemberEntity?
}