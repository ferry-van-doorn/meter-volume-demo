package metervolumedemo.profile;

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
public class ProfileControllerTest {

    @Mock
    private ProfileDtoMapper profileDtoMapper;

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    @Test
    public void testFindByIdWhenIdExistsThenResultIsFound() {
        Long id = 1L;

        Profile profile = new Profile();
        when(profileService.findById(id)).thenReturn(Optional.of(profile));
        ProfileDto profileDto = new ProfileDto();
        when(profileDtoMapper.profileToProfileDto(profile)).thenReturn(profileDto);

        ProfileDto result = profileController.findById(id);

        assertThat(result).isSameAs(profileDto);
    }

    @Test
    public void testFindByIdWhenIdNotExistsThenThrowsResourceNotFoundException() {
        Long id = 1L;

        Profile profile = new Profile();
        when(profileService.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> profileController.findById(id)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void testCreate() {
        ProfileDto profileDto = new ProfileDto();
        Profile profile = new Profile();
        when(profileDtoMapper.profileDtoToNewProfile(profileDto)).thenReturn(profile);

        Profile createdprofile = new Profile();
        createdprofile.setProfile("A");
        when(profileService.create(profile)).thenReturn(createdprofile);

        ProfileDto createdProfileDto = new ProfileDto();
        createdProfileDto.setProfile("A");
        when(profileDtoMapper.profileToProfileDto(createdprofile)).thenReturn(createdProfileDto);

        ProfileDto result = profileController.create(profileDto);

        assertThat(result).isSameAs(createdProfileDto);
    }

    @Test
    public void testUpdateWhenExistingProfileThenUpdateProfile() {
        Long id = 1L;

        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(id);
        Profile profile = new Profile();

        when(profileService.findById(id)).thenReturn(Optional.of(profile));

        Profile updatedProfile = new Profile();
        updatedProfile.setProfile("A");
        when(profileService.update(profile)).thenReturn(updatedProfile);

        ProfileDto updatedProfileDto = new ProfileDto();
        updatedProfileDto.setProfile("A");
        when(profileDtoMapper.profileToProfileDto(updatedProfile)).thenReturn(updatedProfileDto);

        ProfileDto result = profileController.update(profileDto);

        verify(profileDtoMapper).profileDtoToProfile(profileDto, profile);
        assertThat(result).isSameAs(updatedProfileDto);
    }

    @Test
    public void testUpdateWhenNonExistingProfileThenThrowsResourceNotFoundException() {
        Long id = 1L;

        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(id);

        when(profileService.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> profileController.update(profileDto)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        profileController.delete(id);

        verify(profileService).delete(id);
    }
}
