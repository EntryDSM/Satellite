package com.example.exit.domain.common.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID


object SecurityUtil {
    fun getCurrentUserId(): UUID {
        return UUID.fromString(SecurityContextHolder.getContext().authentication.name)
    }
}