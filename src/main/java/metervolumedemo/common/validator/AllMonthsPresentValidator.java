package metervolumedemo.common.validator;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Arrays;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AllMonthsPresentValidator implements ConstraintValidator<AllMonthsPresent, Map<Month, BigDecimal>> {

    @Override
    public void initialize(AllMonthsPresent constraintAnnotation) {

    }

    @Override
    public boolean isValid(Map<Month, BigDecimal> value, ConstraintValidatorContext context) {
        if (value == null || value.keySet().isEmpty()) {
            return false;
        }

        return value.keySet().containsAll(Arrays.asList(Month.values()));
    }
}
