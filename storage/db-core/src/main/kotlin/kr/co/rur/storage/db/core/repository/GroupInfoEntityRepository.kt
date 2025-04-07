package kr.co.rur.storage.db.core.repository

import kr.co.rur.storage.db.core.entity.GroupInfoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GroupInfoEntityRepository : JpaRepository<GroupInfoEntity, Long> {
}