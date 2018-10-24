package metervolumedemo.profile.validator;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SumOfAllOneValidator implements ConstraintValidator<SumOfAllOne, Map<Month, BigDecimal>> {

    @Override
    public void initialize(SumOfAllOne constraintAnnotation) {

    }

    @Override
    public boolean isValid(Map<Month, BigDecimal> value, ConstraintValidatorContext context) {
        if (value == null || value.values().isEmpty()) {
            return false;
        }

        return value.values().stream().reduce(BigDecimal::add).get().compareTo(BigDecimal.ONE) == 0;
    }
}
