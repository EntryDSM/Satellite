package kr.hs.entrydsm.repo.domain.library.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.domain.library.persistence.AccessRight
import kr.hs.entrydsm.repo.domain.library.persistence.LibraryDocument
import kr.hs.entrydsm.repo.domain.library.persistence.repository.LibraryDocumentRepository
import kr.hs.entrydsm.repo.domain.library.presentation.dto.CreateLibraryFileResponse
import kr.hs.entrydsm.repo.domain.school.facade.SchoolYearFacade
import kr.hs.entrydsm.repo.domain.school.properties.SchoolYearProperties
import kr.hs.entrydsm.repo.global.exception.ForbiddenException
import kr.hs.entrydsm.repo.global.thirdparty.aws.s3.AwsS3Adapter
import kr.hs.entrydsm.repo.global.thirdparty.pdf.PdfAdapter
import kr.hs.entrydsm.repo.global.util.FileUtil.toFileDateFormat
import org.springframework.security.crypto.password.PasswordEncoder
import java.io.File
import java.time.LocalDateTime

@UseCase
class CreateLibraryFileUseCase(
    private val passwordEncoder: PasswordEncoder,
    private val schoolYearProperties: SchoolYearProperties,
    private val schoolYearFacade: SchoolYearFacade,
    private val pdfAdapter: PdfAdapter,
    private val awsS3Adapter: AwsS3Adapter,
    private val documentRepository: DocumentRepository,
    private val libraryDocumentRepository: LibraryDocumentRepository
) {
    fun execute(grade: Int, secret: String): CreateLibraryFileResponse {

        if (passwordEncoder.matches(secret, schoolYearProperties.secret)) {
            throw ForbiddenException
        }

        val year = schoolYearFacade.getSchoolYear()
        val documents = documentRepository.findByYearAndWriterGrade(year, grade.toString())
        documentRepository.saveAll(
            documents.map { it.delete() }
        )

        val bytes = pdfAdapter.generateGradeLibraryDocument(documents)
        val filePath = awsS3Adapter.savePdf(
            File("${year}_${grade}_${LocalDateTime.now().toFileDateFormat()}")
                .apply { writeBytes(bytes) }
        )

        val libraryDocument = libraryDocumentRepository.save(
            LibraryDocument(
                year = year,
                grade = grade,
                fileUrl = filePath,
                accessRight = AccessRight.PRIVATE
            )
        )
        return CreateLibraryFileResponse(libraryDocument.id)
    }
}