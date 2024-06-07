package greencity.annotations;

import greencity.validator.ValidEventDateTimeValidator;
import greencity.validator.ValidLinkValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidLinkValidator.class)
public @interface ValidLink {
    String message() default "Link is incorrect. The link must start with http(s)://";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}