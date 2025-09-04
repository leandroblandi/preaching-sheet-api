package org.jw.preechingsheet.api.exceptions;

import java.io.Serial;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	protected final String detail;
	protected final HttpStatus status;

	protected BaseException(String message, HttpStatus status) {
		super(message);
		this.detail = message;
		this.status = status;
	}
}
