package kr.co.rur.storage.db.core.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "member")
class MemberEntity (

    @Column
    var snsId: String,

    @Column
    var nickNm: String? = null,

) : BaseEntity() {
    fun updateNickNm(nickNm: String) {
        this.nickNm = nickNm
    }
}