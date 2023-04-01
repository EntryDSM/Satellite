package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.verify
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.persistence.Document
import kr.hs.entrydsm.satellite.domain.document.persistence.enums.Status
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.student.persistence.Student
import kr.hs.entrydsm.satellite.common.security.SecurityUtil

internal class SubmitMyDocumentUseCaseTest : DescribeSpec({

    val documentRepository: DocumentRepository = mockk()
    mockkObject(SecurityUtil)

    val submitMyDocumentUseCase = SubmitMyDocumentUseCase(documentRepository)

    describe("submitMyDocumentUseCase") {

        val student = anyValueObject<Student>("number" to "1")
        val document = getTestDocument(
            student = student,
            status = Status.CREATED
        )

        context("CREATED 상태의 문서를 가진 학생(유저)가 주어지면") {

            val slot = slot<Document>()

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.queryByWriterStudentId(student.id) } returns document
            every { documentRepository.save(capture(slot)) } returnsArgument 0

            it("SUBMITTED 상태로 변경하여 저장한다.") {

                submitMyDocumentUseCase.execute()

                verify(exactly = 1) { documentRepository.save(slot.captured) }
                slot.captured.status shouldBe Status.SUBMITTED
            }
        }

        val submittedDocument = getTestDocument(
            student = student,
            status = Status.SUBMITTED
        )

        context("SUBMITTED 상태의 문서를 가진 학생(유저)가 주어지면") {

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.queryByWriterStudentId(student.id) } returns submittedDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    submitMyDocumentUseCase.execute()
                }
                verify(exactly = 0) { documentRepository.save(any()) }
            }
        }

        val sharedDocument = getTestDocument(
            student = student,
            status = Status.SHARED
        )

        context("SHARED 상태의 문서를 가진 학생(유저)가 주어지면") {

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.queryByWriterStudentId(student.id) } returns sharedDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    submitMyDocumentUseCase.execute()
                }
                verify(exactly = 0) { documentRepository.save(any()) }
            }
        }
    }
})