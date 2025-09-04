package org.jw.preechingsheet.api.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jw.preechingsheet.api.dtos.CreatePersonDto;
import org.jw.preechingsheet.api.entities.Person;
import org.jw.preechingsheet.api.enums.MinistryRoleEnum;
import org.springframework.data.domain.Page;

public interface IPersonService {
	public Optional<Person> find(Long id);
	
	public Page<Person> findAll(int page, int size, String sort, String direction);
	
	public List<Person> findAll(String sort);
	
	public Person create(CreatePersonDto request);
	
	public void incrementPreachingCount(Long personId);
	
	public Long countActive();
	
	public void disable(Long id);
	
	public Map<MinistryRoleEnum, Long> countActiveByRole();

}
