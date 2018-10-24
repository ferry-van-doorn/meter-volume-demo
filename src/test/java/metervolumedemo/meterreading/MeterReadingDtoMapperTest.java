package metervolumedemo.meterreading;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class MeterReadingDtoMapperTest {

    private MeterReadingDtoMapper meterReadingDtoMapper = new MeterReadingDtoMapper();

    @Test
    public void testMeterReadingToMeterReadingDto() {
        MeterReading meterReading = createMeterReading();

        MeterReadingDto result = meterReadingDtoMapper.meterReadingToMeterReadingDto(meterReading);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(meterReading.getId());
        assertThat(result.getProfileId()).isEqualTo(meterReading.getProfileId());
        assertThat(result.getMeterId()).isEqualTo(meterReading.getMeterId());
        assertThat(result.getYear()).isEqualTo(meterReading.getYear());
        assertThat(result.getReadings()).containsAllEntriesOf(meterReading.getReadings());
    }

    @Test
    public void testMeterReadingDtoToNewMeterReading() {
        MeterReadingDto meterReadingDto = createMeterReadingDto();

        MeterReading result = meterReadingDtoMapper.meterReadingDtoToNewMeterReading(meterReadingDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(meterReadingDto.getId());
        assertThat(result.getProfileId()).isEqualTo(meterReadingDto.getProfileId());
        assertThat(result.getMeterId()).isEqualTo(meterReadingDto.getMeterId());
        assertThat(result.getYear()).isEqualTo(meterReadingDto.getYear());
        assertThat(result.getReadings()).containsAllEntriesOf(meterReadingDto.getReadings());
    }

    @Test
    public void testMeterReadingDtoToMeterReading() {
        MeterReadingDto meterReadingDto = createMeterReadingDto();
        MeterReading meterReading = new MeterReading();

        meterReadingDtoMapper.meterReadingDtoToMeterReading(meterReadingDto, meterReading);

        assertThat(meterReading.getId()).isNull();
        assertThat(meterReading.getMeterId()).isEqualTo(meterReadingDto.getMeterId());
        assertThat(meterReading.getProfileId()).isEqualTo(meterReadingDto.getProfileId());
        assertThat(meterReading.getYear()).isEqualTo(meterReadingDto.getYear());
        assertThat(meterReading.getReadings()).containsAllEntriesOf(meterReadingDto.getReadings());
    }

    private MeterReading createMeterReading() {
        MeterReading meterReading = new MeterReadingWithId(2L);
        meterReading.setYear(2018);
        meterReading.setProfileId(1L);
        Arrays.stream(Month.values()).forEach(month -> meterReading.addReading(month, BigDecimal.ONE));
        return meterReading;
    }

    private MeterReadingDto createMeterReadingDto() {
        MeterReadingDto meterReadingDto = new MeterReadingDto();
        meterReadingDto.setMeterId("2");
        meterReadingDto.setProfileId(1L);
        meterReadingDto.setYear(2018);
        meterReadingDto.setReadings(Arrays.stream(Month.values()).collect(Collectors.toMap(month -> month, month -> BigDecimal.ONE)));
        return meterReadingDto;
    }

    private static class MeterReadingWithId extends MeterReading {

        MeterReadingWithId(Long id) {
            setId(id);
        }
    }
}
