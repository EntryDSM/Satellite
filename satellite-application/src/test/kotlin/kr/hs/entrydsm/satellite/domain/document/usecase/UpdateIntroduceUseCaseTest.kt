package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.dto.IntroduceRequest
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student

internal class UpdateIntroduceUseCaseTest : DescribeSpec({

    val securityPort: SecurityPort = mockk()
    val documentPort: DocumentPort = mockk()

    val updateIntroduceUseCase = UpdateIntroduceUseCase(securityPort, documentPort)

    describe("updateIntroduce") {

        val student = anyValueObject<Student>()
        val document = getTestDocument(student)

        val request = IntroduceRequest("qwe", "ty")

        context("자기소개 데이터를 받으면") {

            val slot = slot<Document>()

            every { securityPort.getCurrentStudent() } returns student
            every { documentPort.queryByWriterStudentId(student.id) } returns document
            every { documentPort.save(capture(slot)) } returnsArgument 0

            it("본인(학생) 문서의 자기소개 정보를 수정한다.") {

                updateIntroduceUseCase.execute(request)
                onlyIntroduceIsDifferent(slot, document)
            }
        }
    }
})

private fun onlyIntroduceIsDifferent(
    slot: CapturingSlot<Document>,
    document: Document
) {
    slot.captured.id shouldBe document.id
    slot.captured.writer shouldBe document.writer
    slot.captured.introduce shouldNotBe document.introduce
    slot.captured.skillSet shouldBe document.skillSet
    slot.captured.projectList shouldBe document.projectList
    slot.captured.awardList shouldBe document.awardList
    slot.captured.certificateList shouldBe document.certificateList
}