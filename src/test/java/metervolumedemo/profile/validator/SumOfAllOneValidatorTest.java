package metervolumedemo.profile.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class SumOfAllOneValidatorTest {

    private SumOfAllOneValidator sumOfAllOneValidator = new SumOfAllOneValidator();

    @Test
    public void testIsValidWhenValueNullThenReturnFalse() {

        assertThat(sumOfAllOneValidator.isValid(null, null)).isFalse();
    }

    @Test
    public void testIsValidWhenEmptyMapThenReturnFalse() {
        Map<Month, BigDecimal> value = new HashMap<>();

        assertThat(sumOfAllOneValidator.isValid(value, null)).isFalse();
    }

    @Test
    public void testIsValidWhenSumNotOneThenReturnFalse() {
        Map<Month, BigDecimal> value = Arrays.stream(Month.values()).collect(Collectors.toMap(month -> month, month -> new BigDecimal("0.09")));

        assertThat(sumOfAllOneValidator.isValid(value, null)).isFalse();
    }

    @Test
    public void testIsValidWhenSumIsOneThenReturnTrue() {
        Map<Month, BigDecimal> value = Arrays.stream(Month.values()).collect(
                Collectors.toMap(month -> month, month -> month.getValue() > 8 ? new BigDecimal("0.09") : new BigDecimal("0.08")));

        assertThat(sumOfAllOneValidator.isValid(value, null)).isTrue();
    }
}