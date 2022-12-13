package com.example.exit.domain.auth.presentation

import com.example.exit.domain.auth.presentation.dto.request.ReceivePhoneNumberCodeRequest
import com.example.exit.domain.auth.presentation.dto.request.SendPhoneNumberCodeRequest
import com.example.exit.domain.auth.presentation.dto.response.SendPhoneNumberCodeResponse
import com.example.exit.domain.auth.usecase.ReceivePhoneNumberVerificationCodeUseCase
import com.example.exit.domain.auth.usecase.SendPhoneNumberVerificationCodeUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RequestMapping("/auth")
@RestController
class AuthController(
    private val sendPhoneNumberVerificationCodeUseCase: SendPhoneNumberVerificationCodeUseCase,
    private val receivePhoneNumberVerificationCodeUseCase: ReceivePhoneNumberVerificationCodeUseCase
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/phone")
    fun sendPhoneNumberVerificationCode(@RequestBody @Valid request: SendPhoneNumberCodeRequest): SendPhoneNumberCodeResponse {
        return sendPhoneNumberVerificationCodeUseCase.execute(
            phoneNumber = request.phoneNumber
        )
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/phone")
    fun receivePhoneNumberVerificationCode(@RequestBody @Valid request: ReceivePhoneNumberCodeRequest) {
        receivePhoneNumberVerificationCodeUseCase.execute(
            code = request.code,
            phoneNumber = request.phoneNumber
        )
    }
}