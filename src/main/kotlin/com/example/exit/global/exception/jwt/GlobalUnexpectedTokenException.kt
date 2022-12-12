package com.example.exit.global.exception.jwt

import com.example.exit.global.error.GlobalErrorCode
import com.example.exit.global.error.custom.GlobalCustomException

object GlobalUnexpectedTokenException: GlobalCustomException(
    GlobalErrorCode.UNEXPECTED_JWT
)