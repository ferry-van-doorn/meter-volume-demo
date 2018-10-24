package metervolumedemo.profile.validator;

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
@Constraint(validatedBy = { SumOfAllOneValidator.class })
public @interface SumOfAllOne {

    String message() default "Sum of all items does not result in 1.0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
