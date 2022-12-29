package kr.hs.entrydsm.exit.global.error

import kr.hs.entrydsm.exit.global.error.response.BindErrorResponse
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
    protected fun handleBindException(e: BindException): kr.hs.entrydsm.exit.global.error.response.BindErrorResponse? {

        val errorMap = HashMap<String, String?>()

        for (error: FieldError in e.fieldErrors) {
            errorMap[error.field] = error.defaultMessage
        }

        return kr.hs.entrydsm.exit.global.error.response.BindErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            fieldError = listOf(errorMap)
        )
    }
}