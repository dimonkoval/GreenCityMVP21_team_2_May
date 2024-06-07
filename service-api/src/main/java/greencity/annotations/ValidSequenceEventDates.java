package greencity.annotations;

import greencity.validator.ValidSequenceEventDatesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidSequenceEventDatesValidator.class)
public @interface ValidSequenceEventDates {
    String message() default "Each next event date should be after the previous one";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
