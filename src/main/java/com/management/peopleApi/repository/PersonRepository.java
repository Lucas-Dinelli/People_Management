package com.management.peopleApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.peopleApi.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	
}
