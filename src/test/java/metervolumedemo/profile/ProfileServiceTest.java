package metervolumedemo.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import metervolumedemo.common.ResourceInUseException;
import metervolumedemo.common.ResourceNotFoundException;
import metervolumedemo.meterreading.MeterReadingService;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private MeterReadingService meterReadingService;

    @InjectMocks
    private ProfileService profileService;

    @Test
    public void testCreateWithCorrectProfileThenResultIsStored() {
        Profile profile = new Profile();

        when(profileRepository.save(profile)).thenReturn(profile);

        Profile result = profileService.create(profile);

        assertThat(result).isSameAs(profile);
    }

    @Test
    public void testCreateWithInCorrectProfileThenExpectIllegalArgumentException() {
        ProfileWithId profile = new ProfileWithId(1L);

        assertThatThrownBy(() -> profileService.create(profile)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void tesUpdateWithCorrectProfileThenResultIsStored() {
        ProfileWithId profile = new ProfileWithId(1L);

        when(profileRepository.save(profile)).thenReturn(profile);

        Profile result = profileService.update(profile);

        assertThat(result).isSameAs(profile);
    }

    @Test
    public void tesUpdateWithInCorrectProfileThenExpectIllegalArgumentException() {
        Profile profile = new Profile();

        assertThatThrownBy(() -> profileService.update(profile)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFind() {
        Long id = 1L;

        Optional<Profile> optionalProfile = Optional.of(new Profile());
        when(profileRepository.findById(id)).thenReturn(optionalProfile);

        Optional<Profile> result = profileService.findById(id);

        assertThat(result).isSameAs(optionalProfile);
    }

    @Test
    public void testDeleteWhenNotUsedByMeterReadingThenExecuteDelete() {
        Long id = 1L;

        when(meterReadingService.existsByProfileId(id)).thenReturn(false);

        profileService.delete(id);

        verify(profileRepository).deleteById(id);
    }

    @Test
    public void testDeleteWhenUsedByMeterReadingThenThrowsResourceInUseException() {
        Long id = 1L;

        when(meterReadingService.existsByProfileId(id)).thenReturn(true);

        assertThatThrownBy(() -> profileService.delete(id)).isInstanceOf(ResourceInUseException.class);
    }

    private static class ProfileWithId extends Profile {

        ProfileWithId(Long id) {
            setId(id);
        }
    }
}
