package metervolumedemo.meterreading.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import metervolumedemo.profile.ProfileService;

@Component
public class ProfileExistsValidator implements ConstraintValidator<ProfileExists, Long> {

    private final ProfileService profileService;

    public ProfileExistsValidator(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void initialize(ProfileExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return profileService.existsById(value);
    }
}