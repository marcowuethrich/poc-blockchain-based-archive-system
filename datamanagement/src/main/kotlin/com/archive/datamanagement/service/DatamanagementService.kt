package com.archive.datamanagement.service

import com.archive.datamanagement.model.jpa.ArchiveObject
import com.archive.datamanagement.repository.ArchiveObjectRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DatamanagementService(
    private val repository: ArchiveObjectRepository
) {
    fun getAllArchiveObjects(): Iterable<ArchiveObject> = this.repository.findAllByDeleted()

    fun save(entry: ArchiveObject) = this.repository.save(entry)

    fun update(entry: ArchiveObject): ArchiveObject = TODO()

    fun delete(id: UUID) = this.repository.findById(id).ifPresent {
        it.deleted = true
        this.update(it)
    }

}
