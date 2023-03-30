package kr.hs.entrydsm.satellite.global.thirdparty.pdf

import com.itextpdf.io.source.ByteArrayOutputStream
import kr.hs.entrydsm.satellite.domain.document.persistence.DocumentJpaEntity
import kr.hs.entrydsm.satellite.common.thirdparty.pdf.util.PdfUtil
import org.springframework.stereotype.Component

@Component
class PdfAdapter(
    private val templateProcessor: TemplateProcessor
) {

    fun generateGradeLibraryDocument(documentJpaEntities: List<DocumentJpaEntity>): ByteArray {

        val bookCover = templateProcessor.process(
            TemplateFileName.COVER,
            null
        )
            .run(PdfUtil::convertHtmlToPdf)
        val documentsByClassRoom = getPdfDocumentChapteredByClassNum(documentJpaEntities)

        return PdfUtil.concatPdf(bookCover, documentsByClassRoom).toByteArray()
    }

    private fun getPdfDocumentChapteredByClassNum(documentJpaEntities: List<DocumentJpaEntity>): ByteArrayOutputStream {

        val documentClassNumMap = getDocumentClassNumMap(documentJpaEntities)

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

    private fun getDocumentClassNumMap(documentJpaEntities: List<DocumentJpaEntity>): MutableMap<String, MutableList<DocumentJpaEntity>> {
        val documentsClassNumMap = mutableMapOf<String, MutableList<DocumentJpaEntity>>()

        documentJpaEntities.forEach { document ->
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