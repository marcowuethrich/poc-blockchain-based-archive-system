package com.archive.datamanagement.model.jpa

import java.time.Instant

interface AuditedJpaPersistable : JpaPersistable {

    val createdAt: Instant

    val updatedAt: Instant

}
