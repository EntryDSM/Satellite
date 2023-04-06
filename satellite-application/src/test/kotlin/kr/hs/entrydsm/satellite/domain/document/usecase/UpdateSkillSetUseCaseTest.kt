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
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student

internal class UpdateSkillSetUseCaseTest : DescribeSpec({

    val securityPort: SecurityPort = mockk()
    val documentPort: DocumentPort = mockk()

    val updateSkillSetUseCase = UpdateSkillSetUseCase(securityPort, documentPort)

    describe("updateSkillSet") {

        val student = anyValueObject<Student>()
        val document = getTestDocument(student)

        val request = listOf("Backend")

        context("스킬셋 데이터를 받으면") {

            val slot = slot<Document>()

            every { securityPort.getCurrentStudent() } returns student
            every { documentPort.queryByWriterStudentId(student.id) } returns document
            every { documentPort.save(capture(slot)) } returnsArgument 0

            it("본인(학생) 문서의 스킬셋 정보를 수정한다.") {

                updateSkillSetUseCase.execute(request)
                onlySkillSetIsDifferent(slot, document)
            }
        }
    }
})

private fun onlySkillSetIsDifferent(
    slot: CapturingSlot<Document>,
    document: Document
) {
    slot.captured.id shouldBe document.id
    slot.captured.writer shouldBe document.writer
    slot.captured.introduce shouldBe document.introduce
    slot.captured.skillSet shouldNotBe document.skillSet
    slot.captured.projectList shouldBe document.projectList
    slot.captured.awardList shouldBe document.awardList
    slot.captured.certificateList shouldBe document.certificateList
}