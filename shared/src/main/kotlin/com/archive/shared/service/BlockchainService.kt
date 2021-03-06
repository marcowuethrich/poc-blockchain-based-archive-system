package com.archive.shared.service

import com.archive.shared.model.dto.StateDto
import com.archive.shared.model.dto.TransactionDto

/**
 * Blockchain service interface
 */
interface BlockchainService {

    fun submit(transaction: TransactionDto): String

    fun get(address: String): StateDto

}
