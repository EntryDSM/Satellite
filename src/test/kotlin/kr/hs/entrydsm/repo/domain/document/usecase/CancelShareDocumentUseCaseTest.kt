package kr.hs.entrydsm.repo.domain.document.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kr.hs.entrydsm.repo.common.getTestDocument
import kr.hs.entrydsm.repo.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.domain.document.persistence.enums.Status
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import org.springframework.data.repository.findByIdOrNull

internal class CancelShareDocumentUseCaseTest : DescribeSpec({

    val documentRepository: DocumentRepository = mockk()

    val cancelShareDocumentUseCase = CancelShareDocumentUseCase(documentRepository)

    describe("cancelShareDocument") {

        val document = getTestDocument(status = Status.SHARED)

        context("SHARED 상태인 문서의 id가 주어지면") {

            val slot = slot<Document>()

            every { documentRepository.findByIdOrNull(document.id) } returns document
            every { documentRepository.save(capture(slot)) } returnsArgument 0

            it("SUBMITTED 상태로 변경하여 저장한다.") {

                cancelShareDocumentUseCase.execute(document.id)

                verify(exactly = 1) { documentRepository.save(slot.captured) }
                slot.captured.status shouldBe Status.SUBMITTED
            }
        }

        val createdDocument = getTestDocument(status = Status.CREATED)

        context("CREATED 상태인 문서의 id가 주어지면") {

            every { documentRepository.findByIdOrNull(createdDocument.id) } returns createdDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    cancelShareDocumentUseCase.execute(createdDocument.id)
                }
                verify(exactly = 0) { documentRepository.save(any()) }
            }
        }

        val submittedDocument = getTestDocument(status = Status.SUBMITTED)

        context("SUBMITTED 상태인 문서의 id가 주어지면") {

            every { documentRepository.findByIdOrNull(submittedDocument.id) } returns submittedDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    cancelShareDocumentUseCase.execute(submittedDocument.id)
                }
                verify(exactly = 0) { documentRepository.save(any()) }
            }
        }
    }
})