package com.archive.shared.model.dbo

import java.time.Instant

interface AuditedJpaPersistableDbo : JpaPersistableDbo {

    val createdAt: Instant

    val updatedAt: Instant

}
