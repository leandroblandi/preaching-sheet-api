package org.jw.preechingsheet.api.services;

import java.util.Optional;

import org.jw.preechingsheet.api.dtos.CreateWeeklyPreachingDto;
import org.jw.preechingsheet.api.entities.WeeklyPreaching;
import org.springframework.data.domain.Page;

public interface IWeeklyPreachingService {
	public Optional<WeeklyPreaching> find(String uuid);
	
	public Page<WeeklyPreaching> findAll(int page);
	
	public WeeklyPreaching create(CreateWeeklyPreachingDto request);
	
	public void disable(String uuid);
}
