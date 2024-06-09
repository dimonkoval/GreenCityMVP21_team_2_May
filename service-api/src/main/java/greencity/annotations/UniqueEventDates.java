package greencity.annotations;

import greencity.validator.UniqueEventDatesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEventDatesValidator.class)
public @interface UniqueEventDates {
    /**
     * Defines the message that will be showed when the input data is not valid.
     *
     * @return message
     */
    String message() default "You can't enter the same date for two days";

    /**
     * Let you select to split the annotations into different groups to apply
     * different validations to each group.
     *
     * @return groups
     */
    Class<?>[] groups() default {};

    /**
     * Payloads are typically used to carry metadata information consumed by a
     * validation client.
     *
     * @return payload
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Specifies the priority of the validation.
     *
     * The priority value is an integer where a higher number indicates a higher priority.
     * This can be particularly useful in complex validation scenarios where the order
     * of validation checks matters. For example, setting a higher priority (higher number)
     * for critical validations can ensure they are executed first.
     *
     * @return priority
     */
    int priority() default 0;
}
