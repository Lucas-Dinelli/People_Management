package com.management.peopleApi.utils;

import java.time.LocalDate;
import java.util.Collections;

import com.management.peopleApi.dto.PersonDTO;
import com.management.peopleApi.model.Person;

public class PersonUtils {
	
	private static final long PERSON_ID = 1L;
	private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "Name";
    private static final String CPF_NUMBER = "36933387879";
    public static final LocalDate BIRTH_DATE = LocalDate.of(2000, 3, 5);
    
    public static PersonDTO createFakeDTO() {
    	return PersonDTO.builder()
    			.firstName(FIRST_NAME)
    			.lastName(LAST_NAME)
    			.cpf(CPF_NUMBER)
    			.birthDate(BIRTH_DATE)
    			.phones(Collections.singletonList(PhoneUtils.createFakeDTO()))
    			.build();
    }
    
    public static Person createFakeModel() {
        return Person.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate(BIRTH_DATE)
                .phones(Collections.singletonList(PhoneUtils.createFakeModel()))
                .build();
    }
}
