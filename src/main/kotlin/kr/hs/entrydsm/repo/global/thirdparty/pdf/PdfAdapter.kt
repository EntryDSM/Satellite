package kr.hs.entrydsm.repo.global.thirdparty.pdf

import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.global.thirdparty.pdf.util.PdfUtil
import org.springframework.stereotype.Component


@Component
class PdfAdapter(
    private val templateProcessor: TemplateProcessor
) {

    fun generateGradeLibraryDocument(documents: List<Document>): ByteArray =
        documents
            .map { templateProcessor.process(TemplateFileName.DOCUMENT, it) }
            .map(PdfUtil::convertHtmlToPdf)
            .reduce(PdfUtil::concatPdf)
            .toByteArray()

}