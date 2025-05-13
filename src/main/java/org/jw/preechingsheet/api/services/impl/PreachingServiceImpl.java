package org.jw.preechingsheet.api.services.impl;

import java.util.Optional;

import org.jw.preechingsheet.api.dtos.CreatePreachingDto;
import org.jw.preechingsheet.api.entities.Person;
import org.jw.preechingsheet.api.entities.PreachingEvent;
import org.jw.preechingsheet.api.repositories.IPreachingEventRepository;
import org.jw.preechingsheet.api.services.IPersonService;
import org.jw.preechingsheet.api.services.IPreachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PreachingServiceImpl implements IPreachingService {
	private static final int DEFAULT_PAGE_SIZE = 5;

	@Autowired
	private IPreachingEventRepository repository;
	
	@Autowired
	private IPersonService personService;
	
	@Override
	public Optional<PreachingEvent> find(String uuid) {
		return repository.findById(uuid);
	}

	@Override
	public Page<PreachingEvent> findAll(int page) {
		Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE);
		return repository.findAll(pageable);
	}

	@Override
	public PreachingEvent create(CreatePreachingDto request) {
		
		Optional<Person> optionalPerson = personService.find(request.getConductorUuid());
		
		if (optionalPerson.isEmpty()) {
			throw new RuntimeException("El conductor con UUID " + request.getConductorUuid() + " no existe.");
		}

		PreachingEvent preaching = PreachingEvent.builder()
			.date(request.getDate())
			.time(request.getTime())
			.appointmentPlace(request.getAppointmentPlace())
			.assignee(optionalPerson.get())
			.preachingGroup(request.getGroup())
			.territories(request.getTerritories())
			.build();

		return repository.save(preaching);
	}

	@Override
	public void disable(String uuid) {
		// TODO Auto-generated method stub

	}

}
