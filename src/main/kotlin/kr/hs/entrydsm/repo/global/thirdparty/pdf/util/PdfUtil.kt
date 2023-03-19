package kr.hs.entrydsm.repo.global.thirdparty.pdf.util

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider
import com.itextpdf.io.font.FontProgramFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.utils.PdfMerger
import java.io.ByteArrayInputStream
import java.io.IOException
import kr.hs.entrydsm.repo.global.thirdparty.pdf.PdfFont

object PdfUtil {

    fun concatPdf(first: ByteArrayOutputStream?, second: ByteArrayOutputStream?): ByteArrayOutputStream {

        val outputStream = ByteArrayOutputStream()
        val mergedDocument = PdfDocument(PdfWriter(outputStream))
        val merger = PdfMerger(mergedDocument)

        val firstDocument = PdfDocument(PdfReader(ByteArrayInputStream(first?.toByteArray())))
        mergePdf(merger, firstDocument)

        val secondDocument = PdfDocument(PdfReader(ByteArrayInputStream(second?.toByteArray())))
        mergePdf(merger, secondDocument)

        mergedDocument.close()
        return outputStream
    }

    private fun mergePdf(merger: PdfMerger, document: PdfDocument) {
        merger.merge(document, 1, document.numberOfPages)
        document.close()
    }

    fun convertHtmlToPdf(html: String): ByteArrayOutputStream {
        val outputStream = ByteArrayOutputStream()
        val converterProperties = createConverterProperties()
        HtmlConverter.convertToPdf(html, outputStream, converterProperties)
        return outputStream
    }

    private fun createConverterProperties(): ConverterProperties {
        val fontProvider = DefaultFontProvider(false, false, false)
        listOf(
            PdfFont.PRETENDARD_LIGHT,
            PdfFont.PRETENDARD_MEDIUM,
            PdfFont.PRETENDARD_BOLD
        ).forEach { font ->
            try {
                fontProvider.addFont(
                    FontProgramFactory.createFont(font)
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return ConverterProperties().apply { this.fontProvider = fontProvider }
    }
}