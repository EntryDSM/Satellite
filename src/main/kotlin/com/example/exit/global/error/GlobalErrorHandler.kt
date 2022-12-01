package com.example.exit.global.error

import com.example.exit.global.error.response.BindErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException::class)
    protected fun handleBindException(e: BindException): BindErrorResponse? {

        val errorMap = HashMap<String, String?>()

        for (error: FieldError in e.fieldErrors) {
            errorMap[error.field] = error.defaultMessage
        }

        return BindErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            fieldError = listOf(errorMap)
        )
    }
}