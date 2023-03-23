package kr.hs.entrydsm.repo.global.thirdparty.pdf

import com.itextpdf.io.source.ByteArrayOutputStream
import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.global.thirdparty.pdf.util.PdfUtil
import org.springframework.stereotype.Component

@Component
class PdfAdapter(
    private val templateProcessor: TemplateProcessor
) {

    fun generateGradeLibraryDocument(documents: List<Document>): ByteArray {

        val bookCover = templateProcessor.process(
            TemplateFileName.COVER,
            null
        )
            .run(PdfUtil::convertHtmlToPdf)
        val documentsByClassRoom = getPdfDocumentChapteredByClassNum(documents)

        return PdfUtil.concatPdf(bookCover, documentsByClassRoom).toByteArray()
    }

    private fun getPdfDocumentChapteredByClassNum(documents: List<Document>): ByteArrayOutputStream {

        val documentClassNumMap = getDocumentClassNumMap(documents)

        return documentClassNumMap.map { (classNum, documents) ->

            val chapter = templateProcessor.process(
                TemplateFileName.CHAPTER,
                mapOf(
                    "grade" to documents[0].writer.grade,
                    "classNum" to classNum
                )
            )
            .run(PdfUtil::convertHtmlToPdf)

            val resume = documents
                .map { templateProcessor.process(TemplateFileName.DOCUMENT, it) }
                .map(PdfUtil::convertHtmlToPdf)
                .reduce(PdfUtil::concatPdf)

            return@map PdfUtil.concatPdf(chapter, resume)

        }.reduce(PdfUtil::concatPdf)
    }

    private fun getDocumentClassNumMap(documents: List<Document>): MutableMap<String, MutableList<Document>> {
        val documentsClassNumMap = mutableMapOf<String, MutableList<Document>>()

        documents.forEach { document ->
            val classNum = document.writer.classNum
            if (documentsClassNumMap.containsKey(document.writer.grade)) {
                documentsClassNumMap[classNum] = mutableListOf()
            } else {
                documentsClassNumMap[classNum]!!.add(document)
            }
        }
        return documentsClassNumMap
    }
}