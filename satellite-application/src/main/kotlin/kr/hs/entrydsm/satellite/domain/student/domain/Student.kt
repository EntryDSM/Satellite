package kr.hs.entrydsm.satellite.domain.student.domain

import java.util.*

data class Student(
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val name: String,
    val grade: String,
    val classNum: String,
    val number: String,
    val profileImagePath: String
)