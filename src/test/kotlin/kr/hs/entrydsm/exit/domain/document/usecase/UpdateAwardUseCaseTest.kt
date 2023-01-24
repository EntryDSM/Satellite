package kr.hs.entrydsm.exit.domain.document.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.*
import kr.hs.entrydsm.exit.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.exit.common.getTestDocument
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.UpdateAwardRequest
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.UpdateAwardRequest.AwardRequest
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import kr.hs.entrydsm.exit.global.security.SecurityUtil

internal class UpdateAwardUseCaseTest : DescribeSpec({

    val documentRepository: DocumentRepository = mockk()
    mockkObject(SecurityUtil)

    val updateAwardUseCase = UpdateAwardUseCase(documentRepository)

    describe("updateAward") {

        val student = anyValueObject<Student>()
        val document = getTestDocument(student)

        val request = anyValueObject<UpdateAwardRequest>(
            "awardList" to listOf(anyValueObject<AwardRequest>())
        )

        context("수상경력 데이터를 받으면") {

            val slot = slot<Document>()

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.findByWriterStudentId(student.id) } returns document
            every { documentRepository.save(capture(slot)) } returnsArgument 0

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
    slot.captured.awardList shouldNotBe document.awardList
    slot.captured.certificateList shouldBe document.certificateList
}