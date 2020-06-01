package com.archive.shared.service

import co.nstant.`in`.cbor.CborBuilder
import co.nstant.`in`.cbor.CborEncoder
import com.archive.shared.client.SawtoothClient
import com.archive.shared.model.dto.TransactionDto
import com.google.common.io.BaseEncoding
import com.google.protobuf.ByteString
import org.springframework.stereotype.Service
import sawtooth.sdk.protobuf.*
import sawtooth.sdk.signing.Signer
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.*

@Service
class SawtoothService(
    private val signer: Signer,
    private val client: SawtoothClient
) : BlockchainService {

    val archiveRecordNameSpace = this.sha512Hash(ADDRESS_PREFIX.toByteArray()).substring(0, 6)

    companion object {
        const val ADDRESS_PREFIX = "archive"
    }

    override fun submit(transaction: TransactionDto): String {
        val payload: ByteArray = this.createPayload(transaction)
        val transHeader: TransactionHeader = this.createTransactionHeader(transaction, payload)
        val trans: Transaction = this.createTransaction(transHeader, payload)
        val batchHeader: BatchHeader = this.createBatchHeader(arrayOf(trans))
        val batch: Batch = this.createBatch(batchHeader, arrayOf(trans))
        val batchList: ByteArray = this.createBatchList(batch)

        val response = client.postBatchList(batchList)

        return this.buildAddress(transaction.blockchainAddress)
    }

    fun createRandomAddress(): String = this.sha512Hash(UUID.randomUUID().toString().toByteArray()).substring(0, 64)

    override fun get(address: String) {
        TODO("Not yet implemented")

    }


    private fun createPayload(transaction: TransactionDto): ByteArray {
        val payload = ByteArrayOutputStream()
        CborEncoder(payload).encode(
            CborBuilder()
                .addMap()
                .put("refAddress", transaction.blockchainAddress)
                .put("contentHash", transaction.contentHash)
                .put("dipHash", transaction.dipHash)
                .put("action", transaction.action.action)
                .end()
                .build()
        )

        return payload.toByteArray()
    }


    private fun createTransactionHeader(transaction: TransactionDto, payload: ByteArray) =
        TransactionHeader.newBuilder()
            .setSignerPublicKey(this.signer.publicKey.hex())
            .setFamilyName(transaction.familyName)
            .setFamilyVersion(transaction.familyVersion)
            .addInputs(buildAddress(transaction.blockchainAddress))
            .addOutputs(buildAddress(transaction.blockchainAddress))
            .setPayloadSha512(sha512Hash(payload))
            .setBatcherPublicKey(signer.publicKey.hex())
            .setNonce(UUID.randomUUID().toString())
            .build()

    private fun sha512Hash(input: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-512")
        digest.reset()
        digest.update(input)
        return BaseEncoding.base16().lowerCase().encode(digest.digest())
    }


    private fun createTransaction(header: TransactionHeader, payload: ByteArray) = Transaction.newBuilder()
        .setHeader(header.toByteString())
        .setPayload(ByteString.copyFrom(payload))
        .setHeaderSignature(this.signer.sign(header.toByteArray()))
        .build();

    private fun createBatchHeader(trans: Array<Transaction>): BatchHeader = BatchHeader.newBuilder()
        .setSignerPublicKey(signer.publicKey.hex())
        .addAllTransactionIds(
            trans.map { transaction -> transaction.headerSignature }
        ).build()

    private fun createBatch(header: BatchHeader, trans: Array<Transaction>) = Batch.newBuilder()
        .setHeader(header.toByteString())
        .addAllTransactions(trans.asIterable())
        .setHeaderSignature(this.signer.sign(header.toByteArray()))
        .build()

    private fun createBatchList(batch: Batch) = BatchList.newBuilder()
        .addBatches(batch)
        .build()
        .toByteArray()

    private fun buildAddress(ref: String) = this.archiveRecordNameSpace + ref
}
