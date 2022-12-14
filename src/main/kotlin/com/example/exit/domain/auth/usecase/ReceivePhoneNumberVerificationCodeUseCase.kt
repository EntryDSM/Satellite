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

    companion object{
        private const val MAX_TIME_TO_LIVE = 999999L
    }

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
                timeToLive = MAX_TIME_TO_LIVE
            )
        )
    }
}