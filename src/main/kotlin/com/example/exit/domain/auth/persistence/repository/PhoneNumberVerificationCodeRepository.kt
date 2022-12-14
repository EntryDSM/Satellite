package com.example.exit.domain.auth.persistence.repository

import com.example.exit.domain.auth.persistence.PhoneNumberVerificationCode
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PhoneNumberVerificationCodeRepository : CrudRepository<PhoneNumberVerificationCode, String> {
}