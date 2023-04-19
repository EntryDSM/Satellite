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
import kr.hs.entrydsm.satellite.domain.document.dto.ProjectRequest
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.student.domain.StudentDomain

internal class UpdateProjectUseCaseTest : DescribeSpec({

    val securityPort: SecurityPort = mockk()
    val documentPort: DocumentPort = mockk()

    val updateProjectUseCase = UpdateProjectUseCase(securityPort, documentPort)

    describe("updateProject") {

        val student = anyValueObject<StudentDomain>()
        val document = getTestDocument(student)

        val request = listOf(anyValueObject<ProjectRequest>())

        context("프로젝트 데이터를 받으면") {

            val slot = slot<Document>()

            coEvery { securityPort.getCurrentStudent() } returns student
            coEvery { documentPort.queryByWriterStudentId(student.id) } returns document.copy()
            coEvery { documentPort.save(capture(slot)) } returnsArgument 0

            it("본인(학생) 문서의 프로젝트 정보를 수정한다.") {

                updateProjectUseCase.execute(request)
                onlyProjectIsDifferent(slot, document)
            }
        }
    }
})

private fun onlyProjectIsDifferent(
    slot: CapturingSlot<Document>,
    document: Document
) {
    slot.captured.id shouldBe document.id
    slot.captured.writer shouldBe document.writer
    slot.captured.introduce shouldBe document.introduce
    slot.captured.skillSet shouldBe document.skillSet
    slot.captured.projectList shouldNotBe document.projectList
    slot.captured.awardList shouldBe document.awardList
    slot.captured.certificateList shouldBe document.certificateList
}