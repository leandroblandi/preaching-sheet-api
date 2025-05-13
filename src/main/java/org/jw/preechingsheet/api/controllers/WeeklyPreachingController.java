package org.jw.preechingsheet.api.controllers;

import org.jw.preechingsheet.api.dtos.CreateWeeklyPreachingDto;
import org.jw.preechingsheet.api.entities.WeeklyPreaching;
import org.jw.preechingsheet.api.models.ApiResponse;
import org.jw.preechingsheet.api.services.IWeeklyPreachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/weekly-preachings")
public class WeeklyPreachingController {

	@Autowired
	private IWeeklyPreachingService weeklyPreachingService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<Page<WeeklyPreaching>>> findAll(@RequestParam int page) {
		ApiResponse<Page<WeeklyPreaching>> response = ApiResponse.ok(weeklyPreachingService.findAll(page));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<WeeklyPreaching>> create(@Valid @RequestBody CreateWeeklyPreachingDto dto) {
		ApiResponse<WeeklyPreaching> response = ApiResponse.ok(weeklyPreachingService.create(dto));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
}
