package metervolumedemo.meterreading.validator;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReadingIncreaseEachMonthValidator implements ConstraintValidator<ReadingIncreaseEachMonth, Map<Month, BigDecimal>> {

    @Override
    public void initialize(ReadingIncreaseEachMonth constraintAnnotation) {

    }

    @Override
    public boolean isValid(Map<Month, BigDecimal> value, ConstraintValidatorContext context) {
        if (value == null || value.values().isEmpty()) {
            return false;
        }

        BigDecimal currentReading = BigDecimal.ZERO;
        for (Month month : Month.values()) {
            BigDecimal reading = value.get(month);
            if (reading == null || reading.compareTo(currentReading) < 0) {
                return false;
            }
            currentReading = reading;
        }
        return true;
    }
}
