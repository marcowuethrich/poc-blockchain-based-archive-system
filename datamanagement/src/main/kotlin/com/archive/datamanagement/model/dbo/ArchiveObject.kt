package com.archive.datamanagement.model.dbo

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "T_Archive_Object")
data class ArchiveObject(

        @Id
        @Column(nullable = false, unique = true)
        override val id: UUID = UUID.randomUUID(),

        @CreatedDate
        @Column(nullable = false)
        override val createdAt: Instant = Instant.now(),

        @LastModifiedDate
        @Column(nullable = false)
        override val updatedAt: Instant = Instant.now(),

        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "blockchainRefId", referencedColumnName = "id")
        val blockchainRef: BlockchainRef? = null,

        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "contentId", referencedColumnName = "id")
        val content: Content? = null,

        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "metaDataId", referencedColumnName = "id")
        val metaData: MetaData? = null,

        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "producerId", referencedColumnName = "id")
        val producer: Producer? = null

) : AuditedJpaPersistable {

        override fun equals(other: Any?): Boolean = this === other || (other is JpaPersistable && id == other.id)

        override fun hashCode(): Int = 31 * super.hashCode() + id.hashCode()

        override fun toString(): String = "${javaClass.simpleName}(id = $id)"
}
