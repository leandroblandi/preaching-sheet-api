package org.jw.preechingsheet.api.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jw.preechingsheet.api.dtos.CreateWeeklyPreachingDto;
import org.jw.preechingsheet.api.entities.PreachingEvent;
import org.jw.preechingsheet.api.entities.WeeklyPreaching;
import org.jw.preechingsheet.api.exceptions.impl.NotValidParamException;
import org.jw.preechingsheet.api.models.FileResponse;
import org.jw.preechingsheet.api.repositories.IWeeklyPreachingRepository;
import org.jw.preechingsheet.api.services.IExcelPreachingService;
import org.jw.preechingsheet.api.services.IPreachingService;
import org.jw.preechingsheet.api.services.IWeeklyPreachingService;
import org.jw.preechingsheet.api.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WeeklyPreachingService implements IWeeklyPreachingService {

	@Autowired
	private IPreachingService preachingService;
	
	@Autowired
	private IExcelPreachingService excelPreachingService;
	
	@Autowired
	private IWeeklyPreachingRepository weeklyPreachingRepository;
	
	@Override
	public Optional<WeeklyPreaching> find(Long id) {
		return weeklyPreachingRepository.findById(id);
	}

	@Override
	public Page<WeeklyPreaching> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return weeklyPreachingRepository.findAll(pageable);
	}
	
	public FileResponse export(Long id) {
		
		Optional<WeeklyPreaching> preachingOptional = find(id);
		
		if (preachingOptional.isEmpty()) {
			throw new NotValidParamException("person_id");
		}
		
		return excelPreachingService.create(preachingOptional.get());
	}

	@Override
	public WeeklyPreaching create(CreateWeeklyPreachingDto request) {
		List<PreachingEvent> events = new ArrayList<>();
		
		request.getPreachingEvents().forEach(event -> {
			events.add(preachingService.create(event));
		});
		
		LocalDate firstDate = events.getFirst().getDate();
		LocalDate lastDate = events.getLast().getDate();
		
		WeeklyPreaching weeklyPreaching = WeeklyPreaching
				.builder()
				.preachingEvents(events)
				.startDate(firstDate)
				.endDate(lastDate)
				.monthName(DateUtils.getMonthDescription(firstDate))
				.build();
		
		return weeklyPreachingRepository.save(weeklyPreaching);
	}

	@Override
	public void disable(Long id) {
		// TODO Auto-generated method stub
	}
}
