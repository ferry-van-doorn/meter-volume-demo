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
@Constraint(validatedBy = { ProfileExistsValidator.class })
public @interface ProfileExists {

    String message() default "The profile must exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
