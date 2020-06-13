package com.archive.ingest

import com.archive.shared.service.SawtoothSigner
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import sawtooth.sdk.signing.Secp256k1Context

class SawtoothSignerTests {

    private val signer: SawtoothSigner =
        SawtoothSigner("b4704ab0023b7a141742eaaa63dadf6e459d6c1da579915a23f6a40ca06cf693")

    @Test
    fun getNewPrivateKey() {
        val key = this.signer.getNewPrivateKey(Secp256k1Context())
        println(key)
        assertNotNull(key)
    }

    @Test
    fun getPublicKey() {
        val pubKey = this.signer.signer().publicKey.hex();

        assertEquals("03b7cda073e3853db470e26c8f3f63c0f5f76002e446fbb36c47dc84c4931fd9cb", pubKey)
    }
}
