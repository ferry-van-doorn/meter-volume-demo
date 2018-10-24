package metervolumedemo.meterreading;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MeterReadingServiceTest {

    @Mock
    private MeterReadingRepository meterReadingRepository;

    @InjectMocks
    private MeterReadingService meterReadingService;

    @Test
    public void testCreateWithInCorrectProfileThenResultIsStored() {
        MeterReading meterReading = new MeterReading();

        MeterReading createdMeterReading = new MeterReading();
        when(meterReadingRepository.save(meterReading)).thenReturn(createdMeterReading);

        MeterReading result = meterReadingService.create(meterReading);

        assertThat(result).isSameAs(createdMeterReading);
    }

    @Test
    public void testCreateWithInCorrectProfileThenExpectIllegalArgumentException() {
        MeterReading meterReading = new MeterReadingWithId(2L);

        assertThatThrownBy(() -> meterReadingService.create(meterReading)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateWithCorrectProfileThenResultIsStored() {
        MeterReading meterReading = new MeterReadingWithId(2L);

        MeterReading updatedMeterReading = new MeterReading();
        when(meterReadingRepository.save(meterReading)).thenReturn(updatedMeterReading);

        MeterReading result = meterReadingService.update(meterReading);

        assertThat(result).isSameAs(updatedMeterReading);
    }

    @Test
    public void testUpdateWithInCorrectProfileThenExpectIllegalArgumentException() {
        MeterReading meterReading = new MeterReading();

        assertThatThrownBy(() -> meterReadingService.update(meterReading)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindById() {
        Long id = 1L;

        Optional<MeterReading> optionalMeterReading = Optional.of(new MeterReading());
        when(meterReadingRepository.findById(id)).thenReturn(optionalMeterReading);

        Optional<MeterReading> result = meterReadingService.findById(id);

        assertThat(result).isSameAs(optionalMeterReading);
    }

    @Test
    public void testExistsByProfileId() {
        Long profileId = 2L;

        when(meterReadingRepository.existsByProfileId(profileId)).thenReturn(true);

        boolean result = meterReadingService.existsByProfileId(profileId);

        assertThat(result).isTrue();
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;

        meterReadingService.deleteById(id);

        verify(meterReadingRepository).deleteById(id);
    }

    private static class MeterReadingWithId extends MeterReading {

        MeterReadingWithId(Long id) {
            setId(id);
        }
    }
}