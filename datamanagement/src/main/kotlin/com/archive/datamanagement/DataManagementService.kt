package com.archive.datamanagement

import com.archive.shared.model.ModelConverter
import com.archive.shared.model.dbo.ArchiveObjectDbo
import com.archive.shared.model.dbo.BlockchainRefDbo
import com.archive.shared.model.dto.AIPDto
import org.springframework.stereotype.Service
import java.util.*

/**
 * DataManagement Service
 * Implements the logic of the meta data module
 */
@Service
class DataManagementService(
    private val repository: DataManagementRepository,
    private val converter: ModelConverter
) {
    fun getAllArchiveObjects(): Iterable<ArchiveObjectDbo> = this.repository.findAllByDeleted()

    fun getAIP(id: UUID): AIPDto = this.converter.dboToDto(this.repository.findById(id).orElseThrow())

    fun save(entry: ArchiveObjectDbo): ArchiveObjectDbo = this.repository.save(entry)

    fun update(entry: ArchiveObjectDbo): ArchiveObjectDbo = this.save(entry)

    fun delete(id: UUID) = this.repository.findById(id).ifPresent {
        it.deleted = true
        this.update(it)
    }

    fun clear() = this.repository.deleteAll()

    fun updateBlockchainAddress(id: UUID, ref: String) {
        val dbo = this.repository.findById(id)
        dbo.ifPresent {
            it.blockchainRef = BlockchainRefDbo(reference = ref)
            this.repository.save(it)
        }
    }

    fun getBlockchainAddress(aipId: UUID): String {
        val blockchainRef = this.repository.getOne(aipId).blockchainRef
        return blockchainRef!!.reference!!
    }
}
