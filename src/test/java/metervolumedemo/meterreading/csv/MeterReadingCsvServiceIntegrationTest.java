package metervolumedemo.meterreading.csv;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Month;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import metervolumedemo.meterreading.MeterReadingRepository;
import metervolumedemo.profile.Profile;
import metervolumedemo.profile.ProfileService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MeterReadingCsvServiceIntegrationTest {

    @Autowired
    private MeterReadingCsvService meterReadingCsvService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @Test
    public void testReadCsvWhenTwoOfThreeValidMeterreadingsInCSVThenStoreTwo() throws IOException {
        profileService.create(createProfile("A"));
        profileService.create(createProfile("B"));

        Resource meterReadingResource = new ClassPathResource("meterreading.csv");

        meterReadingCsvService.readCsv(meterReadingResource.getFile());

        assertThat(meterReadingRepository.findAll()).hasSize(2);
    }

    private Profile createProfile(String name) {
        Profile profile = new Profile();
        profile.setProfile(name);
        Arrays.stream(Month.values()).forEach(month -> profile.addFraction(month, month.getValue() > 8 ? new BigDecimal("0.09") : new BigDecimal("0.08")));
        return profile;
    }
}