package com.archive.ingest

import com.archive.shared.client.DataManagementClient
import com.archive.shared.model.dto.ContentDto
import com.archive.shared.model.dto.DIPDto
import com.archive.shared.model.dto.HashAlgorithm
import com.archive.shared.service.SawtoothService
import com.archive.shared.service.VerifyService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HashTests {


    @Mock
    lateinit var sawtoothService: SawtoothService

    @Mock
    lateinit var dataManagementClient: DataManagementClient
    private lateinit var verifier: VerifyService;
    private val mapper: ObjectMapper = jacksonObjectMapper()


    @Before
    fun init() {
        this.verifier = VerifyService(mapper, sawtoothService, dataManagementClient)
    }

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
        println(hash)

        assertEquals("2f6fd544d3043e9c3619515b4acbdf64545c348d5674e87098852a63039b34ff", hash)

    }

    @Test
    fun simpleDIPTest() {
        val dip = DIPDto(
            content = ContentDto(
                name = "simple_txt_file",
                extension = "txt",
                type = "text/plain",
                size = 43,
                sizeUnit = "byte"
            ),
            creation = "2020-04-10T03:34:18.115",
            authorName = "Marco Wüthrich"
        )
        val content = mapper.writeValueAsBytes(dip)
        val hash = this.verifier.contentToHash(content, HashAlgorithm.SHA3_256)
        println(hash)
        assertEquals("b94cf449e31557f6f6e72491673d35bcf11ce1c6a78fae9f2962bd3c893698a0", hash)
    }

    @Test
    fun verifyDIP() {
        val dip = DIPDto(
            content = ContentDto(
                name = "simple_txt_file",
                extension = "txt",
                type = "text/plain",
                size = 43,
                sizeUnit = "byte"
            ),
            creation = "2020-04-10T03:34:18.115",
            authorName = "Marco Wüthrich"
        )
        this.verifier.verifyHash(
            mapper.writeValueAsBytes(dip),
            "b94cf449e31557f6f6e72491673d35bcf11ce1c6a78fae9f2962bd3c893698a0",
            HashAlgorithm.SHA3_256
        )
    }


}
