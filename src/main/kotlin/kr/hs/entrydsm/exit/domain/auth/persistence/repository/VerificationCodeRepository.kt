package kr.hs.entrydsm.exit.domain.auth.persistence.repository

import kr.hs.entrydsm.exit.domain.auth.persistence.VerificationCode
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VerificationCodeRepository : CrudRepository<VerificationCode, String>