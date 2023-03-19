package kr.hs.entrydsm.exit.global.thirdparty.pdf

import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.global.thirdparty.pdf.util.PdfUtil
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