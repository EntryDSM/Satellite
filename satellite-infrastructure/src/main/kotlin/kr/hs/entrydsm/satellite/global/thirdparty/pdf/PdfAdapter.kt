package kr.hs.entrydsm.satellite.global.thirdparty.pdf

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.file.spi.PdfPort
import kr.hs.entrydsm.satellite.domain.library.spi.dto.LibraryPdfDocumentDto
import kr.hs.entrydsm.satellite.global.thirdparty.pdf.util.PdfUtil
import java.io.ByteArrayOutputStream

@Adapter
class PdfAdapter(
    private val templateProcessor: TemplateProcessor
) : PdfPort {

    override fun generateGradeLibraryDocument(documents: List<Document>): LibraryPdfDocumentDto {

        val bookCover = templateProcessor.process(
            TemplateFileName.COVER, null
        ).run(PdfUtil::convertHtmlToPdf)
        val documentsByClassRoom = getPdfDocumentChapterByClassNum(documents)

        var page = 0
        return LibraryPdfDocumentDto(
            byteArray = PdfUtil.concatPdf(bookCover, documentsByClassRoom).toByteArray(),
            index = documents
                .sortedBy { it.writer.studentNumber }
                .associate {
                    val pair = "${it.writer.studentNumber} ${it.writer.name}" to page
                    page += it.projectList.size + 1
                    pair
                }
        )
    }

    private fun getPdfDocumentChapterByClassNum(documents: List<Document>): ByteArrayOutputStream {

        val index = mapOf<String, Int>()

        return PdfUtil.concatPdf(
            documents
                .map { templateProcessor.process(TemplateFileName.DOCUMENT, it) }
                .map(PdfUtil::convertHtmlToPdf)
        )
    }

    private fun getDocumentClassNumMap(documents: List<Document>): MutableMap<Int, MutableList<Document>> {
        val documentsClassNumMap = mutableMapOf<Int, MutableList<Document>>()

        documents.forEach { document ->
            val classNum = document.writer.classNum
            if (documentsClassNumMap.containsKey(document.writer.classNum)) {
                documentsClassNumMap[classNum]!!.add(document)
            } else {
                documentsClassNumMap[classNum] = mutableListOf()
            }
            documentsClassNumMap[classNum]!!.add(document)
        }
        return documentsClassNumMap
    }
}