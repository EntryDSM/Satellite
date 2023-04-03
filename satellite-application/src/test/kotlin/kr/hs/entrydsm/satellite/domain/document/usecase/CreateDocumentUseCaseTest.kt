package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentAlreadyExistException
import kr.hs.entrydsm.satellite.domain.document.spi.DocumentPort
import kr.hs.entrydsm.satellite.domain.library.domain.SchoolYear
import kr.hs.entrydsm.satellite.domain.library.spi.SchoolYearPort
import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student

internal class CreateDocumentUseCaseTest : DescribeSpec({

    val securityPort: SecurityPort = mockk()
    val documentPort: DocumentPort = mockk()
    val majorPort: MajorPort = mockk()
    val schoolYearPort: SchoolYearPort = mockk()

    val createDocumentUseCase = CreateDocumentUseCase(securityPort, documentPort, majorPort, schoolYearPort)

    describe("createDocument") {

        val student = anyValueObject<Student>(
            "grade" to "1",
            "classNum" to "1",
            "number" to "1"
        )
        val schoolYear = anyValueObject<SchoolYear>(
            "year" to 2023
        )
        val major = anyValueObject<Major>()
        val document = getTestDocument(student, major)

        context("아직 문서를 생성하지 않은 학생과 전공의 정보가 주어지면") {

            every { securityPort.getCurrentStudent() } returns student
            every { schoolYearPort.getSchoolYear() } returns schoolYear
            every { documentPort.existByWriterStudentId(student.id) } returns false
            every { majorPort.queryById(major.id) } returns major
            every { documentPort.save(any()) } returns document

            it("문서를 생성한다.") {

                val response = createDocumentUseCase.execute(major.id)
                response shouldBe document.id
            }
        }

        context("이미 문서를 생성한 학생과 전공의 정보가 주어지면") {

            every { securityPort.getCurrentStudent() } returns student
            every { schoolYearPort.getSchoolYear() } returns schoolYear
            every { documentPort.existByWriterStudentId(student.id) } returns true

            it("DocumentAlreadyExist 예외를 던진다.") {

                shouldThrow<DocumentAlreadyExistException> {
                    createDocumentUseCase.execute(major.id)
                }
            }
        }

        context("주어진 id를 가진 Major가 존재하지 않으면") {

            every { securityPort.getCurrentStudent() } returns student
            every { schoolYearPort.getSchoolYear() } returns schoolYear
            every { documentPort.existByWriterStudentId(student.id) } returns false
            every { majorPort.queryById(major.id) } returns null

            it("MajorNotFound 예외를 던진다.") {

                shouldThrow<MajorNotFoundException> {
                    createDocumentUseCase.execute(major.id)
                }
            }
        }
    }
})