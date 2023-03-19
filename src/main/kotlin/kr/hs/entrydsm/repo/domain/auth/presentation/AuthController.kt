package kr.hs.entrydsm.repo.domain.auth.presentation

import javax.validation.Valid
import kr.hs.entrydsm.repo.domain.auth.presentation.dto.request.SendMailVerificationCodeRequest
import kr.hs.entrydsm.repo.domain.auth.presentation.dto.request.SendPhoneVerificationCodeRequest
import kr.hs.entrydsm.repo.domain.auth.presentation.dto.request.VerifyCodeRequest
import kr.hs.entrydsm.repo.domain.auth.usecase.SendMailVerificationCodeUseCase
import kr.hs.entrydsm.repo.domain.auth.usecase.SendPhoneVerificationCodeUseCase
import kr.hs.entrydsm.repo.domain.auth.usecase.VerifyCodeUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val sendPhoneVerificationCodeUseCase: SendPhoneVerificationCodeUseCase,
    private val sendMailVerificationCodeUseCase: SendMailVerificationCodeUseCase,
    private val verifyCodeUseCase: VerifyCodeUseCase
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/phone")
    fun sendPhoneVerificationCode(
        @RequestBody @Valid request: SendPhoneVerificationCodeRequest
    ): kr.hs.entrydsm.repo.domain.auth.presentation.dto.response.SendPhoneNumberCodeResponse {
        return sendPhoneVerificationCodeUseCase.execute(
            phoneNumber = request.phoneNumber
        )
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/mail")
    fun sendMailVerificationCode(
        @RequestBody @Valid request: SendMailVerificationCodeRequest
    ) {
        return sendMailVerificationCodeUseCase.execute(
            email = request.email
        )
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/verify")
    fun verifyCode(
        @RequestBody @Valid request: VerifyCodeRequest
    ) {
        verifyCodeUseCase.execute(
            key = request.key,
            code = request.code
        )
    }
}