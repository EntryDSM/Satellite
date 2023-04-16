package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.document.domain.DocumentStatus

data class DocumentResponse(
    val profileImageUrl: String,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val isExist: Boolean,
    val status: DocumentStatus?,
    val majorName: String?,
    val heading: String?,
    val introduce: String?
)
