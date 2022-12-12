package com.example.exit.domain.common

import com.example.exit.global.error.custom.CustomErrorProperty

enum class DomainErrorCode(
    private val status: Int,
    private val message: String,
    private val code: String
) : CustomErrorProperty {

    PASSWORD_MISS_MATCHED(400, "COMMON-400-1","Password Mismatch"),

    TEACHER_NOT_FOUND(404, "TEACHER NOT FOUND", "TEACHER-404-1")
    ;

    override fun status(): Int = status
    override fun message(): String = message
    override fun code(): String = code
}