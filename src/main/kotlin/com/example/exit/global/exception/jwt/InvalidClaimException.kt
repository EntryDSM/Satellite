package com.example.exit.global.exception.jwt

import com.example.exit.global.error.GlobalErrorCode
import com.example.exit.global.error.custom.CustomException

object InvalidClaimException: CustomException(
    GlobalErrorCode.INVALID_CLAIM_JWT
)