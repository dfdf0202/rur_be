package kr.co.rur.storage.db.core.entity

import jakarta.persistence.*

@Entity
@Table(name = "record")
class RecordEntity (
    @Column(nullable = false)
    var price: Int,

    @Column(nullable = false)
    var reason: String,

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var memberEntity: MemberEntity? = null,

    @JoinColumn(name = "group_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var groupInfoEntity: GroupInfoEntity? = null
) : BaseEntity()