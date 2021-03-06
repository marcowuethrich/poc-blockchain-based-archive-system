package com.archive.shared.model.dbo

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "T_Archive_Object")
data class ArchiveObjectDbo(

    @Id
    @Column(nullable = false, unique = true)
    override var id: UUID = UUID.randomUUID(),

    @CreatedDate
    @Column(nullable = false)
    override val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(nullable = false)
    override val updatedAt: Instant = Instant.now(),

    @Column(nullable = false)
    var deleted: Boolean = false,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "blockchainRefId", referencedColumnName = "id")
    var blockchainRef: BlockchainRefDbo? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "contentId", referencedColumnName = "id")
    val content: ContentDbo? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "metaDataId", referencedColumnName = "id")
    val metaData: MetaDataDbo? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "producerId", referencedColumnName = "id")
    val producer: ProducerDbo? = null

) : AuditedJpaPersistableDbo {

    override fun equals(other: Any?): Boolean = this === other || (other is JpaPersistableDbo && id == other.id)

    override fun hashCode(): Int = 31 * super.hashCode() + id.hashCode()

    override fun toString(): String = "${javaClass.simpleName}(id = $id)"
}
