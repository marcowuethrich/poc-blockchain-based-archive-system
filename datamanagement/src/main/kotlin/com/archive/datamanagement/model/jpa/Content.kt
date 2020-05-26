package com.archive.datamanagement.model.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "T_Content")
class Content(
    @Id
    @Column(nullable = false, unique = true)
    override val id: UUID = UUID.randomUUID(),

    @CreatedDate
    @Column(nullable = false)
    override val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(nullable = false)
    override val updatedAt: Instant = Instant.now(),

    @Column
    val name: String? = null,

    @Column
    val extension: String? = null,

    @Column
    val type: String? = null,

    @Column
    val size: Int? = null,

    @Column
    val sizeUnit: String? = null,

    @OneToOne(mappedBy = "content")
    @JsonIgnore
    val archiveObject: ArchiveObject? = null

) : AuditedJpaPersistable {
    override fun equals(other: Any?): Boolean = this === other || (other is JpaPersistable && id == other.id)

    override fun hashCode(): Int = 31 * super.hashCode() + id.hashCode()

    override fun toString(): String = "${javaClass.simpleName}(id = $id)"
}
