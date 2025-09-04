package org.jw.preechingsheet.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jw.preechingsheet.api.annotations.RateLimited;
import org.jw.preechingsheet.api.dtos.CreatePersonDto;
import org.jw.preechingsheet.api.entities.Person;
import org.jw.preechingsheet.api.enums.MinistryRoleEnum;
import org.jw.preechingsheet.api.models.ApiResponse;
import org.jw.preechingsheet.api.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<ApiResponse<Page<Person>>> findAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "fullName") String sort,
			@RequestParam(defaultValue = "asc") String direction) {
		
		ApiResponse<Page<Person>> response = ApiResponse.ok(service.findAll(page, size, sort, direction));
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<List<Person>>> findAll(
			@RequestParam(defaultValue = "fullName") String sort) {
		ApiResponse<List<Person>> response = ApiResponse.ok(service.findAll(sort));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@GetMapping("/count-by-roles")
	public ResponseEntity<ApiResponse<Map<MinistryRoleEnum, Long>>> countActiveByRole() {
		ApiResponse<Map<MinistryRoleEnum, Long>> response = ApiResponse.ok(service.countActiveByRole());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/count-active")
	public ResponseEntity<ApiResponse<Map<String, Long>>> countActive() {
	    Long count = service.countActive();
	    Map<String, Long> response = new HashMap<>();
	    response.put("active_count", count);
	    return ResponseEntity.ok(ApiResponse.ok(response));
	}

	@PostMapping
	@RateLimited
	public ResponseEntity<ApiResponse<Person>> create(@RequestBody CreatePersonDto dto) {
		ApiResponse<Person> response = ApiResponse.ok(service.create(dto));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@DeleteMapping("/{id}")
	@RateLimited
	public ResponseEntity<ApiResponse<Void>> disable(@PathVariable Long id) {
		service.disable(id);
		ApiResponse<Void> response = ApiResponse.info("Persona deshabilitada exitosamente");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
