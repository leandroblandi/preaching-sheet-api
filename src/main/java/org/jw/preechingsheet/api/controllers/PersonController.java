package org.jw.preechingsheet.api.controllers;

import org.jw.preechingsheet.api.dtos.CreatePersonDto;
import org.jw.preechingsheet.api.entities.Person;
import org.jw.preechingsheet.api.models.ApiResponse;
import org.jw.preechingsheet.api.services.IPersonService;
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

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {
	
	@Autowired
	private IPersonService service;
	
	@GetMapping
	public ResponseEntity<ApiResponse<Page<Person>>> findAll(@RequestParam int page) {
		ApiResponse<Page<Person>> response = ApiResponse.ok(service.findAll(page));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Person>> create(@RequestBody CreatePersonDto dto) {
		ApiResponse<Person> response = ApiResponse.ok(service.create(dto));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
