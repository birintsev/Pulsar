package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserRegistrationDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;

/**
 * Converts {@link UserRegistrationDTO} to {@link User}
 * <p>
 * Designed for use in registration request handling
 * */
public class UserRegistrationDTOConverter
    implements Function<UserRegistrationDTO, User> {

    private static final Logger LOGGER = Logger.getLogger(
        UserRegistrationDTOConverter.class
    );

    private final Validator validator;

    /**
     * A default constructor
     * */
    public UserRegistrationDTOConverter() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public User apply(UserRegistrationDTO dto) {
        validate(dto);
        return new User(
            new User.UserID(dto.getEmail()),
            dto.getUsername(),
            dto.getFirstName(),
            dto.getLastName(),
            dto.getAge(),
            dto.getPhoneNumber(),
            dto.getPassword(),
            new HashSet<>()
        );
    }

    /**
     * This method is aimed only to encapsulate validation logic
     * and to reduce code
     * in the main ({@link #apply(UserRegistrationDTO)}) method
     * */
    private void validate(UserRegistrationDTO dto) {
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations =
            validator.validate(dto);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(
                "Converting invalid UserRegistrationDTO"
                    + " to User."
                    + " The source is below:"
                    + System.lineSeparator()
                    + dto
                    + System.lineSeparator()
                    + "Constraints violations are below:"
                    + System.lineSeparator()
                    + constraintViolations
            );
        }
    }
}
