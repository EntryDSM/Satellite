package kr.hs.entrydsm.repo.domain.document.usecase

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kr.hs.entrydsm.repo.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.repo.common.getTestDocument
import kr.hs.entrydsm.repo.domain.company.persistence.Company
import kr.hs.entrydsm.repo.domain.document.exception.DocumentAccessRightException
import kr.hs.entrydsm.repo.domain.document.persistence.enums.Status
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.domain.student.persistence.Student
import kr.hs.entrydsm.repo.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.repo.domain.teacher.persistence.Teacher
import kr.hs.entrydsm.repo.global.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull

internal class QueryDocumentInfoUseCaseTest : DescribeSpec({

    val documentRepository: DocumentRepository = mockk()
    val studentRepository: StudentRepository = mockk()
    mockkObject(SecurityUtil)

    val queryDocumentInfoUseCase = QueryDocumentInfoUseCase(documentRepository, studentRepository)

    describe("queryDocumentInfo") {

        val student = anyValueObject<Student>(
            "grade" to "1",
            "classNum" to "1",
            "number" to "1"
        )
        val otherStudent = anyValueObject<Student>()
        val teacher = anyValueObject<Teacher>()
        val company = anyValueObject<Company>()

        val createdDocument = getTestDocument(
            student = student,
            status = Status.CREATED
        )

        context("작성자가 CREATED 상태의 문서를 조회하면") {

            every { SecurityUtil.getCurrentUserAuthority() } returns kr.hs.entrydsm.repo.domain.auth.constant.Authority.STUDENT
            every { documentRepository.findByIdOrNull(createdDocument.id) } returns createdDocument
            every { SecurityUtil.getCurrentUserId() } returns student.id
            every { studentRepository.findByIdOrNull(student.id) } returns student

            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(createdDocument.id)
                }
            }
        }

        context("작성자가 아닌 학생이 CREATED 상태의 문서를 조회하면") {

            every { SecurityUtil.getCurrentUserAuthority() } returns kr.hs.entrydsm.repo.domain.auth.constant.Authority.STUDENT
            every { documentRepository.findByIdOrNull(createdDocument.id) } returns createdDocument
            every { SecurityUtil.getCurrentUserId() } returns otherStudent.id
            every { studentRepository.findByIdOrNull(otherStudent.id) } returns otherStudent

            it("DocumentAccessRight 예외를 던진다.") {
                shouldThrow<DocumentAccessRightException> {
                    queryDocumentInfoUseCase.execute(createdDocument.id)
                }
            }
        }

        context("선생님이 CREATED 상태의 문서를 조회하면") {

            every { SecurityUtil.getCurrentUserAuthority() } returns kr.hs.entrydsm.repo.domain.auth.constant.Authority.TEACHER
            every { documentRepository.findByIdOrNull(createdDocument.id) } returns createdDocument
            every { SecurityUtil.getCurrentUserId() } returns teacher.id
            every { studentRepository.findByIdOrNull(teacher.id) } returns null

            it("DocumentAccessRight 예외를 던진다.") {
                shouldThrow<DocumentAccessRightException> {
                    queryDocumentInfoUseCase.execute(createdDocument.id)
                }
            }
        }

        context("회사가 CREATED 상태의 문서를 조회하면") {

            every { SecurityUtil.getCurrentUserAuthority() } returns kr.hs.entrydsm.repo.domain.auth.constant.Authority.COMPANY
            every { documentRepository.findByIdOrNull(createdDocument.id) } returns createdDocument
            every { SecurityUtil.getCurrentUserId() } returns company.id
            every { studentRepository.findByIdOrNull(company.id) } returns null

            it("DocumentAccessRight 예외를 던진다.") {
                shouldThrow<DocumentAccessRightException> {
                    queryDocumentInfoUseCase.execute(createdDocument.id)
                }
            }
        }

        val submittedDocument = getTestDocument(
            student = student,
            status = Status.SUBMITTED
        )

        context("작성자가 아닌 학생이 SUBMITTED 상태의 문서를 조회하면") {

            every { SecurityUtil.getCurrentUserAuthority() } returns kr.hs.entrydsm.repo.domain.auth.constant.Authority.STUDENT
            every { documentRepository.findByIdOrNull(submittedDocument.id) } returns submittedDocument
            every { SecurityUtil.getCurrentUserId() } returns otherStudent.id
            every { studentRepository.findByIdOrNull(otherStudent.id) } returns otherStudent

            it("DocumentAccessRight 예외를 던진다.") {
                shouldThrow<DocumentAccessRightException> {
                    queryDocumentInfoUseCase.execute(submittedDocument.id)
                }
            }
        }

        context("선생님이 SUBMITTED 상태의 문서를 조회하면") {

            every { SecurityUtil.getCurrentUserAuthority() } returns kr.hs.entrydsm.repo.domain.auth.constant.Authority.TEACHER
            every { documentRepository.findByIdOrNull(submittedDocument.id) } returns submittedDocument
            every { SecurityUtil.getCurrentUserId() } returns teacher.id
            every { studentRepository.findByIdOrNull(teacher.id) } returns null

            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(submittedDocument.id)
                }
            }
        }

        context("회사가 SUBMITTED 상태의 문서를 조회하면") {

            every { SecurityUtil.getCurrentUserAuthority() } returns kr.hs.entrydsm.repo.domain.auth.constant.Authority.COMPANY
            every { documentRepository.findByIdOrNull(submittedDocument.id) } returns submittedDocument
            every { SecurityUtil.getCurrentUserId() } returns company.id
            every { studentRepository.findByIdOrNull(company.id) } returns null

            it("DocumentAccessRight 예외를 던진다.") {
                shouldThrow<DocumentAccessRightException> {
                    queryDocumentInfoUseCase.execute(submittedDocument.id)
                }
            }
        }

        val sharedDocument = getTestDocument(
            student = student,
            status = Status.SHARED
        )

        context("작성자가 아닌 학생이 SHARED 상태의 문서를 조회하면") {

            every { SecurityUtil.getCurrentUserAuthority() } returns kr.hs.entrydsm.repo.domain.auth.constant.Authority.STUDENT
            every { documentRepository.findByIdOrNull(sharedDocument.id) } returns sharedDocument
            every { SecurityUtil.getCurrentUserId() } returns otherStudent.id
            every { studentRepository.findByIdOrNull(otherStudent.id) } returns otherStudent

            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(sharedDocument.id)
                }
            }
        }

        context("선생님이 SHARED 상태의 문서를 조회하면") {

            every { SecurityUtil.getCurrentUserAuthority() } returns kr.hs.entrydsm.repo.domain.auth.constant.Authority.TEACHER
            every { documentRepository.findByIdOrNull(sharedDocument.id) } returns sharedDocument
            every { SecurityUtil.getCurrentUserId() } returns teacher.id
            every { studentRepository.findByIdOrNull(teacher.id) } returns null

            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(sharedDocument.id)
                }
            }
        }

        context("회사가 SHARED 상태의 문서를 조회하면") {

            every { SecurityUtil.getCurrentUserAuthority() } returns kr.hs.entrydsm.repo.domain.auth.constant.Authority.COMPANY
            every { documentRepository.findByIdOrNull(sharedDocument.id) } returns sharedDocument
            every { SecurityUtil.getCurrentUserId() } returns company.id
            every { studentRepository.findByIdOrNull(company.id) } returns null

            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(sharedDocument.id)
                }
            }
        }
    }
})
