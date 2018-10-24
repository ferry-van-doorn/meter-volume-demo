package metervolumedemo.meterreading;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import metervolumedemo.common.ResourceNotFoundException;

@RestController
@RequestMapping("/meterreading")
@Validated
public class MeterReadingController {

    private final MeterReadingService meterReadingService;

    private final MeterReadingDtoMapper meterReadingDtoMapper;

    public MeterReadingController(MeterReadingService meterReadingService, MeterReadingDtoMapper meterReadingDtoMapper) {
        this.meterReadingService = meterReadingService;
        this.meterReadingDtoMapper = meterReadingDtoMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public MeterReadingDto findById(Long id) {
        return meterReadingDtoMapper.meterReadingToMeterReadingDto(meterReadingService.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public List<MeterReadingDto> findAll() {
        return meterReadingService.findAll().stream().map(meterReadingDtoMapper::meterReadingToMeterReadingDto).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public MeterReadingDto create(@Valid @RequestBody MeterReadingDto meterReadingDto) {
        MeterReading meterReading = meterReadingDtoMapper.meterReadingDtoToNewMeterReading(meterReadingDto);
        return meterReadingDtoMapper.meterReadingToMeterReadingDto(meterReadingService.create(meterReading));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public MeterReadingDto update(@Valid @RequestBody MeterReadingDto meterReadingDto) {
        MeterReading meterReading = meterReadingService.findById(meterReadingDto.getId()).orElseThrow(ResourceNotFoundException::new);
        meterReadingDtoMapper.meterReadingDtoToMeterReading(meterReadingDto, meterReading);
        return meterReadingDtoMapper.meterReadingToMeterReadingDto(meterReadingService.update(meterReading));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(Long id) {
        meterReadingService.deleteById(id);
    }
}
