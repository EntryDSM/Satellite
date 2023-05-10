package kr.hs.entrydsm.satellite.global.thirdparty.pdf

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.file.spi.PdfPort
import kr.hs.entrydsm.satellite.domain.library.domain.DocumentIndex
import kr.hs.entrydsm.satellite.domain.library.spi.dto.LibraryPdfDocumentDto
import kr.hs.entrydsm.satellite.global.thirdparty.pdf.util.PdfUtil
import java.io.ByteArrayOutputStream

@Adapter
class PdfAdapter(
    private val templateProcessor: TemplateProcessor
) : PdfPort {

    override fun generateLibraryDocument(documents: List<Document>): LibraryPdfDocumentDto {

        val sortedDocuments = documents.sortedBy { it.writer.studentNumber }
        var page = 0
        return LibraryPdfDocumentDto(
            byteArray = getPdfDocumentPdfs(sortedDocuments).toByteArray(),
            index = sortedDocuments
                .map {
                    DocumentIndex(
                        name = it.writer.name,
                        major = it.writer.majorName,
                        studentNumber = it.writer.studentNumber,
                        page = page
                    ).apply {
                        page += it.projectList.size + 1
                    }
                }
        )
    }

    private fun getPdfDocumentPdfs(documents: List<Document>): ByteArrayOutputStream {
        return PdfUtil.concatPdf(
            documents
                .map { templateProcessor.process(TemplateFileName.DOCUMENT, it) }
                .map(PdfUtil::convertHtmlToPdf)
        )
    }
}