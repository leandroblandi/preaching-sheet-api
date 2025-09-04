package org.jw.preechingsheet.api.exceptions.impl;

import java.io.Serial;

import org.jw.preechingsheet.api.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ExcelGenerationException extends BaseException {
	@Serial
	private static final long serialVersionUID = -1857085083221237939L;

	public ExcelGenerationException(String name) {
		super("Hubo un problema al generar la planilla `%s`".formatted(name), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
