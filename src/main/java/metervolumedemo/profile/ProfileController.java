package metervolumedemo.profile;

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
@RequestMapping("/profile")
@Validated
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileDtoMapper profileDtoMapper;

    public ProfileController(ProfileService profileService, ProfileDtoMapper profileDtoMapper) {
        this.profileService = profileService;
        this.profileDtoMapper = profileDtoMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ProfileDto findById(Long id) {
        return profileDtoMapper.profileToProfileDto(profileService.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public List<ProfileDto> findAll() {
        return profileService.findAll().stream().map(profileDtoMapper::profileToProfileDto).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ProfileDto create(@Valid @RequestBody ProfileDto profileDto) {
        Profile profile = profileDtoMapper.profileDtoToNewProfile(profileDto);
        return profileDtoMapper.profileToProfileDto(profileService.create(profile));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ProfileDto update(@Valid @RequestBody ProfileDto profileDto) {
        Profile profile = profileService.findById(profileDto.getId()).orElseThrow(ResourceNotFoundException::new);
        profileDtoMapper.profileDtoToProfile(profileDto, profile);
        return profileDtoMapper.profileToProfileDto(profileService.update(profile));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(Long id) {
        profileService.delete(id);
    }
}
