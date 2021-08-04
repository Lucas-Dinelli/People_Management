package com.management.peopleApi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.any;

import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import com.management.peopleApi.dto.PersonDTO;
import com.management.peopleApi.model.Person;
import com.management.peopleApi.repository.PersonRepository;
import com.management.peopleApi.service.exceptions.DataIntegrityViolationCustomException;
import com.management.peopleApi.service.exceptions.ObjectNotFoundException;

import static com.management.peopleApi.utils.PersonUtils.createFakeDTO;
import static com.management.peopleApi.utils.PersonUtils.createFakeModel;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	
	@InjectMocks
	private PersonService personService;
	
	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Test
	void create_ReturnsPersonDTO_WhenSuccessful() {
		PersonDTO personDTO = createFakeDTO();
        Person expectedPerson = createFakeModel();
        
        when(modelMapper.map(personDTO, Person.class)).thenReturn(expectedPerson);
        when(personRepository.save(any(Person.class))).thenReturn(expectedPerson);
        when(modelMapper.map(expectedPerson, PersonDTO.class)).thenReturn(personDTO);
        
        personDTO = personService.create(personDTO);
        
        assertEquals(1L, expectedPerson.getId());
        assertEquals(expectedPerson.getFirstName(), personDTO.getFirstName());
        assertEquals(expectedPerson.getLastName(), personDTO.getLastName());
        assertEquals(expectedPerson.getCpf(), personDTO.getCpf());
        assertEquals(expectedPerson.getBirthDate(), personDTO.getBirthDate());
	}
	
	@Test
	void  create_ThrowsDataIntegrityViolationCustomException_WhenDataIntegrityIsViolated() {
		PersonDTO personDTO = createFakeDTO();
		Person expectedCreatedPerson = createFakeModel();
		
		when(modelMapper.map(personDTO, Person.class)).thenReturn(expectedCreatedPerson);
		when(personRepository.save(any(Person.class))).thenThrow(DataIntegrityViolationException.class);
        
       assertThrows(DataIntegrityViolationCustomException.class, () -> personService.create(personDTO));
	}
	
	@Test
	void findById_ReturnsPersonDTO_WhenSuccessful() {
		PersonDTO personDTO = createFakeDTO();
		Person expectedPerson = createFakeModel();
		
		when(personRepository.findById(expectedPerson.getId())).thenReturn(Optional.of(expectedPerson));
		when(modelMapper.map(expectedPerson, PersonDTO.class)).thenReturn(personDTO);
		
		personDTO = personService.findById(1L);
		
        assertEquals(expectedPerson.getFirstName(), personDTO.getFirstName());
        assertEquals(expectedPerson.getLastName(), personDTO.getLastName());
        assertEquals(expectedPerson.getCpf(), personDTO.getCpf());
        assertEquals(expectedPerson.getBirthDate(), personDTO.getBirthDate());
	}
	
	@Test
	void findById_ThrowsObjectNotFoundException_WhenPersonIsEmpty() {
		
		assertThrows(ObjectNotFoundException.class, () -> personService.findById(1L));
	}
	
	@Test
	void findAll_ReturnsListOfPersonDTO_WhenSuccessful() {
		List<PersonDTO> peopleDTO = Collections.singletonList(createFakeDTO());
		List<Person> expectedPeople = Collections.singletonList(createFakeModel());
		
		when(personRepository.findAll()).thenReturn(expectedPeople);
		when(modelMapper.map(expectedPeople.get(0), PersonDTO.class)).thenReturn(peopleDTO.get(0));
		
		peopleDTO = personService.findAll();
		
		assertEquals(peopleDTO.size(), 1);
		assertEquals(expectedPeople.get(0).getFirstName(), peopleDTO.get(0).getFirstName());
		assertEquals(expectedPeople.get(0).getLastName(), peopleDTO.get(0).getLastName());
		assertEquals(expectedPeople.get(0).getCpf(), peopleDTO.get(0).getCpf());
		assertEquals(expectedPeople.get(0).getBirthDate(), peopleDTO.get(0).getBirthDate());
	}
	
	@Test
	void findAll_ReturnsEmptyList_WhenNoPersonExists() {
		List<PersonDTO> peopleDTO;
		List<Person> expectedPeople = Collections.emptyList();
		
		when(personRepository.findAll()).thenReturn(expectedPeople);
		
		peopleDTO = personService.findAll();
		
		assertEquals(0, peopleDTO.size());
	}
	
	@Test
	void update_ReturnsUpdatedPersonDTO_WhenSuccessful() {
		PersonDTO personDTO = createFakeDTO();
		Person expectedPerson = createFakeModel();
		
		personDTO.setFirstName("Update");
		expectedPerson.setFirstName("Update");
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(expectedPerson));
		when(modelMapper.map(personDTO, Person.class)).thenReturn(expectedPerson);
		when(personRepository.save(any(Person.class))).thenReturn(expectedPerson);
		when(modelMapper.map(expectedPerson, PersonDTO.class)).thenReturn(personDTO);
		
		personDTO = personService.update(1L, personDTO);
		
		assertEquals(1L, expectedPerson.getId());
		assertEquals(expectedPerson.getFirstName(), personDTO.getFirstName());
		assertEquals("Update", personDTO.getFirstName());
		assertEquals(expectedPerson.getLastName(), personDTO.getLastName());
        assertEquals(expectedPerson.getCpf(), personDTO.getCpf());
        assertEquals(expectedPerson.getBirthDate(), personDTO.getBirthDate());
	}
	
	@Test
	void delete_ReturnsVoid_WhenSuccessful() {
		Person expectedPerson = createFakeModel();
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(expectedPerson));
		doNothing().when(personRepository).deleteById(expectedPerson.getId());
		
		personService.delete(1L);
	}
}
