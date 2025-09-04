package org.jw.preechingsheet.api.services.impl;

import java.util.Optional;

import org.jw.preechingsheet.api.dtos.CreatePreachingDto;
import org.jw.preechingsheet.api.entities.Person;
import org.jw.preechingsheet.api.entities.PreachingEvent;
import org.jw.preechingsheet.api.exceptions.impl.NotValidParamException;
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
	public Optional<PreachingEvent> find(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<PreachingEvent> findAll(int page) {
		Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE);
		return repository.findAll(pageable);
	}

	@Override
	public PreachingEvent create(CreatePreachingDto request) {
		
		PreachingEvent.PreachingEventBuilder builder = PreachingEvent.builder()
			.date(request.getDate())
			.appointmentPlace(request.getAppointmentPlace())
			.specialEvent(request.isSpecialEvent());
		
		if (request.isSpecialEvent()) {
			// Para eventos especiales, no se requiere conductor, grupo, territorios ni hora
			builder.time(null)
				.assignee(null)
				.preachingGroup(null)
				.territories(null);
		} else {
			// Para eventos normales, validar que exista el conductor
			Optional<Person> optionalPerson = personService.find(request.getConductorId());
			
			if (optionalPerson.isEmpty()) {
				throw new NotValidParamException("person_id");
			}
			
			builder.time(request.getTime())
				.assignee(optionalPerson.get())
				.preachingGroup(request.getGroup())
				.territories(request.getTerritories());
			
			// Increment preaching count for the conductor
			personService.incrementPreachingCount(request.getConductorId());
		}

		PreachingEvent preaching = builder.build();
		return repository.save(preaching);
	}

	@Override
	public void disable(Long id) {
		// TODO Auto-generated method stub

	}

}
