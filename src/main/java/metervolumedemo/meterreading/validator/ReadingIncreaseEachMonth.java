package metervolumedemo.meterreading.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { ReadingIncreaseEachMonthValidator.class })
public @interface ReadingIncreaseEachMonth {

    String message() default "The reading cannot be lower than the previous month";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
