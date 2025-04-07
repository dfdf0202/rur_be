package kr.co.rur.storage.db.core.entity

import jakarta.persistence.*

@Entity
@Table(name = "group_info")
class GroupInfoEntity (

    @Column
    val name: String,

) : BaseEntity()