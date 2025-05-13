package org.jw.preechingsheet.api.services.impl;

import java.util.Optional;

import org.jw.preechingsheet.api.dtos.CreatePersonDto;
import org.jw.preechingsheet.api.entities.Person;
import org.jw.preechingsheet.api.repositories.IPersonRepository;
import org.jw.preechingsheet.api.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PersonServiceImpl implements IPersonService {
	private static final int DEFAULT_PAGE_SIZE = 5;
	
	@Autowired
	private IPersonRepository personRepository;
	
	@Override
	public Optional<Person> find(String uuid) {
		return personRepository.findById(uuid);
	}

	@Override
	public Page<Person> findAll(int page) {
		Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE);
		return personRepository.findAll(pageable);
	}

	@Override
	public Person create(CreatePersonDto request) {
		Person person = Person.builder()
				.fullName(request.getFullName())
				.role(request.getRole())
				.build();
		return personRepository.save(person);
	}

	@Override
	@Transactional
	public void disable(String uuid) {	
		Optional<Person> personOptional = find(uuid);
		
		personOptional.ifPresent(person -> {
			person.setActive(false);
			personRepository.save(person);
		});
	}
}
