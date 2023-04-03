package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student

internal class SubmitMyDocumentUseCaseTest : DescribeSpec({

    val securityPort: SecurityPort = mockk()
    val documentPort: DocumentPort = mockk()

    val submitMyDocumentUseCase = SubmitMyDocumentUseCase(securityPort, documentPort)

    describe("submitMyDocumentUseCase") {

        val student = anyValueObject<Student>("number" to "1")
        val document = getTestDocument(
            student = student,
            status = DocumentStatus.CREATED
        )

        context("CREATED 상태의 문서를 가진 학생(유저)가 주어지면") {

            val slot = slot<Document>()

            every { securityPort.getCurrentStudent() } returns student
            every { documentPort.queryByWriterStudentId(student.id) } returns document
            every { documentPort.save(capture(slot)) } returnsArgument 0

            it("SUBMITTED 상태로 변경하여 저장한다.") {

                submitMyDocumentUseCase.execute()

                verify(exactly = 1) { documentPort.save(slot.captured) }
                slot.captured.status shouldBe DocumentStatus.SUBMITTED
            }
        }

        val submittedDocument = getTestDocument(
            student = student,
            status = DocumentStatus.SUBMITTED
        )

        context("SUBMITTED 상태의 문서를 가진 학생(유저)가 주어지면") {

            every { securityPort.getCurrentStudent() } returns student
            every { documentPort.queryByWriterStudentId(student.id) } returns submittedDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    submitMyDocumentUseCase.execute()
                }
                verify(exactly = 0) { documentPort.save(any()) }
            }
        }

        val sharedDocument = getTestDocument(
            student = student,
            status = DocumentStatus.SHARED
        )

        context("SHARED 상태의 문서를 가진 학생(유저)가 주어지면") {

            every { securityPort.getCurrentStudent() } returns student
            every { documentPort.queryByWriterStudentId(student.id) } returns sharedDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    submitMyDocumentUseCase.execute()
                }
                verify(exactly = 0) { documentPort.save(any()) }
            }
        }
    }
})