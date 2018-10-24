package metervolumedemo.common.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class AllMonthsPresentValidatorTest {

    private AllMonthsPresentValidator allMonthsPresentValidator = new AllMonthsPresentValidator();

    @Test
    public void testIsValidWhenValueNullThenReturnFalse() {

        assertThat(allMonthsPresentValidator.isValid(null, null)).isFalse();
    }

    @Test
    public void testIsValidWhenEmptyMapThenReturnFalse() {
        Map<Month, BigDecimal> value = new HashMap<>();

        assertThat(allMonthsPresentValidator.isValid(value, null)).isFalse();
    }

    @Test
    public void testIsValidWhenMonthMissingThenReturnFalse() {
        Map<Month, BigDecimal> value = Arrays.stream(Month.values()).filter(month -> month == Month.DECEMBER)
                                             .collect(Collectors.toMap(month -> month, month -> BigDecimal.ONE));

        assertThat(allMonthsPresentValidator.isValid(value, null)).isFalse();
    }

    @Test
    public void testIsValidWhenAllMonthsPresentThenReturnTrue() {
        Map<Month, BigDecimal> value = Arrays.stream(Month.values()).collect(Collectors.toMap(month -> month, month -> BigDecimal.ONE));

        assertThat(allMonthsPresentValidator.isValid(value, null)).isTrue();
    }
}
