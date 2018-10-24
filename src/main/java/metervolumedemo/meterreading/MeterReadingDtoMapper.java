package metervolumedemo.meterreading;

import org.springframework.stereotype.Component;

@Component
public class MeterReadingDtoMapper {

    public MeterReadingDto meterReadingToMeterReadingDto(MeterReading meterReading) {
        MeterReadingDto meterReadingDto = new MeterReadingDto();
        meterReadingDto.setId(meterReading.getId());
        meterReadingDto.setMeterId(meterReading.getMeterId());
        meterReadingDto.setProfileId(meterReading.getProfileId());
        meterReadingDto.setYear(meterReading.getYear());
        meterReadingDto.setReadings(meterReading.getReadings());
        return meterReadingDto;
    }

    public MeterReading meterReadingDtoToNewMeterReading(MeterReadingDto meterReadingDto) {
        MeterReading meterReading = new MeterReading();
        meterReadingDtoToMeterReading(meterReadingDto, meterReading);
        return meterReading;
    }

    public void meterReadingDtoToMeterReading(MeterReadingDto meterReadingDto, MeterReading meterReading) {
        meterReading.setMeterId(meterReadingDto.getMeterId());
        meterReading.setProfileId(meterReadingDto.getProfileId());
        meterReading.setYear(meterReadingDto.getYear());
        meterReadingDto.getReadings().forEach(meterReading::addReading);
    }
}
