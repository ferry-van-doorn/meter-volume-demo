package metervolumedemo.profile;

import org.springframework.stereotype.Component;

@Component
public class ProfileDtoMapper {

    ProfileDto profileToProfileDto(Profile profile) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(profile.getId());
        profileDto.setProfile(profile.getProfile());
        profileDto.setFractions(profile.getFractions());
        return profileDto;
    }

    Profile profileDtoToNewProfile(ProfileDto profileDto) {
        Profile profile = new Profile();
        profileDtoToProfile(profileDto, profile);
        return profile;
    }

    void profileDtoToProfile(ProfileDto profileDto, Profile profile) {
        profile.setProfile(profileDto.getProfile());
        profileDto.getFractions().forEach(profile::addFraction);
    }
}
