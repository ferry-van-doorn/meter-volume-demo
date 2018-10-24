package metervolumedemo.meterreading;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class MeterReadingService {

    private final MeterReadingRepository meterReadingRepository;

    public MeterReadingService(MeterReadingRepository meterReadingRepository) {
        this.meterReadingRepository = meterReadingRepository;
    }

    public Optional<MeterReading> findById(Long id) {
        return meterReadingRepository.findById(id);
    }

    public List<MeterReading> findAll() {
        return meterReadingRepository.findAll();
    }

    public MeterReading create(MeterReading meterReading) {
        if (meterReading.getId() != null) {
            throw new IllegalArgumentException("MeterReading id must be null");
        }
        return meterReadingRepository.save(meterReading);
    }

    public MeterReading update(MeterReading meterReading) {
        if (meterReading.getId() == null) {
            throw new IllegalArgumentException("MeterReading id cannot be null");
        }
        return meterReadingRepository.save(meterReading);
    }

    public boolean existsByProfileId(Long profileId) {
        return meterReadingRepository.existsByProfileId(profileId);
    }

    public void deleteById(Long id) {
        meterReadingRepository.deleteById(id);
    }
}
