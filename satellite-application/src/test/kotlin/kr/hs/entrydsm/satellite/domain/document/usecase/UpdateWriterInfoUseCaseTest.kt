package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.dto.WriterInfoRequest
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort

internal class UpdateWriterInfoUseCaseTest : DescribeSpec({

    val securityPort: SecurityPort = mockk()
    val documentPort: DocumentPort = mockk()
    val studentPort: StudentPort = mockk()
    val majorPort: MajorPort = mockk()

    val updateWriterInfoUseCase = UpdateWriterInfoUseCase(securityPort, studentPort, documentPort, majorPort)

    describe("updateWriterInfo") {

        val student = anyValueObject<Student>("number" to "1")
        val document = getTestDocument(student)
        val major = anyValueObject<Major>()

        val request = anyValueObject<WriterInfoRequest>()

        context("작성자 정보 데이터를 받으면") {

            val slot = slot<Document>()

            every { securityPort.getCurrentStudent() } returns student
            every { documentPort.queryByWriterStudentId(student.id) } returns document
            every { majorPort.queryById(request.majorId) } returns major
            every { documentPort.save(capture(slot)) } returnsArgument 0
            every { studentPort.save(any()) } returnsArgument 0

            it("본인(학생) 문서의 작성자 정보를 수정한다.") {

                updateWriterInfoUseCase.execute(request)

                verify(exactly = 1) { documentPort.save(slot.captured) }
                verify(exactly = 1) { studentPort.save(any()) }
                onlyWriterIsDifferent(slot, document)
            }
        }
    }
})

private fun onlyWriterIsDifferent(
    slot: CapturingSlot<Document>,
    document: Document
) {
    slot.captured.id shouldBe document.id
    slot.captured.writer shouldNotBe document.writer
    slot.captured.introduce shouldBe document.introduce
    slot.captured.skillSet shouldBe document.skillSet
    slot.captured.projectList shouldBe document.projectList
    slot.captured.awardList shouldBe document.awardList
    slot.captured.certificateList shouldBe document.certificateList
}