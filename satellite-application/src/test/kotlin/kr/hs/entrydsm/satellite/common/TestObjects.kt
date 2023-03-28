package kr.hs.entrydsm.satellite.common

import java.util.UUID
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.domain.document.persistence.Document
import kr.hs.entrydsm.satellite.domain.document.persistence.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.document.persistence.enums.Status
import kr.hs.entrydsm.satellite.domain.major.persistence.Major
import kr.hs.entrydsm.satellite.domain.student.persistence.Student

fun getTestDocument(
    student: Student = anyValueObject("number" to "1"),
    major: Major = anyValueObject(),
    status: Status = Status.CREATED,
    year: Int = 2023
) = Document(
    id = UUID.randomUUID(),
    writer = WriterInfoElement(
        student = student,
        major = major
    ),
    status = status,
    year = year
)