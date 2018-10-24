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
@Constraint(validatedBy = { ConsumptionInProfileRangeValidator.class })
public @interface ConsumptionInProfileRange {

    String message() default "Consumption for the month deviates too much from expected value based on the profile";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
