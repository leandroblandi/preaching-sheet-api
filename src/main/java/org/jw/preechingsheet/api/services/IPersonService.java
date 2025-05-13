package org.jw.preechingsheet.api.services;

import java.util.Optional;

import org.jw.preechingsheet.api.dtos.CreatePersonDto;
import org.jw.preechingsheet.api.entities.Person;
import org.springframework.data.domain.Page;

public interface IPersonService {
	public Optional<Person> find(String uuid);
	
	public Page<Person> findAll(int page);
	
	public Person create(CreatePersonDto request);
	
	public void disable(String uuid);
}
