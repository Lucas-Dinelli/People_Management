package com.management.peopleApi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.management.peopleApi.utils.PersonUtils.createFakeModel;

import com.management.peopleApi.model.Person;

@DataJpaTest
public class PersonRepositoryTest {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Test
	void save_ReturnsPersistedPerson_WhenSuccessful() {
		Person expectedPerson = createFakeModel();
		
		Person personSaved = this.personRepository.save(expectedPerson);
		
		expectedPerson.setId(personSaved.getId());
		expectedPerson.getPhones().get(0).setId(personSaved.getPhones().get(0).getId());
		
		assertNotNull(personSaved);
		assertEquals(expectedPerson, personSaved);
	}
	
	@Test
	void save_ReturnsUpdatedPerson_WhenExistingPersonIsEdited() {
		
		Person personSaved = this.personRepository.save(createFakeModel());
		
		Long oldId = personSaved.getId();
		String oldFirstName = personSaved.getFirstName();
		
		Person personToBeUpdated = personSaved;
		personToBeUpdated.setFirstName("Update");
		
		Person updatedPerson = this.personRepository.save(personToBeUpdated);
		
		assertEquals(oldId, updatedPerson.getId());
		assertNotEquals(oldFirstName, updatedPerson.getFirstName());
		assertEquals("Update", updatedPerson.getFirstName());
	}
	
	@Test
	void save_ThrowsDataIntegrityViolationException_WhenPersonIsEmpty() {
		Person emptyPerson = new Person();
		
		assertThrows(DataIntegrityViolationException.class, () -> this.personRepository.save(emptyPerson));
	}
	
	@Test
	void save_ThrowsDataIntegrityViolationException_WhenCpfAlreadyExists() {
		Person person = createFakeModel();
		
		personRepository.save(person);
		
		assertThrows(DataIntegrityViolationException.class, () -> this.personRepository.save(createFakeModel()));
	}
	
	@Test
	void findById_ReturnsPerson_WhenSuccessful() {
		
		Person personSaved = this.personRepository.save(createFakeModel());
		
		Optional<Person> personReturned = this.personRepository.findById(personSaved.getId());
		
		assertEquals(true, personReturned.isPresent());
		assertEquals(personSaved, personReturned.get());
	}
	
	@Test
	void findById_ReturnsOptionalEmpty_WhenNotExistsId() {
		
		Person personSaved = this.personRepository.save(createFakeModel());
		
		Optional<Person> personReturned = this.personRepository.findById(personSaved.getId() + 100);
		
		assertEquals(false, personReturned.isPresent());
	}
	
	@Test
	void findAll_ReturnsListOfPeople_WhenSuccessful() {
		Person firstPerson = createFakeModel();
		Person secondPerson = createFakeModel();
		
		firstPerson.setFirstName("First");
		secondPerson.setFirstName("Second");
		secondPerson.setCpf("12345678963");
		
		firstPerson = personRepository.save(firstPerson);
		secondPerson = personRepository.save(secondPerson);
		
		List<Person> people = this.personRepository.findAll();
		
		assertEquals(2, people.size());
		assertEquals(firstPerson, people.get(0));
		assertEquals(secondPerson, people.get(1));
	}
	
	@Test
	void findAll_ReturnsEmptyList_WhenNoPersonExists() {
		
		List<Person> people = this.personRepository.findAll();
		
		assertEquals(true, people.isEmpty());
	}
	
	@Test
	void delete_ReturnsVoid_WhenSuccessful() {
		
		Person personSaved = this.personRepository.save(createFakeModel());
		
		this.personRepository.deleteById(personSaved.getId());
		
		Optional<Person> personReturned = this.personRepository.findById(personSaved.getId());
		
		assertEquals(false, personReturned.isPresent());
	}
}
