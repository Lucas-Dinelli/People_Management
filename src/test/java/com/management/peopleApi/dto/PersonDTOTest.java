package com.management.peopleApi.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.management.peopleApi.utils.PersonUtils.createFakeDTO;

public class PersonDTOTest {
	
	private static final String FIRST_NAME_IS_BLANK = "The firstName field must be filled";
	
	private static final String FIRST_NAME_INCORRECT_SIZE = "The firstName field must be between 2 and 100 characters";
	
	private static final String LAST_NAME_IS_BLANK = "The lastName field must be filled";
	
	private static final String LAST_NAME_INCORRECT_SIZE = "The lastName field must be between 2 and 100 characters";
	
	private static final String CPF_IS_NULL = "The cpf field must be filled and only with numbers";
	
	private static final String INVALID_CPF = "Invalid CPF";
	
	private static final String CPF_INCORRECT_SIZE = "Just 11 numbers";
	
	private static final String BIRTH_DATE_IS_NULL = "The birthDate field must be entered in the following format: dd-MM-yyyy";
	
	private Validator validator;
	
	@BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
    void test_NoViolations_WhenSuccessful() {
		PersonDTO personDTO =  createFakeDTO();

		Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
		
		assertTrue(violations.isEmpty());
    }
	
	@Test
    void test_ExistsViolation_WhenFirstNameIsBlank() {
		PersonDTO personDTO =  createFakeDTO();
		personDTO.setFirstName("   ");

		Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(FIRST_NAME_IS_BLANK)));
    }
	
	@Test
    void test_ExistsViolation_WhenFirstNameIncorrectSize() {
		PersonDTO personDTO =  createFakeDTO();
		personDTO.setFirstName("F");

		Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(FIRST_NAME_INCORRECT_SIZE)));
    }
	
	@Test
    void test_ExistsViolation_WhenLastNameIsBlank() {
		PersonDTO personDTO =  createFakeDTO();
		personDTO.setLastName("   ");

		Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(LAST_NAME_IS_BLANK)));
    }
	
	@Test
    void test_ExistsViolation_WhenLastNameIncorrectSize() {
		PersonDTO personDTO =  createFakeDTO();
		personDTO.setLastName("L");

		Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(LAST_NAME_INCORRECT_SIZE)));
    }
	
	@Test
    void test_ExistsViolation_WhenCpfIsNull() {
		PersonDTO personDTO =  createFakeDTO();
		personDTO.setCpf(null);

		Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(CPF_IS_NULL)));
    }
	
	@Test
    void test_ExistsViolation_WhenCpfIsInvalid() {
		PersonDTO personDTO =  createFakeDTO();
		personDTO.setCpf("12345678912");

		Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(INVALID_CPF)));
    }
	
	@Test
    void test_ExistsViolation_WhenCpfIncorrectSize() {
		PersonDTO personDTO =  createFakeDTO();
		personDTO.setCpf("369.333.878-79");

		Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(CPF_INCORRECT_SIZE)));
    }
	
	@Test
    void test_ExistsViolation_WhenBirthDateIsNull() {
		PersonDTO personDTO =  createFakeDTO();
		personDTO.setBirthDate(null);

		Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(BIRTH_DATE_IS_NULL)));
    }
	
}
