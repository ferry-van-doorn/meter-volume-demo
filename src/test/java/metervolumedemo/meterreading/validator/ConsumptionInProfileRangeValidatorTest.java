package metervolumedemo.meterreading.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import metervolumedemo.meterreading.MeterReadingDto;
import metervolumedemo.profile.Profile;
import metervolumedemo.profile.ProfileService;

@RunWith(MockitoJUnitRunner.class)
public class ConsumptionInProfileRangeValidatorTest {

    private static final BigDecimal totalVolume = new BigDecimal("246.00");

    @Mock
    private ProfileService profileService;

    private BigDecimal tolerance = new BigDecimal("0.25");

    private ConsumptionInProfileRangeValidator consumptionInProfileRangeValidator;

    @Before
    public void setUp() {

        consumptionInProfileRangeValidator = new ConsumptionInProfileRangeValidator(profileService, tolerance);
    }

    @Test
    public void testIsValidWhenNoProfileThenReturnFalse() {
        MeterReadingDto meterReadingDto = createMeterReadingDto();

        when(profileService.findById(meterReadingDto.getProfileId())).thenReturn(Optional.empty());

        assertThat(consumptionInProfileRangeValidator.isValid(meterReadingDto, null)).isFalse();
    }

    @Test
    public void testIsValidWhenNoTotalVolumeThenReturnFalse() {
        MeterReadingDto meterReadingDto = createMeterReadingDto();
        meterReadingDto.getReadings().remove(Month.DECEMBER);

        Profile profile = createProfile();
        when(profileService.findById(meterReadingDto.getProfileId())).thenReturn(Optional.of(profile));

        assertThat(consumptionInProfileRangeValidator.isValid(meterReadingDto, null)).isFalse();
    }

    @Test
    public void testIsValidWhenMissingMonthThenReturnFalse() {
        MeterReadingDto meterReadingDto = createMeterReadingDto();
        meterReadingDto.getReadings().remove(Month.MAY);

        Profile profile = createProfile();
        when(profileService.findById(meterReadingDto.getProfileId())).thenReturn(Optional.of(profile));

        assertThat(consumptionInProfileRangeValidator.isValid(meterReadingDto, null)).isFalse();
    }

    @Test
    public void testIsValidWhenValidMeterReadingThenReturnTrue() {
        MeterReadingDto meterReadingDto = createMeterReadingDto();

        Profile profile = createProfile();
        when(profileService.findById(meterReadingDto.getProfileId())).thenReturn(Optional.of(profile));

        assertThat(consumptionInProfileRangeValidator.isValid(meterReadingDto, null)).isTrue();
    }

    @Test
    public void testIsValidWhenValidMeterReadingCloseToTheBoundaryThenReturnTrue() {
        MeterReadingDto meterReadingDto = createMeterReadingDto();
        BigDecimal withinMonthlyTolerance = totalVolume.multiply(determineLinearMonthFraction()).multiply(tolerance).subtract(new BigDecimal("0.01"));
        meterReadingDto.getReadings().put(Month.JUNE, generateMonthReading(Month.JUNE.getValue()).subtract(withinMonthlyTolerance));
        meterReadingDto.getReadings().put(Month.AUGUST, generateMonthReading(Month.AUGUST.getValue()).add(withinMonthlyTolerance));

        Profile profile = createProfile();
        when(profileService.findById(meterReadingDto.getProfileId())).thenReturn(Optional.of(profile));

        assertThat(consumptionInProfileRangeValidator.isValid(meterReadingDto, null)).isTrue();
    }

    @Test
    public void testIsValidWhenMeterReadingOutsideToTheBoundaryThenReturnFalse() {
        MeterReadingDto meterReadingDto = createMeterReadingDto();
        BigDecimal withinMonthlyTolerance = totalVolume.multiply(determineLinearMonthFraction()).multiply(tolerance).add(new BigDecimal("0.01"));
        meterReadingDto.getReadings().put(Month.JUNE, generateMonthReading(Month.JUNE.getValue()).subtract(withinMonthlyTolerance));

        Profile profile = createProfile();
        when(profileService.findById(meterReadingDto.getProfileId())).thenReturn(Optional.of(profile));

        assertThat(consumptionInProfileRangeValidator.isValid(meterReadingDto, null)).isFalse();
    }

    private Profile createProfile() {
        Profile profile = new Profile();
        Arrays.stream(Month.values()).forEach(month -> profile.addFraction(month, determineLinearMonthFraction()));
        return profile;
    }

    private MeterReadingDto createMeterReadingDto() {
        MeterReadingDto meterReadingDto = new MeterReadingDto();
        meterReadingDto.setProfileId(2L);
        meterReadingDto.setReadings(Arrays.stream(Month.values()).collect(Collectors.toMap(month -> month, month -> generateMonthReading(month.getValue()))));
        return meterReadingDto;
    }

    private BigDecimal generateMonthReading(int month) {
        if (month == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal fraction = determineLinearMonthFraction();
        return totalVolume.multiply(fraction).add(generateMonthReading(month - 1));
    }

    private BigDecimal determineLinearMonthFraction() {
        return BigDecimal.ONE.divide(BigDecimal.valueOf(Month.values().length), 6, RoundingMode.HALF_UP);
    }
}