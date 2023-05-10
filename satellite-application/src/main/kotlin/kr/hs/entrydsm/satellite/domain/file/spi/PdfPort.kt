package kr.hs.entrydsm.satellite.domain.file.spi

import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.library.spi.dto.LibraryPdfDocumentDto

interface PdfPort {
    fun generateLibraryDocument(documents: List<Document>): LibraryPdfDocumentDto
}