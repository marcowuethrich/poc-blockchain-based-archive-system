package com.archive.shared.model

import com.archive.shared.model.dbo.ArchiveObjectDbo
import com.archive.shared.model.dbo.ContentDbo
import com.archive.shared.model.dbo.MetaDataDbo
import com.archive.shared.model.dbo.ProducerDbo
import com.archive.shared.model.dto.AIPDto
import com.archive.shared.model.dto.ContentDto
import com.archive.shared.model.dto.DIPDto
import com.archive.shared.model.dto.SIPDto
import org.springframework.stereotype.Service

@Service
class ModelConverter {

    fun dtoToDbo(sip: SIPDto, aip: AIPDto) = ArchiveObjectDbo(
        content = ContentDbo(
            name = aip.dip.content.name,
            extension = aip.dip.content.extension,
            originalContentFileName = aip.originalContentFileName,
            type = aip.dip.content.type,
            size = aip.dip.content.size,
            sizeUnit = aip.dip.content.sizeUnit,
            fingerprint = aip.contentHash
        ),
        metaData = MetaDataDbo(
            creation = aip.dip.creation,
            authorName = aip.dip.authorName,
            fingerprint = aip.dipHash
        ),
        producer = ProducerDbo(
            name = sip.producer.name,
            uuid = sip.producer.uuid
        )
    )

    fun dboToDto(dbo: ArchiveObjectDbo): AIPDto = AIPDto(
        id = dbo.id,
        dip = DIPDto(
            content = ContentDto(
                id = dbo.content!!.id,
                name = dbo.content.name,
                extension = dbo.content.extension,
                type = dbo.content.type,
                size = dbo.content.size,
                sizeUnit = dbo.content.sizeUnit
            ),
            creation = dbo.metaData!!.creation,
            authorName = dbo.metaData.authorName
        ),
        originalContentFileName = dbo.content.originalContentFileName!!,
        dipHash = dbo.metaData.fingerprint!!,
        contentHash = dbo.content.fingerprint!!
    )

}
