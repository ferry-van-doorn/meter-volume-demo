package metervolumedemo.meterreading.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class ReadingIncreaseEachMonthValidatorTest {

    private ReadingIncreaseEachMonthValidator readingIncreaseEachMonthValidator = new ReadingIncreaseEachMonthValidator();

    @Test
    public void testIsValidWhenValueNullThenReturnFalse() {

        assertThat(readingIncreaseEachMonthValidator.isValid(null, null)).isFalse();
    }

    @Test
    public void testIsValidWhenEmptyMapThenReturnFalse() {
        Map<Month, BigDecimal> value = new HashMap<>();

        assertThat(readingIncreaseEachMonthValidator.isValid(value, null)).isFalse();
    }

    @Test
    public void testIsValidWhenDescreaseInReadingThenReturnFalse() {
        Map<Month, BigDecimal> value = Arrays.stream(Month.values()).collect(Collectors.toMap(month -> month, month -> new BigDecimal(12 - month.getValue())));

        assertThat(readingIncreaseEachMonthValidator.isValid(value, null)).isFalse();
    }

    @Test
    public void testIsValidWhenIncreaseInReadingThenReturnTrue() {
        Map<Month, BigDecimal> value = Arrays.stream(Month.values()).collect(Collectors.toMap(month -> month, month -> new BigDecimal(month.getValue())));

        assertThat(readingIncreaseEachMonthValidator.isValid(value, null)).isTrue();
    }

    @Test
    public void testIsValidWhenReadingRemainTheSameThenReturnTrue() {
        Map<Month, BigDecimal> value = Arrays.stream(Month.values()).collect(Collectors.toMap(month -> month, month -> BigDecimal.ZERO));

        assertThat(readingIncreaseEachMonthValidator.isValid(value, null)).isTrue();
    }
}