package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentIllegalStatusException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort

internal class CancelShareDocumentUseCaseTest : DescribeSpec({

    val documentPort: DocumentPort = mockk()

    val cancelShareDocumentUseCase = CancelShareDocumentUseCase(documentPort)

    describe("cancelShareDocument") {

        val document = getTestDocument(status = DocumentStatus.SHARED)

        context("SHARED 상태인 문서의 id가 주어지면") {

            val slot = slot<Document>()

            coEvery { documentPort.queryById(document.id) } returns document
            coEvery { documentPort.save(capture(slot)) } returnsArgument 0

            it("SUBMITTED 상태로 변경하여 저장한다.") {

                cancelShareDocumentUseCase.execute(document.id)

                coVerify(exactly = 1) { documentPort.save(slot.captured) }
                slot.captured.status shouldBe DocumentStatus.SUBMITTED
            }
        }

        val createdDocument = getTestDocument(status = DocumentStatus.CREATED)

        context("CREATED 상태인 문서의 id가 주어지면") {

            coEvery { documentPort.queryById(createdDocument.id) } returns createdDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    cancelShareDocumentUseCase.execute(createdDocument.id)
                }
                coVerify(exactly = 0) { documentPort.save(any()) }
            }
        }

        val submittedDocument = getTestDocument(status = DocumentStatus.SUBMITTED)

        context("SUBMITTED 상태인 문서의 id가 주어지면") {

            coEvery { documentPort.queryById(submittedDocument.id) } returns submittedDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<DocumentIllegalStatusException> {
                    cancelShareDocumentUseCase.execute(submittedDocument.id)
                }
                coVerify(exactly = 0) { documentPort.save(any()) }
            }
        }
    }
})