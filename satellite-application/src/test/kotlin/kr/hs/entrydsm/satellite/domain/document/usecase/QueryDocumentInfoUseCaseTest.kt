package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.coEvery
import io.mockk.mockk
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentAccessRightException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort

internal class QueryDocumentInfoUseCaseTest : DescribeSpec({

    val securityPort: SecurityPort = mockk()
    val documentPort: DocumentPort = mockk()
    val filePort: FilePort = mockk(relaxed = true)

    val queryDocumentInfoUseCase = QueryDocumentInfoUseCase(securityPort, documentPort, filePort)

    describe("queryDocumentInfo") {

        val student = anyValueObject<Student>()
        val otherStudent = anyValueObject<Student>()

        val createdDocument = getTestDocument(
            student = student,
            status = DocumentStatus.CREATED
        )

        context("작성자가 CREATED 상태의 문서를 조회하면") {

            coEvery { securityPort.getCurrentUserAuthority() } returns Authority.STUDENT
            coEvery { securityPort.getCurrentStudent() } returns student
            coEvery { documentPort.queryById(createdDocument.id) } returns createdDocument

            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(createdDocument.id)
                }
            }
        }

        context("작성자가 아닌 학생이 CREATED 상태의 문서를 조회하면") {

            coEvery { securityPort.getCurrentUserAuthority() } returns Authority.STUDENT
            coEvery { securityPort.getCurrentStudent() } returns otherStudent
            coEvery { documentPort.queryById(createdDocument.id) } returns createdDocument

            it("DocumentAccessRight 예외를 던진다.") {
                shouldThrow<DocumentAccessRightException> {
                    queryDocumentInfoUseCase.execute(createdDocument.id)
                }
            }
        }

        context("선생님이 CREATED 상태의 문서를 조회하면") {

            coEvery { securityPort.getCurrentUserAuthority() } returns Authority.TEACHER
            coEvery { documentPort.queryById(createdDocument.id) } returns createdDocument

            it("DocumentAccessRight 예외를 던진다.") {
                shouldThrow<DocumentAccessRightException> {
                    queryDocumentInfoUseCase.execute(createdDocument.id)
                }
            }
        }

        val submittedDocument = getTestDocument(
            student = student,
            status = DocumentStatus.SUBMITTED
        )

        context("작성자가 아닌 학생이 SUBMITTED 상태의 문서를 조회하면") {

            coEvery { securityPort.getCurrentUserAuthority() } returns Authority.STUDENT
            coEvery { securityPort.getCurrentStudent() } returns otherStudent
            coEvery { documentPort.queryById(submittedDocument.id) } returns submittedDocument

            it("DocumentAccessRight 예외를 던진다.") {
                shouldThrow<DocumentAccessRightException> {
                    queryDocumentInfoUseCase.execute(submittedDocument.id)
                }
            }
        }

        context("선생님이 SUBMITTED 상태의 문서를 조회하면") {

            coEvery { securityPort.getCurrentUserAuthority() } returns Authority.TEACHER
            coEvery { documentPort.queryById(submittedDocument.id) } returns submittedDocument

            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(submittedDocument.id)
                }
            }
        }

        val sharedDocument = getTestDocument(
            student = student,
            status = DocumentStatus.SHARED
        )

        context("작성자가 아닌 학생이 SHARED 상태의 문서를 조회하면") {

            coEvery { securityPort.getCurrentUserAuthority() } returns Authority.STUDENT
            coEvery { securityPort.getCurrentStudent() } returns otherStudent
            coEvery { documentPort.queryById(sharedDocument.id) } returns sharedDocument

            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(sharedDocument.id)
                }
            }
        }

        context("선생님이 SHARED 상태의 문서를 조회하면") {

            coEvery { securityPort.getCurrentUserAuthority() } returns Authority.TEACHER
            coEvery { documentPort.queryById(sharedDocument.id) } returns sharedDocument
            
            it("문서의 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryDocumentInfoUseCase.execute(sharedDocument.id)
                }
            }
        }
    }
})