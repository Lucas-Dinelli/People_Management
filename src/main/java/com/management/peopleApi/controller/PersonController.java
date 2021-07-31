package com.management.peopleApi.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.management.peopleApi.dto.PersonDTO;
import com.management.peopleApi.service.PersonService;

@RestController
@RequestMapping("/v1/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping
	public ResponseEntity<PersonDTO> create(@Valid @RequestBody PersonDTO personDTO) {
		personDTO = personService.create(personDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(personDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(personDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
		PersonDTO personDTO = personService.findById(id);
		return ResponseEntity.ok().body(personDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<PersonDTO>> findAll() {
		return ResponseEntity.ok().body(personService.findAll());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PersonDTO> update(@PathVariable Long id, @Valid @RequestBody PersonDTO personDTOUpdated){
		PersonDTO personDTO = personService.update(id, personDTOUpdated);
		return ResponseEntity.ok().body(personDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		personService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
