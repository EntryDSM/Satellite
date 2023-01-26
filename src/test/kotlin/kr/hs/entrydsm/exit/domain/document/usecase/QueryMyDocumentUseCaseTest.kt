package kr.hs.entrydsm.exit.domain.document.usecase

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kr.hs.entrydsm.exit.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.exit.common.getTestDocument
import kr.hs.entrydsm.exit.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.element.IntroduceElement
import kr.hs.entrydsm.exit.domain.document.persistence.element.WriterInfoElement
import kr.hs.entrydsm.exit.domain.document.persistence.enums.Status
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.feedback.persistence.Feedback
import kr.hs.entrydsm.exit.domain.feedback.persistence.repository.FeedbackRepository
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import kr.hs.entrydsm.exit.global.security.SecurityUtil
import java.util.UUID.randomUUID

internal class QueryMyDocumentUseCaseTest : DescribeSpec({

    val documentRepository: DocumentRepository = mockk()
    val feedbackRepository: FeedbackRepository = mockk()
    mockkObject(SecurityUtil)

    val queryMyDocumentInfoUseCase = QueryMyDocumentInfoUseCase(documentRepository, feedbackRepository)

    describe("updateAward") {

        val student = anyValueObject<Student>(
            "number" to "1"
        )
        val document = getTestDocument(student)

        context("내 문서를 조회하면") {

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.findByWriterStudentId(student.id) } returns document
            every { feedbackRepository.findByDocumentId(document.id) } returns listOf()

            it("문서 상세 정보를 반환한다.") {
                shouldNotThrow<Exception> {
                    queryMyDocumentInfoUseCase.execute()
                }
            }
        }

        val elementIds = listOf(randomUUID(), randomUUID(), randomUUID(), randomUUID(), randomUUID())

        val documentWithFeedbacks = Document(
            writer = WriterInfoElement(
                student = student,
                major = anyValueObject()
            ),
            introduce = IntroduceElement(
                elementId = elementIds[0]
            ),
            status = Status.SUBMITTED,
            projectList = mutableListOf(
                anyValueObject("elementId" to elementIds[1]),
                anyValueObject("elementId" to elementIds[2])
            ),
            awardList = mutableListOf(
                anyValueObject("elementId" to elementIds[3]),
            ),
            certificateList = mutableListOf(
                anyValueObject("elementId" to elementIds[4]),
            )
        )

        val feedbacks = listOf(
            Feedback(document.id, elementIds[0], "0", false),
            Feedback(document.id, elementIds[1], "1", false),
            Feedback(document.id, elementIds[2], "2", false),
            Feedback(document.id, elementIds[3], "3", false),
            Feedback(document.id, elementIds[4], "4", false)
        )

        context("피드백이 있는 내 문서를 조회하면") {

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.findByWriterStudentId(student.id) } returns documentWithFeedbacks
            every { feedbackRepository.findByDocumentId(documentWithFeedbacks.id) } returns feedbacks

            it("문서 상세, 피드백 정보를 반환한다.") {

                val response = queryMyDocumentInfoUseCase.execute()

                response.introduce.feedback shouldBe feedbacks[0].comment

                response.projectList[0].feedback shouldBe feedbacks[1].comment
                response.projectList[1].feedback shouldBe feedbacks[2].comment

                response.awardList[0].feedback shouldBe feedbacks[3].comment

                response.certificateList[0].feedback shouldBe feedbacks[4].comment
            }
        }

        context("내 문서가 존재하지 않으면") {

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.findByWriterStudentId(student.id) } returns null

            it("DocumentNotFound 예외를 던진다.") {
                shouldThrow<DocumentNotFoundException> {
                    queryMyDocumentInfoUseCase.execute()
                }
            }
        }
    }
})
