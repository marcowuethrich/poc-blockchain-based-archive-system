package com.archive.shared

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import sawtooth.sdk.signing.Secp256k1Context
import sawtooth.sdk.signing.Secp256k1PrivateKey
import sawtooth.sdk.signing.Signer

@Component
class BlockchainSigner(
    @Value("\${archive.blockchain.private-key-hex}") private val privateKeyHex: String
) {

    @Bean
    fun signer(): Signer {
        return Signer(Secp256k1Context(), getPrivateKey())
    }

    fun getPrivateKey(): Secp256k1PrivateKey = Secp256k1PrivateKey.fromHex(this.privateKeyHex)

    fun getNewPrivateKey(context: Secp256k1Context): String = context.newRandomPrivateKey().hex()

}
