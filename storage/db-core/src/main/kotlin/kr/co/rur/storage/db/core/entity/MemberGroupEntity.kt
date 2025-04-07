package kr.co.rur.storage.db.core.entity

import jakarta.persistence.*
import kr.co.rur.core.enum.GroupRole

@Entity
@Table(name = "member_group")
class MemberGroupEntity (

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var memberEntity: MemberEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    val groupEntity: GroupInfoEntity,

    @Enumerated(EnumType.STRING)
    var role: GroupRole

) : BaseEntity()