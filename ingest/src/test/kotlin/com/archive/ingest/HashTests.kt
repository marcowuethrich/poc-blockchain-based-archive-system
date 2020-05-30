package com.archive.ingest

import com.archive.ingest.service.VerifyHash
import com.archive.shared.model.dto.ContentDto
import com.archive.shared.model.dto.DIPDto
import com.archive.shared.model.dto.HashAlgorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HashTests {

    private val mapper: ObjectMapper = jacksonObjectMapper()
    private val verifier: VerifyHash = VerifyHash(mapper)

    @Test
    fun sha_256Test() {
        assertEquals(
            "a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e",
            this.verifier.contentToHash("Hello World".toByteArray(), HashAlgorithm.SHA_256)
        )
    }

    @Test
    fun sha3_256Test() {
        assertEquals(
            "e167f68d6563d75bb25f3aa49c29ef612d41352dc00606de7cbd630bb2665f51",
            this.verifier.contentToHash("Hello World".toByteArray(), HashAlgorithm.SHA3_256)
        )
    }

    @Test
    fun simpleFileTest() {
        val testFile = this::class.java.classLoader.getResourceAsStream("data/simple_txt_file.txt").readAllBytes()
        val hash = this.verifier.contentToHash(testFile, HashAlgorithm.SHA3_256)
        print(hash)

        assertEquals("2f6fd544d3043e9c3619515b4acbdf64545c348d5674e87098852a63039b34ff", hash)

    }

    @Test
    fun simpleDIPTest() {
        val dip = DIPDto(
            content = ContentDto(
                name = "simple_txt_file",
                extension = "txt",
                type = "text",
                size = 43,
                sizeUnit = "byte"
            ),
            creation = "2020-05-29T15:54:12.380Z",
            authorName = "Marco Wüthrich"
        )
        val content = mapper.writeValueAsBytes(dip)
        val hash = this.verifier.contentToHash(content, HashAlgorithm.SHA3_256)
        print(hash)
        assertEquals("ad15daa9d66fedf14c0d25fb9365917c75dd5029948ff9bdfc6e8d69384a78d0", hash)
    }

}