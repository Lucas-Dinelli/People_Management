package com.management.peopleApi.controller;

import static org.hamcrest.core.Is.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.any;

import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.management.peopleApi.dto.PersonDTO;
import com.management.peopleApi.service.PersonService;

import io.restassured.http.ContentType;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import static com.management.peopleApi.utils.PersonUtils.createFakeDTO;

@WebMvcTest
public class PersonControllerTest {
	
	@Autowired
	private PersonController personController;
	
	@MockBean
	private PersonService personService;
	
	private final String PATH = "http://localhost:8080/v1/person";
	
	@BeforeEach
	public void setUp() {
		standaloneSetup(this.personController);
	}
	
	@Test
	void create_ReturnsCreated_WhenSuccessful() {
		PersonDTO expectedPersonDTO = createFakeDTO();
		expectedPersonDTO.setId(1L);
		
		when(personService.create(any(PersonDTO.class))).thenReturn(expectedPersonDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
			.body(expectedPersonDTO)
		.when()
			.post(PATH)
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	void findById_ReturnsPersonDTO_WhenSuccessful() {
		PersonDTO expectedPersonDTO = createFakeDTO();
		expectedPersonDTO.setId(2L);
		when(personService.findById(any(Long.class))).thenReturn(expectedPersonDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.get(PATH + "/{id}", expectedPersonDTO.getId())
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("id", is(2))
			.body("firstName", is(expectedPersonDTO.getFirstName()))
			.body("lastName", is(expectedPersonDTO.getLastName()))
			.body("cpf", is(expectedPersonDTO.getCpf()))
			.body("birthDate", is("05-03-2000"))
			.body("phones[0].type", is(expectedPersonDTO.getPhones().get(0).getType().toString()))
			.body("phones[0].number", is(expectedPersonDTO.getPhones().get(0).getNumber().toString()));
	}
	
	@Test
	void findAll_ReturnsListOfPersonDTO_WhenSuccessful() {
		PersonDTO firstExpectedPersonDTO = createFakeDTO();
		firstExpectedPersonDTO.setId(3L);
		
		PersonDTO secondExpectedPersonDTO = createFakeDTO();
		secondExpectedPersonDTO.setId(4L);
		secondExpectedPersonDTO.setFirstName("Second Test");
		
		List<PersonDTO>peopleDTO = new ArrayList<>();
		peopleDTO.add(firstExpectedPersonDTO);
		peopleDTO.add(secondExpectedPersonDTO);
		
		when(personService.findAll()).thenReturn(peopleDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.get(PATH)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("[0].id", is(3))
			.body("[0].firstName", is(firstExpectedPersonDTO.getFirstName()))
			.body("[0].firstName", is("Test"))
			.body("[0].lastName", is(firstExpectedPersonDTO.getLastName()))
			.body("[0].cpf", is(firstExpectedPersonDTO.getCpf()))
			.body("[0].birthDate", is("05-03-2000"))
			.body("[0].phones[0].type", is(firstExpectedPersonDTO.getPhones().get(0).getType().toString()))
			.body("[0].phones[0].number", is(firstExpectedPersonDTO.getPhones().get(0).getNumber().toString()))
			
			.body("[1].id", is(4))
			.body("[1].firstName", is(secondExpectedPersonDTO.getFirstName()))
			.body("[1].firstName", is("Second Test"))
			.body("[1].lastName", is(secondExpectedPersonDTO.getLastName()))
			.body("[1].cpf", is(secondExpectedPersonDTO.getCpf()))
			.body("[1].birthDate", is("05-03-2000"))
			.body("[1].phones[0].type", is(secondExpectedPersonDTO.getPhones().get(0).getType().toString()))
			.body("[1].phones[0].number", is(secondExpectedPersonDTO.getPhones().get(0).getNumber().toString()));
	}
	
	@Test
	void update_ReturnsUpdatedPersonDTO_WhenSuccessful() {
		PersonDTO personDTOUpdated = createFakeDTO();
		personDTOUpdated.setId(5L);
		
		when(personService.update(any(Long.class), any(PersonDTO.class))).thenReturn(personDTOUpdated);
		
		personDTOUpdated.setFirstName("Updated Test");
		
		given().log().all()
			.contentType(ContentType.JSON)
			.body(personDTOUpdated)
		.when()
			.put(PATH + "/{id}", 5)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("id", is(5))
			.body("firstName", is(personDTOUpdated.getFirstName()))
			.body("firstName", is("Updated Test"))
			.body("lastName", is(personDTOUpdated.getLastName()))
			.body("cpf", is(personDTOUpdated.getCpf()))
			.body("birthDate", is("05-03-2000"))
			.body("phones[0].type", is(personDTOUpdated.getPhones().get(0).getType().toString()))
			.body("phones[0].number", is(personDTOUpdated.getPhones().get(0).getNumber().toString()));
	}
	
	@Test
	void delete_ReturnsNoContent_WhenSuccessful() {
		
		doNothing().when(personService).delete(any(Long.class));
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.delete(PATH + "/{id}", 1)
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
}
