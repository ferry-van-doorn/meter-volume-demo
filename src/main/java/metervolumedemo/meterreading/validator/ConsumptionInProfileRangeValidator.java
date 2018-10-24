package metervolumedemo.meterreading.validator;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import metervolumedemo.meterreading.MeterReadingDto;
import metervolumedemo.profile.Profile;
import metervolumedemo.profile.ProfileService;

@Component
@Transactional
public class ConsumptionInProfileRangeValidator implements ConstraintValidator<ConsumptionInProfileRange, MeterReadingDto> {

    private final ProfileService profileService;
    private final BigDecimal tolerance;

    public ConsumptionInProfileRangeValidator(ProfileService profileService, @Value("${volume.tolerance:0.25}") BigDecimal tolerance) {
        this.profileService = profileService;
        this.tolerance = tolerance;
    }

    @Override
    public void initialize(ConsumptionInProfileRange constraintAnnotation) {

    }

    @Override
    public boolean isValid(MeterReadingDto value, ConstraintValidatorContext context) {

        if (value == null || value.getProfileId() == null || value.getReadings().isEmpty()) {
            return false;
        }

        Optional<Profile> optionalProfile = profileService.findById(value.getProfileId());
        if (!optionalProfile.isPresent()) {
            return false;
        }
        Profile profile = optionalProfile.get();

        BigDecimal totalVolume = getTotalVolume(value);
        if (totalVolume == null) {
            return false;
        }

        return checkAllMonthValues(value.getReadings(), profile, totalVolume);
    }

    private BigDecimal getTotalVolume(MeterReadingDto value) {

        return value.getReadings().get(Month.DECEMBER);
    }

    private boolean checkAllMonthValues(Map<Month, BigDecimal> readings, Profile profile, BigDecimal totalVolume) {

        BigDecimal previousReading = BigDecimal.ZERO;

        for (Month currentMonth : Month.values()) {
            BigDecimal currentReading = readings.get(currentMonth);
            if (currentReading == null) {
                return false;
            }

            BigDecimal currentVolume = currentReading.subtract(previousReading);

            if (!isMonthValueBetweenProfileBoundaries(profile, totalVolume, currentMonth, currentVolume)) {
                return false;
            }

            previousReading = currentReading;
        }

        return true;
    }

    private boolean isMonthValueBetweenProfileBoundaries(Profile profile, BigDecimal totalVolume, Month month, BigDecimal currentVolume) {

        BigDecimal currentFraction = profile.getFractions().get(month);

        BigDecimal lowerBound = totalVolume.multiply(currentFraction).multiply(BigDecimal.ONE.subtract(tolerance));
        BigDecimal upperBound = totalVolume.multiply(currentFraction).multiply(BigDecimal.ONE.add(tolerance));

        return currentVolume.compareTo(lowerBound) > 0 && currentVolume.compareTo(upperBound) < 0;
    }
}
