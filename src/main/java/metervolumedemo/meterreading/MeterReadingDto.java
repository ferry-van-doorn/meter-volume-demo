package metervolumedemo.meterreading;

import java.math.BigDecimal;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import metervolumedemo.common.validator.AllMonthsPresent;
import metervolumedemo.meterreading.validator.ConsumptionInProfileRange;
import metervolumedemo.meterreading.validator.ProfileExists;
import metervolumedemo.meterreading.validator.ReadingIncreaseEachMonth;

@ConsumptionInProfileRange
public class MeterReadingDto {

    private Long id;

    @NotBlank
    private String meterId;

    @ProfileExists
    private Long profileId;

    private Integer year;

    @AllMonthsPresent
    @ReadingIncreaseEachMonth
    private Map<Month, BigDecimal> readings = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Map<Month, BigDecimal> getReadings() {
        return readings;
    }

    public void setReadings(Map<Month, BigDecimal> readings) {
        this.readings = readings;
    }
}
