package org.jw.preechingsheet.api.controllers;

import org.jw.preechingsheet.api.dtos.CreatePreachingDto;
import org.jw.preechingsheet.api.entities.PreachingEvent;
import org.jw.preechingsheet.api.models.ApiResponse;
import org.jw.preechingsheet.api.services.IPreachingService;
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
@RequestMapping("/api/v1/preachings")
public class PreachingController {

	@Autowired
	private IPreachingService service;
	
	@GetMapping
	public ResponseEntity<ApiResponse<Page<PreachingEvent>>> findAll(@RequestParam int page) {
		ApiResponse<Page<PreachingEvent>> response = ApiResponse.ok(service.findAll(page));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<PreachingEvent>> create(@Valid @RequestBody CreatePreachingDto dto) {
		PreachingEvent created = service.create(dto);
		ApiResponse<PreachingEvent> response = ApiResponse.ok(created);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
