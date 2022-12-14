package com.example.exit.domain.common.security

import java.util.UUID

interface SecurityPort {

    fun getCurrentUserId(): UUID

}