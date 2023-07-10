package kr.hs.entrydsm.satellite.common.validator

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ElementSizeValidator::class])
annotation class ElementSize(
    val min: Int = -1,
    val max: Int = Int.MAX_VALUE,
    val message: String = "Element size is not valid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)

class ElementSizeValidator : ConstraintValidator<ElementSize?, List<String?>> {

    var annotation: ElementSize? = null
        get() = field!!

    override fun initialize(elementSize: ElementSize?) {
        annotation = elementSize
    }

    override fun isValid(
        objects: List<String?>,
        context: ConstraintValidatorContext,
    ) = objects.all {
            annotation!!.min <= it!!.length && it.length <=  annotation!!.max
        }
}