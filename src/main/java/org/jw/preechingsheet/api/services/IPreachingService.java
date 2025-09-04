package org.jw.preechingsheet.api.services;

import java.util.Optional;

import org.jw.preechingsheet.api.dtos.CreatePreachingDto;
import org.jw.preechingsheet.api.entities.PreachingEvent;
import org.springframework.data.domain.Page;

public interface IPreachingService {
	public Optional<PreachingEvent> find(Long id);
	
	public Page<PreachingEvent> findAll(int page);
	
	public PreachingEvent create(CreatePreachingDto request);
	
	public void disable(Long id);
}
