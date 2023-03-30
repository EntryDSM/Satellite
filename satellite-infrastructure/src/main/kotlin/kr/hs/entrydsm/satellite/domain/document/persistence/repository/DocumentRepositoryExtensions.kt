package kr.hs.entrydsm.satellite.domain.document.persistence.repository

import com.querydsl.core.types.dsl.BooleanExpression
import java.util.UUID
import kr.hs.entrydsm.satellite.domain.document.persistence.DocumentJpaEntity
import kr.hs.entrydsm.satellite.domain.document.persistence.QDocument.document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus

fun DocumentRepository.findByStatusAndWriterInfo(
    documentStatus: DocumentStatus,
    name: String,
    grade: String?,
    classNum: String?,
    majorId: UUID?
): List<DocumentJpaEntity> {

    return findBy(
        document.status.eq(documentStatus),
        document.writer.name.contains(name),
        eqGrade(grade),
        eqClassNum(classNum),
        eqMajorId(majorId)
    )
}

fun DocumentRepository.findByWriterInfo(
    name: String,
    grade: String?,
    classNum: String?,
    majorId: UUID?
): List<DocumentJpaEntity> {

    return findBy(
        document.writer.name.contains(name),
        eqGrade(grade),
        eqClassNum(classNum),
        eqMajorId(majorId)
    )
}

private fun eqGrade(grade: String?): BooleanExpression? {
    return if (grade != null) document.writer.grade.eq(grade) else null
}

private fun eqClassNum(classNum: String?): BooleanExpression? {
    return if (classNum != null) document.writer.classNum.eq(classNum) else null
}

private fun eqMajorId(majorId: UUID?): BooleanExpression? {
    return if (majorId != null) document.writer.majorId.eq(majorId) else null
}