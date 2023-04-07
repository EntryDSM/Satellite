package kr.hs.entrydsm.satellite.global.thirdparty.pdf.util

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider
import com.itextpdf.io.font.FontProgramFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.utils.PdfMerger
import kr.hs.entrydsm.satellite.global.thirdparty.pdf.PdfFont
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

object PdfUtil {

    fun concatPdf(vararg documents: ByteArrayOutputStream) = concatPdf(documents.toList())

    fun concatPdf(documents: List<ByteArrayOutputStream>): ByteArrayOutputStream {

        val outputStream = ByteArrayOutputStream()

        val mergedDocument = PdfDocument(PdfWriter(outputStream))
        val merger = PdfMerger(mergedDocument)

        documents.forEach { document ->
            val pdf = PdfDocument(PdfReader(ByteArrayInputStream(document.toByteArray())))
            mergePdf(merger, pdf)
        }

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