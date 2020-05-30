package com.archive.shared.model

import com.archive.shared.model.dbo.ArchiveObjectDbo
import com.archive.shared.model.dbo.ContentDbo
import com.archive.shared.model.dbo.MetaDataDbo
import com.archive.shared.model.dbo.ProducerDbo
import com.archive.shared.model.dto.AIPDto
import com.archive.shared.model.dto.SIPDto
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class ModelConverter {

    fun dtoToDbo(sip: SIPDto, aip: AIPDto) = ArchiveObjectDbo(
        content = ContentDbo(
            name = aip.dip.content.name,
            extension = aip.dip.content.extension,
            type = aip.dip.content.type,
            size = aip.dip.content.size,
            sizeUnit = aip.dip.content.sizeUnit
        ),
        metaData = MetaDataDbo(
            creation = OffsetDateTime.parse(aip.dip.creation),
            authorName = aip.dip.authorName
        ),
        producer = ProducerDbo(
            name = sip.producer.name,
            uuid = sip.producer.uuid
        )
    )
}
