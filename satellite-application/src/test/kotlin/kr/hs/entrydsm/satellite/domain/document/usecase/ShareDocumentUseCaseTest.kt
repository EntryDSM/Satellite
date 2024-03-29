package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.feedback.domain.Feedback
import kr.hs.entrydsm.satellite.domain.feedback.spi.FeedbackPort

internal class ShareDocumentUseCaseTest : DescribeSpec({

    val documentPort: DocumentPort = mockk()
    val feedbackPort: FeedbackPort = mockk(relaxed = true)

    val shareDocumentUseCase = ShareDocumentUseCase(documentPort, feedbackPort)

    describe("shareDocument") {

        val document = getTestDocument(status = DocumentStatus.SUBMITTED)
        val feedbackList = listOf<Feedback>()

        context("SUBMITTED 상태인 문서의 id가 주어지면") {

            val slot = slot<Document>()

            coEvery { documentPort.queryById(document.id) } returns document
            coEvery { documentPort.save(capture(slot)) } returnsArgument 0
            coEvery { feedbackPort.queryByDocumentId(document.id) } returns feedbackList
            coJustRun { feedbackPort.deleteByDocumentId(document.id) }

            it("해당 문서를 SHARED 상태로 변경하여 저장한다.") {

                shareDocumentUseCase.execute(document.id)

                coVerify(exactly = 1) { documentPort.save(slot.captured) }
                slot.captured.status shouldBe DocumentStatus.SHARED

                coVerify(exactly = 1) { feedbackPort.deleteByDocumentId(document.id) }
            }
        }

        val createdDocument = getTestDocument(status = DocumentStatus.CREATED)

        context("CREATED 상태인 문서의 id가 주어지면") {

            coEvery { documentPort.queryById(createdDocument.id) } returns createdDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    shareDocumentUseCase.execute(createdDocument.id)
                }
                coVerify(exactly = 0) { documentPort.save(any()) }
            }
        }

        val sharedDocument = getTestDocument(status = DocumentStatus.SHARED)

        context("SHARED 상태인 문서의 id가 주어지면") {

            coEvery { documentPort.queryById(sharedDocument.id) } returns sharedDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    shareDocumentUseCase.execute(sharedDocument.id)
                }
                coVerify(exactly = 0) { documentPort.save(any()) }
            }
        }
    }
})