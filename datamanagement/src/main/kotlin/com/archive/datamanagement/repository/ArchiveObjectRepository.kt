package com.archive.datamanagement.repository

import com.archive.datamanagement.model.jpa.ArchiveObject
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * ArchiveObjectRepository
 * Spring Data Repository
 */
interface ArchiveObjectRepository : JpaRepository<ArchiveObject, UUID> {

//    fun findByIdAndDeleted(id: UUID, deleted: Boolean = false): MutableIterable<ArchiveObject>

    fun findAllByDeleted(deleted: Boolean = false): MutableIterable<ArchiveObject>
}
