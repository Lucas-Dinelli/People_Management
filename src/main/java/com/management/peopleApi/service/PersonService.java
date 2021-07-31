package com.management.peopleApi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.management.peopleApi.dto.PersonDTO;
import com.management.peopleApi.model.Person;
import com.management.peopleApi.repository.PersonRepository;
import com.management.peopleApi.service.exceptions.DataIntegrityViolationCustomException;
import com.management.peopleApi.service.exceptions.ObjectNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {
	
	private PersonRepository personRepository;
	
	private ModelMapper modelMapper;
	
	public PersonDTO create(PersonDTO personDTO) {
		try {
			Person person = modelMapper.map(personDTO, Person.class);
			personDTO = modelMapper.map(personRepository.save(person), PersonDTO.class);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationCustomException("Check the entered fields and the informed CPF");
		}
		return personDTO;
	}
	
	public PersonDTO findById(Long id) {
		Optional<Person> objPerson = personRepository.findById(id);
		return modelMapper.map(objPerson.orElseThrow(() -> new ObjectNotFoundException("Person not found!")), PersonDTO.class);
	}
	
	public List<PersonDTO> findAll(){
		List<Person> people = personRepository.findAll();
		return people.stream().map(person -> modelMapper.map(person, PersonDTO.class)).collect(Collectors.toList());
	}
	
	public PersonDTO update(Long id, PersonDTO personDTOUpdated) {
		findById(id);
		Person personToUpdate = modelMapper.map(personDTOUpdated, Person.class);
		personToUpdate.setId(id);
		Person personUpdated = personRepository.save(personToUpdate);
		return modelMapper.map(personUpdated, PersonDTO.class);
	}
	
	public void delete(Long id) {
		findById(id);
		personRepository.deleteById(id);
	}
	

}
