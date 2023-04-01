package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.document.persistence.Document
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateCertificateRequest
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateCertificateRequest.CertificateRequest
import kr.hs.entrydsm.satellite.domain.student.persistence.Student
import kr.hs.entrydsm.satellite.common.security.SecurityUtil

internal class UpdateCertificateUseCaseTest : DescribeSpec({

    val documentRepository: DocumentRepository = mockk()
    mockkObject(SecurityUtil)

    val updateCertificateUseCase = UpdateCertificateUseCase(documentRepository)

    describe("updateCertificate") {

        val student = anyValueObject<Student>("number" to "1")
        val document = getTestDocument(student)

        val request = anyValueObject<UpdateCertificateRequest>(
            "certificateList" to listOf(anyValueObject<CertificateRequest>())
        )

        context("자격증 데이터를 받으면") {

            val slot = slot<Document>()

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.queryByWriterStudentId(student.id) } returns document
            every { documentRepository.save(capture(slot)) } returnsArgument 0

            it("본인(학생) 문서의 자격증 정보를 수정한다.") {

                updateCertificateUseCase.execute(request)
                onlyCertificateIsDifferent(slot, document)
            }
        }
    }
})

private fun onlyCertificateIsDifferent(
    slot: CapturingSlot<Document>,
    document: Document
) {
    slot.captured.id shouldBe document.id
    slot.captured.writer shouldBe document.writer
    slot.captured.introduce shouldBe document.introduce
    slot.captured.skillSet shouldBe document.skillSet
    slot.captured.projectList shouldBe document.projectList
    slot.captured.awardList shouldBe document.awardList
    slot.captured.certificateList shouldNotBe document.certificateList
}