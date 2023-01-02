package kr.hs.entrydsm.exit.global.error

import kr.hs.entrydsm.exit.global.error.response.BindErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException


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

    @ExceptionHandler(ConstraintViolationException::class)
    protected fun handleConstraintViolation(e: ConstraintViolationException): ResponseEntity<Map<String, Any>>? {

        val errorMap: MutableMap<String, Any> = HashMap()
        val errors: MutableList<String> = ArrayList()
        for (violation: ConstraintViolation<*> in e.constraintViolations) {
            errors.add(
                violation.rootBeanClass.name + " " +
                        violation.propertyPath + ": " + violation.message
            )
        }
        errorMap["errors"] = errors
        errorMap["message"] = e.message ?: ""
        return ResponseEntity(errorMap, HttpStatus.BAD_REQUEST)
    }
}