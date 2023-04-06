package kr.hs.entrydsm.satellite.domain.document.persistence.element

import com.querydsl.core.annotations.QueryEmbeddable
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import java.util.*

@QueryEmbeddable
class WriterInfoJpaElement(

    elementId: UUID,

    studentId: UUID,
    name: String,
    email: String,
    profileImagePath: String,

    grade: String,
    classNum: String,
    number: String,

    majorId: UUID,
    majorName: String

) : WriterInfoElement(
    elementId, studentId, name, email, profileImagePath, grade, classNum, number, majorId, majorName
) {
    companion object {
        fun of(writer: WriterInfoElement) = writer.run {
            WriterInfoJpaElement(
                elementId, studentId, name, email, profileImagePath, grade, classNum, number, majorId, majorName
            )
        }
    }
}