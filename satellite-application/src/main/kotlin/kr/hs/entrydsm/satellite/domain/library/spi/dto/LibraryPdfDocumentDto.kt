package kr.hs.entrydsm.satellite.domain.library.spi.dto

import kr.hs.entrydsm.satellite.domain.library.domain.DocumentIndex

class LibraryPdfDocumentDto(
    val byteArray: ByteArray,
    val index: List<DocumentIndex>
)