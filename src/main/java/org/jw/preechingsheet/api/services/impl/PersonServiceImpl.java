package org.jw.preechingsheet.api.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jw.preechingsheet.api.dtos.CreatePersonDto;
import org.jw.preechingsheet.api.entities.Person;
import org.jw.preechingsheet.api.enums.MinistryRoleEnum;
import org.jw.preechingsheet.api.repositories.IPersonRepository;
import org.jw.preechingsheet.api.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PersonServiceImpl implements IPersonService {
	
	@Autowired
	private IPersonRepository personRepository;
	
	@Override
	public Optional<Person> find(Long id) {
		return personRepository.findById(id);
	}

	@Override
	public Page<Person> findAll(int page, int size, String sort, String direction) {
		Sort sortObj = Sort.by(Sort.Direction.fromString(direction), sort);
		Pageable pageable = PageRequest.of(page, size, sortObj);
		return personRepository.findAll(pageable);
	}
	
	@Override
	public List<Person> findAll(String sort) {
		Sort sortObj = Sort.by(Sort.Direction.ASC, sort);
		return personRepository.findAll(sortObj);
	}

	@Override
	public Person create(CreatePersonDto request) {
		Person person = Person.builder()
				.fullName(request.getFullName())
				.role(request.getRole())
				.active(request.isActive())
				.build();
		return personRepository.save(person);
	}

	@Override
	@Transactional
	public void incrementPreachingCount(Long personId) {
		Optional<Person> personOptional = find(personId);
		
		personOptional.ifPresent(person -> {
			person.setPreachingCount(person.getPreachingCount() + 1);
			personRepository.save(person);
		});
	}
	
	@Override
	public Long countActive() {
	    return personRepository.countActive();
	}
	
	@Override
	public Map<MinistryRoleEnum, Long> countActiveByRole() {
	    List<Object[]> results = personRepository.countActiveByRole();
	    Map<MinistryRoleEnum, Long> map = new HashMap<>();
	    for (Object[] row : results) {
	        MinistryRoleEnum role = (MinistryRoleEnum) row[0];
	        Long count = (Long) row[1];
	        map.put(role, count);
	    }
	    return map;
	}

	@Override
	@Transactional
	public void disable(Long id) {	
		Optional<Person> personOptional = find(id);
		
		personOptional.ifPresent(person -> {
			person.setActive(false);
			personRepository.save(person);
		});
	}
}
