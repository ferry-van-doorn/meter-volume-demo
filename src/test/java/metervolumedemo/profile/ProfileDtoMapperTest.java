package metervolumedemo.profile;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class ProfileDtoMapperTest {

    private ProfileDtoMapper profileDtoMapper = new ProfileDtoMapper();

    @Test
    public void testProfileToProfileDto() {
        Profile profile = createProfile();

        ProfileDto result = profileDtoMapper.profileToProfileDto(profile);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(profile.getId());
        assertThat(result.getProfile()).isEqualTo(profile.getProfile());
        assertThat(result.getFractions()).containsAllEntriesOf(profile.getFractions());
    }

    @Test
    public void profileDtoToNewProfile() {
        ProfileDto profileDto = createProfileDto();

        Profile result = profileDtoMapper.profileDtoToNewProfile(profileDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getProfile()).isEqualTo(profileDto.getProfile());
        assertThat(result.getFractions()).containsAllEntriesOf(profileDto.getFractions());
    }

    @Test
    public void profileDtoToProfile() {
        ProfileDto profileDto = createProfileDto();

        Profile result = new Profile();
        profileDtoMapper.profileDtoToProfile(profileDto, result);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getProfile()).isEqualTo(profileDto.getProfile());
        assertThat(result.getFractions()).containsAllEntriesOf(profileDto.getFractions());
    }

    private Profile createProfile() {
        Profile profile = new Profile();
        profile.setProfile("A");
        Arrays.asList(Month.values()).forEach(month -> profile.addFraction(month, BigDecimal.ONE));
        return profile;
    }

    private ProfileDto createProfileDto() {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(1L);
        profileDto.setProfile("A");
        profileDto.setFractions(Arrays.stream(Month.values()).collect(Collectors.toMap(month -> month, month -> BigDecimal.ONE)));
        return profileDto;
    }
}