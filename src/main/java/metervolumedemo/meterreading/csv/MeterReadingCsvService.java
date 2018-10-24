package metervolumedemo.meterreading.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import metervolumedemo.meterreading.MeterReadingDto;
import metervolumedemo.profile.ProfileService;

@Component
public class MeterReadingCsvService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeterReadingCsvService.class);

    private final ProfileService profileService;
    private final MeterReadingDtoService meterReadingDtoService;

    public MeterReadingCsvService(ProfileService profileService, MeterReadingDtoService meterReadingDtoService) {
        this.profileService = profileService;
        this.meterReadingDtoService = meterReadingDtoService;
    }

    public void readCsv(File file) {

        List<MeterReadingCsvDto> meterReadingCsvDtos = parseCsv(file);

        List<MeterReadingDto> meterReadingDtos = convertToMeterReadingDto(meterReadingCsvDtos);

        storeMeterReadings(meterReadingDtos);
    }

    private List<MeterReadingCsvDto> parseCsv(File file) {
        CsvToBean<MeterReadingCsvDto> csvToBean;
        try {
            csvToBean = new CsvToBeanBuilder<MeterReadingCsvDto>(new FileReader(file)).withType(MeterReadingCsvDto.class).withSeparator(',').withQuoteChar('\'')
                                                                                      .build();
            return csvToBean.parse();
        } catch (FileNotFoundException e) {
            throw new CannotProcessCsvException("Parsing of CSV file failed", e);
        }
    }

    private List<MeterReadingDto> convertToMeterReadingDto(List<MeterReadingCsvDto> meterReadingCsvDtos) {

        Map<String, List<MeterReadingCsvDto>> groupByMeterId = meterReadingCsvDtos.stream().collect(Collectors.groupingBy(MeterReadingCsvDto::getMeterId));

        return groupByMeterId.entrySet().stream().map(this::mapToMeterReadingDto).collect(Collectors.toList());
    }

    private MeterReadingDto mapToMeterReadingDto(Map.Entry<String, List<MeterReadingCsvDto>> item) {
        MeterReadingDto meterReadingDto = new MeterReadingDto();
        meterReadingDto.setMeterId(item.getKey());
        meterReadingDto.setReadings(item.getValue().stream().collect(
                Collectors.toMap(line -> monthStringToMonth(line.getMonth()), line -> BigDecimal.valueOf(line.getMeterReading()))));
        profileService.findByName(item.getValue().get(0).getProfile()).ifPresent(profile -> meterReadingDto.setProfileId(profile.getId()));
        // NOTE: It is assumed that readings are uploaded in the year after the one that is measured
        meterReadingDto.setYear(LocalDate.now().getYear() - 1);
        return meterReadingDto;
    }

    private Month monthStringToMonth(String month) {
        return Arrays.stream(Month.values()).filter(longName -> longName.getDisplayName(TextStyle.SHORT, Locale.getDefault()).equalsIgnoreCase(month))
                     .findFirst().orElse(null);
    }

    private void storeMeterReadings(List<MeterReadingDto> meterReadingDtos) {
        meterReadingDtos.forEach(meterReadingDto -> {
            try {
                meterReadingDtoService.create(meterReadingDto);
            } catch (ValidationException e) {
                LOGGER.warn("Failed to process meter readings of meter with id: {}", meterReadingDto.getMeterId(), e);
            }
        });
    }
}
