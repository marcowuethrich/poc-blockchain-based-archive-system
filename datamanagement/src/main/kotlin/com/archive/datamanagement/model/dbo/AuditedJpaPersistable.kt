package com.archive.datamanagement.model.dbo

import java.time.Instant

interface AuditedJpaPersistable : JpaPersistable {

    val createdAt: Instant

    val updatedAt: Instant

}
