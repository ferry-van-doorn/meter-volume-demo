package metervolumedemo.meterreading.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import metervolumedemo.profile.ProfileService;

@RunWith(MockitoJUnitRunner.class)
public class ProfileExistsValidatorTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileExistsValidator profileExistsValidator;

    @Test
    public void testIsValidWhenValueNullThenReturnFalse() {

        assertThat(profileExistsValidator.isValid(null, null)).isFalse();
    }

    @Test
    public void testIsValidWhenProfileNotExistsThenReturnFalse() {
        Long profileId = 1L;

        when(profileService.existsById(profileId)).thenReturn(false);

        assertThat(profileExistsValidator.isValid(profileId, null)).isFalse();
    }

    @Test
    public void testIsValidWhenProfileExistsThenReturnTrue() {
        Long profileId = 1L;

        when(profileService.existsById(profileId)).thenReturn(true);

        assertThat(profileExistsValidator.isValid(profileId, null)).isTrue();
    }
}