package org.jw.preechingsheet.api.controllers;

import org.jw.preechingsheet.api.models.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusController {
	
	@GetMapping
	public ResponseEntity<ApiResponse<Void>> status() {
		ApiResponse<Void> response = ApiResponse.info("Working!");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
