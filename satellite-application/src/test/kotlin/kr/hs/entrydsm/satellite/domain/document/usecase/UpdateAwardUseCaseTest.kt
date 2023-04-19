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
import kr.hs.entrydsm.satellite.domain.document.dto.AwardRequest
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.student.domain.StudentDomain

internal class UpdateAwardUseCaseTest : DescribeSpec({

    val securityPort: SecurityPort = mockk()
    val documentPort: DocumentPort = mockk()

    val updateAwardUseCase = UpdateAwardUseCase(securityPort, documentPort)

    describe("updateAward") {

        val student = anyValueObject<StudentDomain>()
        val document = getTestDocument(student)

        val request = listOf(anyValueObject<AwardRequest>())

        context("수상경력 데이터를 받으면") {

            val slot = slot<Document>()

            coEvery { securityPort.getCurrentStudent() } returns student
            coEvery { documentPort.queryByWriterStudentId(student.id) } returns document.copy()
            coEvery { documentPort.save(capture(slot)) } returnsArgument 0

            it("본인(학생) 문서의 정보를 수정한다.") {

                updateAwardUseCase.execute(request)
                onlyAwardIsDifferent(slot, document)
            }
        }
    }
})

private fun onlyAwardIsDifferent(
    slot: CapturingSlot<Document>,
    document: Document
) {
    slot.captured.id shouldBe document.id
    slot.captured.writer shouldBe document.writer
    slot.captured.introduce shouldBe document.introduce
    slot.captured.skillSet shouldBe document.skillSet
    slot.captured.projectList shouldBe document.projectList
    slot.captured.awardList.size shouldNotBe document.awardList.size
    slot.captured.certificateList shouldBe document.certificateList
}