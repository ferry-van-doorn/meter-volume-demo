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

import metervolumedemo.common.ResourceNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class MeterReadingControllerTest {

    @Mock
    private MeterReadingService meterReadingService;

    @Mock
    private MeterReadingDtoMapper meterReadingDtoMapper;

    @InjectMocks
    private MeterReadingController meterReadingController;

    @Test
    public void findByIdWhenExistThenReturnMeterReading() {
        Long id = 1L;

        MeterReading meterReading = new MeterReading();
        when(meterReadingService.findById(id)).thenReturn(Optional.of(meterReading));
        MeterReadingDto meterReadingDto = new MeterReadingDto();
        when(meterReadingDtoMapper.meterReadingToMeterReadingDto(meterReading)).thenReturn(meterReadingDto);

        MeterReadingDto result = meterReadingController.findById(id);

        assertThat(result).isSameAs(meterReadingDto);
    }

    @Test
    public void findByIdWhenNotExistsThenThrowResourceNotFoundException() {
        Long id = 1L;

        when(meterReadingService.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> meterReadingController.findById(id)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void create() {
        MeterReadingDto meterReadingDto = new MeterReadingDto();

        MeterReading meterReading = new MeterReading();
        when(meterReadingDtoMapper.meterReadingDtoToNewMeterReading(meterReadingDto)).thenReturn(meterReading);
        MeterReading createdMeterReading = new MeterReading();
        createdMeterReading.setProfileId(2L);
        when(meterReadingService.create(meterReading)).thenReturn(createdMeterReading);
        MeterReadingDto createdMeterReadingDto = new MeterReadingDto();
        when(meterReadingDtoMapper.meterReadingToMeterReadingDto(createdMeterReading)).thenReturn(createdMeterReadingDto);

        MeterReadingDto result = meterReadingController.create(meterReadingDto);

        assertThat(result).isSameAs(createdMeterReadingDto);
    }

    @Test
    public void updateWhenEntityExistsThenDoUpdate() {
        MeterReadingDto meterReadingDto = new MeterReadingDto();

        MeterReading meterReading = new MeterReading();
        when(meterReadingService.findById(meterReadingDto.getId())).thenReturn(Optional.of(meterReading));
        MeterReading updatedMeterReading = new MeterReading();
        updatedMeterReading.setProfileId(2L);
        when(meterReadingService.update(meterReading)).thenReturn(updatedMeterReading);
        MeterReadingDto updatedMeterReadingDto = new MeterReadingDto();
        when(meterReadingDtoMapper.meterReadingToMeterReadingDto(updatedMeterReading)).thenReturn(updatedMeterReadingDto);

        MeterReadingDto result = meterReadingController.update(meterReadingDto);

        assertThat(result).isSameAs(updatedMeterReadingDto);
    }

    @Test
    public void updateWhenEntityNotExistsThenThrowResourceNotFoundException() {
        MeterReadingDto meterReadingDto = new MeterReadingDto();

        when(meterReadingService.findById(meterReadingDto.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> meterReadingController.update(meterReadingDto)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void delete() {
        Long id = 1L;

        meterReadingController.delete(id);

        verify(meterReadingService).deleteById(id);
    }
}