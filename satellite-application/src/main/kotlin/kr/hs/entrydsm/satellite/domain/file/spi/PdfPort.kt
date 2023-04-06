package kr.hs.entrydsm.satellite.domain.file.spi

import kr.hs.entrydsm.satellite.domain.document.domain.Document

interface PdfPort {
    fun generateGradeLibraryDocument(documents: List<Document>): ByteArray
}