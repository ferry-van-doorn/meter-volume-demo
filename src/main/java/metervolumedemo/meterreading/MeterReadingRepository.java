package metervolumedemo.meterreading;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterReadingRepository  extends JpaRepository<MeterReading, Long> {

    boolean existsByProfileId(Long profileId);
}
