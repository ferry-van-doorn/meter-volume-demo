package metervolumedemo.profile;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import metervolumedemo.common.ResourceInUseException;
import metervolumedemo.meterreading.MeterReadingService;

@Service
@Transactional
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final MeterReadingService meterReadingService;

    public ProfileService(ProfileRepository profileRepository, MeterReadingService meterReadingService) {
        this.profileRepository = profileRepository;
        this.meterReadingService = meterReadingService;
    }

    public Optional<Profile> findById(Long id) {
        return profileRepository.findById(id);
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Profile create(@Valid Profile profile) {
        if (profile.getId() != null) {
            throw new IllegalArgumentException("Profile id must be null");
        }

        return profileRepository.save(profile);
    }

    public Profile update(@Valid Profile profile) {
        if (profile.getId() == null) {
            throw new IllegalArgumentException("Profile id cannot be null");
        }
        return profileRepository.save(profile);
    }

    public boolean existsById(Long id) {
        return profileRepository.existsById(id);
    }

    public Optional<Profile> findByName(String profile) {
        return profileRepository.findByProfile(profile);
    }

    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Profile id cannot be null");
        }
        if (meterReadingService.existsByProfileId(id)) {
            throw new ResourceInUseException("Still used in meter reading");
        }
        profileRepository.deleteById(id);
    }
}
