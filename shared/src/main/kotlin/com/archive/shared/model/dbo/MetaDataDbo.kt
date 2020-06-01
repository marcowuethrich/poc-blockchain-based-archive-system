package com.archive.shared.model.dbo

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "T_MetaData")
class MetaDataDbo(
    @Id
    @Column(nullable = false, unique = true)
    override var id: UUID = UUID.randomUUID(),

    @CreatedDate
    @Column(nullable = false)
    override val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(nullable = false)
    override val updatedAt: Instant = Instant.now(),

    @Column
    val creation: String? = null,

    @Column
    val authorName: String? = null,

    @Column
    val fingerprint: String? = null,

    @OneToOne(mappedBy = "metaData")
    @JsonIgnore
    val archiveObject: ArchiveObjectDbo? = null


) : AuditedJpaPersistableDbo {
    override fun equals(other: Any?): Boolean = this === other || (other is JpaPersistableDbo && id == other.id)

    override fun hashCode(): Int = 31 * super.hashCode() + id.hashCode()

    override fun toString(): String = "${javaClass.simpleName}(id = $id)"
}
