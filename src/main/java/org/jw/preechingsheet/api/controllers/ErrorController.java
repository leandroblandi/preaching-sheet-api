package org.jw.preechingsheet.api.controllers;

import org.jw.preechingsheet.api.exceptions.impl.ExcelGenerationException;
import org.jw.preechingsheet.api.exceptions.impl.NotValidParamException;
import org.jw.preechingsheet.api.models.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

	@ExceptionHandler(NotValidParamException.class)
	public ResponseEntity<ApiResponse<Void>> handle(NotValidParamException e) {
		ApiResponse<Void> response = ApiResponse.error(e.getMessage());
		return ResponseEntity.status(e.getStatus()).body(response);
	}
	
	@ExceptionHandler(ExcelGenerationException.class)
	public ResponseEntity<ApiResponse<Void>> handle(ExcelGenerationException e) {
		ApiResponse<Void> response = ApiResponse.error(e.getMessage());
		return ResponseEntity.status(e.getStatus()).body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handle(Exception e) {
		ApiResponse<Void> response = ApiResponse.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
}
