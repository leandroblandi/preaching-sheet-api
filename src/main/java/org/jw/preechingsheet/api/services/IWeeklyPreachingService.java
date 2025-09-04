package org.jw.preechingsheet.api.services;

import java.util.Optional;

import org.jw.preechingsheet.api.dtos.CreateWeeklyPreachingDto;
import org.jw.preechingsheet.api.entities.WeeklyPreaching;
import org.jw.preechingsheet.api.models.FileResponse;
import org.springframework.data.domain.Page;

public interface IWeeklyPreachingService {
	public Optional<WeeklyPreaching> find(Long id);
	
	public Page<WeeklyPreaching> findAll(int page, int size);
	
	public FileResponse export(Long id);
	
	public WeeklyPreaching create(CreateWeeklyPreachingDto request);
	
	public void disable(Long id);
}
