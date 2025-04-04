package kr.co.rur.storage.db.core.repository

import kr.co.rur.storage.db.core.entity.MemberEntity
import kr.co.rur.storage.db.core.entity.MemberGroupEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberGroupEntityRepository : JpaRepository<MemberGroupEntity, Long> {
    fun findByMemberEntity(memberEntityId: MemberEntity): List<MemberGroupEntity>
}