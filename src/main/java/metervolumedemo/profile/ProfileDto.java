package metervolumedemo.profile;

import java.math.BigDecimal;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import metervolumedemo.common.validator.AllMonthsPresent;
import metervolumedemo.profile.validator.SumOfAllOne;

public class ProfileDto {

    private Long id;

    @NotBlank
    private String profile;

    @AllMonthsPresent
    @SumOfAllOne
    private Map<Month, BigDecimal> fractions = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Map<Month, BigDecimal> getFractions() {
        return fractions;
    }

    public void setFractions(Map<Month, BigDecimal> fractions) {
        this.fractions = fractions;
    }
}
