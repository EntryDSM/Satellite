package kr.hs.entrydsm.satellite.common

import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentDomain
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.major.domain.MajorDomain
import kr.hs.entrydsm.satellite.domain.student.domain.StudentDomain
import java.util.*

fun getTestDocument(
    student: StudentDomain = anyValueObject(),
    major: MajorDomain = anyValueObject(),
    status: DocumentStatus = DocumentStatus.CREATED,
    year: Int = 2023
) = DocumentDomain(
    id = UUID.randomUUID(),
    writer = WriterInfoElement(
        student = student,
        major = major
    ),
    status = status,
    year = year
)