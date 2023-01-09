package kr.hs.entrydsm.exit.global.error

import kr.hs.entrydsm.exit.global.error.response.BindErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class GlobalErrorHandler {

    @ExceptionHandler(BindException::class)
    protected fun handleBindException(e: BindException): BindErrorResponse {

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
    protected fun handleConstraintViolation(e: ConstraintViolationException): BindErrorResponse {

        val errorMap = HashMap<String, String?>()

        for (violation: ConstraintViolation<*> in e.constraintViolations) {
            errorMap[violation.rootBeanClass.name] =
                violation.propertyPath.toString() + ": " +violation.message
        }

        return BindErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            fieldError = listOf(errorMap)
        )
    }

}