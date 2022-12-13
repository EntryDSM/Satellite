package com.example.exit.domain.auth.usecase

import com.example.exit.domain.auth.exception.PhoneNumberVerificationCodeNotFoundException
import com.example.exit.domain.auth.exception.VerificationCodeMisMatchedException
import com.example.exit.domain.auth.persistence.PhoneNumberVerificationCode
import com.example.exit.domain.auth.persistence.repository.PhoneNumberVerificationCodeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReceivePhoneNumberVerificationCodeUseCase(
    private val phoneNumberVerificationCodeRepository: PhoneNumberVerificationCodeRepository,
) {

    fun execute(phoneNumber: String, code: String) {
        val phoneNumberVerificationCode = phoneNumberVerificationCodeRepository.findByIdOrNull(phoneNumber)
            ?: throw PhoneNumberVerificationCodeNotFoundException

        if (phoneNumberVerificationCode.code != code) {
            throw VerificationCodeMisMatchedException
        }

        phoneNumberVerificationCodeRepository.save(
            PhoneNumberVerificationCode(
                phoneNumber = phoneNumberVerificationCode.phoneNumber,
                code = phoneNumberVerificationCode.code,
                isVerified = true,
                countOfSend = phoneNumberVerificationCode.countOfSend,
                timeToLive = 9999999
            )
        )
    }
}