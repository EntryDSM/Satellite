package kr.hs.entrydsm.repo.domain.document.persistence.repository

import com.querydsl.core.types.dsl.BooleanExpression
import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.domain.document.persistence.QDocument.document
import kr.hs.entrydsm.repo.domain.document.persistence.enums.Status
import kr.hs.entrydsm.repo.global.config.querydsl.findBy
import java.util.UUID

fun DocumentRepository.findByStatusAndWriterInfo(
    status: Status,
    name: String,
    grade: String?,
    classNum: String?,
    majorId: UUID?
): List<Document> {

    return findBy(
        document.status.eq(status),
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
): List<Document> {

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