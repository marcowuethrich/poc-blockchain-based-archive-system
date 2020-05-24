package com.archive.datamanagement.model

import java.time.Instant

interface AuditedJpaPersistable : JpaPersistable {

    val createdAt: Instant

    val updatedAt: Instant

}
