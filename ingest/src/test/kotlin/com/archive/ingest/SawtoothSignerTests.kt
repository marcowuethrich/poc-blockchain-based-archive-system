package com.archive.ingest

import com.archive.shared.service.SawtoothSigner
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import sawtooth.sdk.signing.Secp256k1Context

class SawtoothSignerTests {

    private val signer: SawtoothSigner =
        SawtoothSigner("placeholder-private-key")

    @Test
    fun getNewPrivateKey() {
        val key = this.signer.getNewPrivateKey(Secp256k1Context())
        println(key)
        assertNotNull(key)
    }
}
