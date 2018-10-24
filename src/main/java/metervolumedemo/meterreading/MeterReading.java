package metervolumedemo.meterreading;

import java.math.BigDecimal;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;

import org.springframework.data.jpa.domain.AbstractPersistable;

import metervolumedemo.common.validator.AllMonthsPresent;
import metervolumedemo.meterreading.validator.ReadingIncreaseEachMonth;

@Entity
public class MeterReading extends AbstractPersistable<Long> {

    @Column(nullable = false)
    private String meterId;

    @Column(nullable = false)
    private Long profileId;

    @Column(nullable = false)
    private Integer year;

    @ElementCollection
    @CollectionTable(name = "meter_reading_reading")
    @MapKeyColumn(name = "month")
    @Column(name = "reading", nullable = false)
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Month, BigDecimal> readings = new HashMap<>();

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

    public void addReading(Month month, BigDecimal reading) {
        this.readings.put(month, reading);
    }
}
