package kr.hs.entrydsm.exit.domain.auth.persistence.repository

import kr.hs.entrydsm.exit.domain.auth.persistence.PhoneNumberVerificationCode
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PhoneNumberVerificationCodeRepository : CrudRepository<PhoneNumberVerificationCode, String>