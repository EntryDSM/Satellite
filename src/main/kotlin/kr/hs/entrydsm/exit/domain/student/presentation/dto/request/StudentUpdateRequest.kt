package kr.hs.entrydsm.exit.domain.student.presentation.dto.request

data class StudentUpdateRequest (
    val name: String,
    val profileImagePath: String,
    val grade: String,
    val classNum: String,
    val number: String,
    val major: String
)