package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import io.mockk.coVerify
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student

internal class CancelSubmitMyDocumentUseCaseTest : DescribeSpec({

    val documentPort: DocumentPort = mockk()
    val securityPort: SecurityPort = mockk()

    val cancelSubmitMyDocumentUseCase = CancelSubmitMyDocumentUseCase(securityPort, documentPort)

    describe("cancelSubmitMyDocumentUseCase") {

        val student = anyValueObject<Student>()
        val document = getTestDocument(
            student = student,
            status = DocumentStatus.SUBMITTED
        )

        context("SUBMITTED 상태의 문서를 가진 학생(유저)가 주어지면") {

            val slot = slot<Document>()

            coEvery { securityPort.getCurrentStudent() } returns student
            coEvery { documentPort.queryByWriterStudentId(student.id) } returns document
            coEvery { documentPort.save(capture(slot)) } returnsArgument 0

            it("CREATED 상태로 변경하여 저장한다.") {

                cancelSubmitMyDocumentUseCase.execute()

                coVerify(exactly = 1) { documentPort.save(slot.captured) }
                slot.captured.status shouldBe DocumentStatus.CREATED
            }
        }

        val createdDocument = getTestDocument(
            student = student,
            status = DocumentStatus.CREATED
        )

        context("CREATED 상태의 문서를 가진 학생(유저)가 주어지면") {

            coEvery { securityPort.getCurrentStudent() } returns student
            coEvery { documentPort.queryByWriterStudentId(student.id) } returns createdDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    cancelSubmitMyDocumentUseCase.execute()
                }
                coVerify(exactly = 0) { documentPort.save(any()) }
            }
        }

        val sharedDocument = getTestDocument(
            student = student,
            status = DocumentStatus.SHARED
        )

        context("SHARED 상태의 문서를 가진 학생(유저)가 주어지면") {

            coEvery { securityPort.getCurrentStudent() } returns student
            coEvery { documentPort.queryByWriterStudentId(student.id) } returns sharedDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    cancelSubmitMyDocumentUseCase.execute()
                }
                coVerify(exactly = 0) { documentPort.save(any()) }
            }
        }
    }
})