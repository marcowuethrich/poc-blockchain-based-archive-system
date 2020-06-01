package com.archive.datamanagement

import com.archive.shared.model.dbo.ArchiveObjectDbo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * DataManagementRepository
 * Spring Data Repository
 */
interface DataManagementRepository : JpaRepository<ArchiveObjectDbo, UUID> {
    fun findAllByDeleted(deleted: Boolean = false): MutableIterable<ArchiveObjectDbo>
}
