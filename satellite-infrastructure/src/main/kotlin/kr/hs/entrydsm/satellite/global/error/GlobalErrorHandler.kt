package kr.hs.entrydsm.satellite.global.error

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import kr.hs.entrydsm.satellite.common.error.response.BindErrorResponse
import kr.hs.entrydsm.satellite.common.error.response.DefaultErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalErrorHandler {

    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException): BindErrorResponse {

        val errorMap = HashMap<String, String?>()

        for (error: FieldError in e.fieldErrors) {
            errorMap[error.field] = error.defaultMessage
        }

        return BindErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            fieldError = listOf(errorMap)
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(e: ConstraintViolationException): BindErrorResponse {

        val errorMap = HashMap<String, String?>()

        for (violation: ConstraintViolation<*> in e.constraintViolations) {
            errorMap[violation.rootBeanClass.name] =
                violation.propertyPath.toString() + ": " + violation.message
        }

        return BindErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            fieldError = listOf(errorMap)
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): DefaultErrorResponse {

        return DefaultErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            code = GlobalErrorCode.BAD_REQUEST.code(),
            message = e.localizedMessage
        )
    }
}