package kr.co.rur.storage.db.core.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @CreationTimestamp
    @Column
    private var createdAt: LocalDateTime? = null

    @UpdateTimestamp
    @Column
    private var updatedAt: LocalDateTime? = null

    @Comment("사용 여부")
    @Column(nullable = false, columnDefinition = "bit(1) DEFAULT b'1'")
    private var isUse = true

    fun getId(): Long? {
        return id
    }

    fun getCreatedAt(): LocalDateTime? {
        return createdAt
    }

    fun getUpdatedAt(): LocalDateTime? {
        return updatedAt
    }

}