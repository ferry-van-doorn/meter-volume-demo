package metervolumedemo.meterreading.csv;

import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import metervolumedemo.meterreading.MeterReading;
import metervolumedemo.meterreading.MeterReadingDto;
import metervolumedemo.meterreading.MeterReadingDtoMapper;
import metervolumedemo.meterreading.MeterReadingService;

@Component
@Validated
public class MeterReadingDtoService {

    private final MeterReadingDtoMapper meterReadingDtoMapper;
    private final MeterReadingService meterReadingService;

    public MeterReadingDtoService(MeterReadingDtoMapper meterReadingDtoMapper, MeterReadingService meterReadingService) {
        this.meterReadingDtoMapper = meterReadingDtoMapper;
        this.meterReadingService = meterReadingService;
    }

    public MeterReadingDto create(@Valid MeterReadingDto meterReadingDto) {
        MeterReading meterReading = meterReadingDtoMapper.meterReadingDtoToNewMeterReading(meterReadingDto);
        return meterReadingDtoMapper.meterReadingToMeterReadingDto(meterReadingService.create(meterReading));
    }
}
