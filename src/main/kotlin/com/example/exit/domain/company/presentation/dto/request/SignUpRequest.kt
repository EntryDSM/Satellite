package com.example.exit.domain.company.presentation.dto.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class SignUpRequest(
    @field:Length(min = 10, max = 11)
    @field:NotBlank
    val phoneNumber: String,

    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    @field:Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,40}",
        message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    val password: String,

    @field:NotBlank
    val managerName: String,

    @field:NotBlank
    val location: String
)
