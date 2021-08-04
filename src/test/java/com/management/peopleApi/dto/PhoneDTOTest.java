package com.management.peopleApi.dto;

import static com.management.peopleApi.utils.PhoneUtils.createFakeDTO;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhoneDTOTest {
	
	private final String TYPE_IS_NULL = "The type field must be entered with the following values: [HOME, MOBILE or COMMERCIAL]";
	
	private final String NUMBER_IS_BLANK = "The number field must be filled";
	
	private final String NUMBER_INCORRECT_SIZE = "The number must be between 8 and 15 characters.";
	
	private Validator validator;
	
	@BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
    void test_NoViolations_WhenSuccessful() {
		PhoneDTO phoneDTO = createFakeDTO();

		Set<ConstraintViolation<PhoneDTO>> violations = validator.validate(phoneDTO);
		
		assertTrue(violations.isEmpty());
    }
	
	@Test
    void test_ExistsViolation_WhenTypeIsNull() {
		PhoneDTO phoneDTO = createFakeDTO();
		phoneDTO.setType(null);

		Set<ConstraintViolation<PhoneDTO>> violations = validator.validate(phoneDTO);
		
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(TYPE_IS_NULL)));
    }
	
	@Test
    void test_ExistsViolation_WhenNumberIsBlank() {
		PhoneDTO phoneDTO = createFakeDTO();
		phoneDTO.setNumber("   ");

		Set<ConstraintViolation<PhoneDTO>> violations = validator.validate(phoneDTO);
		
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(NUMBER_IS_BLANK)));
    }
	
	@Test
    void test_ExistsViolation_WhenNumberIncorrectSize() {
		PhoneDTO phoneDTO = createFakeDTO();
		phoneDTO.setNumber("9999999");

		Set<ConstraintViolation<PhoneDTO>> violations = validator.validate(phoneDTO);
		
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(NUMBER_INCORRECT_SIZE)));
    }
}
