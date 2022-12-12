package com.example.exit.domain.auth.persistence.repository

import com.example.exit.domain.auth.persistence.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RefreshTokenRepository : CrudRepository<RefreshToken, UUID>