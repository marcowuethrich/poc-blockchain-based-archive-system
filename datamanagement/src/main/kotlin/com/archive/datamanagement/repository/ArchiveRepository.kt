package com.archive.datamanagement.repository

import com.archive.datamanagement.model.jpa.ArchiveObject
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ArchiveRepository : CrudRepository<ArchiveObject, UUID> {

}
