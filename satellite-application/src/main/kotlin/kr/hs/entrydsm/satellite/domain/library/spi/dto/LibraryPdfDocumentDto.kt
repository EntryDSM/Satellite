package kr.hs.entrydsm.satellite.domain.library.spi.dto

class LibraryPdfDocumentDto(
    val byteArray: ByteArray,
    val index: Map<String, Int>
)