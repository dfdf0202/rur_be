package kr.co.rur.storage.db.core.entity

import jakarta.persistence.*

@Entity
@Table(name = "member_group")
class MemberGroupEntity (

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var memberEntity: MemberEntity? = null,

) : BaseEntity()