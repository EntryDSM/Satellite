package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.common.util.FileUtil.toFileDateFormat
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import kr.hs.entrydsm.satellite.domain.file.spi.PdfPort
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.LibraryDocument
import kr.hs.entrydsm.satellite.domain.library.exception.SecretMismatchException
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort
import kr.hs.entrydsm.satellite.domain.library.spi.SchoolYearPort
import java.io.File
import java.time.LocalDateTime
import java.util.*

@UseCase
class CreateLibraryFileUseCase(
    private val schoolYearPort: SchoolYearPort,
    private val pdfPort: PdfPort,
    private val filePort: FilePort,
    private val documentPort: DocumentPort,
    private val libraryDocumentPort: LibraryDocumentPort
) {
    fun execute(grade: Int, secret: String): UUID {

        if (schoolYearPort.secretMatches(secret)) {
            throw SecretMismatchException
        }

        val year = schoolYearPort.getSchoolYear().year
        val documents = documentPort.queryByYearAndWriterGrade(year, grade)

        /*
        documentPort.saveAll(
            documents.map { it.delete() }
        )
        */

        val bytes = pdfPort.generateGradeLibraryDocument(documents)
        val filePath = filePort.savePdf(
            File("${year}_${grade}_${LocalDateTime.now().toFileDateFormat()}.pdf")
                .apply { writeBytes(bytes) }
        )

        val libraryDocumentJpaEntity = libraryDocumentPort.save(
            LibraryDocument(
                year = year,
                grade = grade,
                filePath = filePath,
                accessRight = AccessRight.PRIVATE
            )
        )
        return libraryDocumentJpaEntity.id
    }
}