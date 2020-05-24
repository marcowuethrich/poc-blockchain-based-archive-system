package com.archive.datamanagement.service

import com.archive.datamanagement.model.dbo.ArchiveObject
import com.archive.datamanagement.repository.ArchiveRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArchiveService(
        private val archiveRepository: ArchiveRepository
) {
    fun getAllArchiveObjects(): Iterable<ArchiveObject> = this.archiveRepository.findAll()

    fun save(entry: ArchiveObject) = this.archiveRepository.save(entry)

    fun update(entry: ArchiveObject): Nothing = TODO()

    fun delete(id: UUID) = this.archiveRepository.deleteById(id)

}
