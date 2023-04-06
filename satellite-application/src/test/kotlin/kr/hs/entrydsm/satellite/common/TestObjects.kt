package kr.hs.entrydsm.satellite.common

import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.domain.document.domain.Document
import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus
import kr.hs.entrydsm.satellite.domain.document.domain.element.WriterInfoElement
import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.*

fun getTestDocument(
    student: Student = anyValueObject(),
    major: Major = anyValueObject(),
    status: DocumentStatus = DocumentStatus.CREATED,
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