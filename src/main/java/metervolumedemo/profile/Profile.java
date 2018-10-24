package metervolumedemo.profile;

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

@Entity
public class Profile extends AbstractPersistable<Long> {

    @Column(unique = true, nullable = false)
    private String profile;

    @ElementCollection
    @CollectionTable(name = "profile_fraction")
    @MapKeyColumn(name = "month")
    @Column(name = "fraction", nullable = false)
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Month, BigDecimal> fractions = new HashMap<>();

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Map<Month, BigDecimal> getFractions() {
        return fractions;
    }

    public void addFraction(Month month, BigDecimal fraction) {
        this.fractions.put(month, fraction);
    }

    public void clearFractions() {
        this.fractions.clear();
    }
}
