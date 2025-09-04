package org.jw.preechingsheet.api.controllers;

import java.util.Optional;

import org.jw.preechingsheet.api.annotations.RateLimited;
import org.jw.preechingsheet.api.dtos.CreateWeeklyPreachingDto;
import org.jw.preechingsheet.api.entities.WeeklyPreaching;
import org.jw.preechingsheet.api.models.ApiResponse;
import org.jw.preechingsheet.api.models.FileResponse;
import org.jw.preechingsheet.api.services.IWeeklyPreachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/weekly_preachings")
public class WeeklyPreachingController {

	@Autowired
	private IWeeklyPreachingService weeklyPreachingService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<Page<WeeklyPreaching>>> findAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		ApiResponse<Page<WeeklyPreaching>> response = ApiResponse.ok(weeklyPreachingService.findAll(page, size));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Optional<WeeklyPreaching>>> find(@PathVariable long id) {
		ApiResponse<Optional<WeeklyPreaching>> response = ApiResponse.ok(weeklyPreachingService.find(id));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/{id}/xls")
	@RateLimited
	public ResponseEntity<Resource> export(@PathVariable Long id) {
		FileResponse response = weeklyPreachingService.export(id);
		Resource resource = response.getFile();
		String formattedFileName = "Hoja de salidas - %s".formatted(response.getFileName().trim());
		return ResponseEntity
				.status(HttpStatus.OK)
				.header("Content-Disposition", "attachment;filename=%s.xlsx".formatted(formattedFileName))
				.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(resource);
	}
	
	@PostMapping
	@RateLimited
	public ResponseEntity<ApiResponse<WeeklyPreaching>> create(@Valid @RequestBody CreateWeeklyPreachingDto dto) {
		ApiResponse<WeeklyPreaching> response = ApiResponse.ok(weeklyPreachingService.create(dto));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
}
