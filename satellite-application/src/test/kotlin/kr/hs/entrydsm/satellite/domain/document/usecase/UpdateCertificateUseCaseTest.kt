package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.dto.CertificateRequest
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.student.domain.StudentDomain

internal class UpdateCertificateUseCaseTest : DescribeSpec({

    val securityPort: SecurityPort = mockk()
    val documentPort: DocumentPort = mockk()

    val updateCertificateUseCase = UpdateCertificateUseCase(securityPort, documentPort)

    describe("updateCertificate") {

        val student = anyValueObject<StudentDomain>()
        val document = getTestDocument(student)

        val request = listOf(anyValueObject<CertificateRequest>())

        context("자격증 데이터를 받으면") {

            val slot = slot<Document>()

            coEvery { securityPort.getCurrentStudent() } returns student
            coEvery { documentPort.queryByWriterStudentId(student.id) } returns document.copy()
            coEvery { documentPort.save(capture(slot)) } returnsArgument 0

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
    slot.captured.certificateList.size shouldNotBe document.certificateList.size
}