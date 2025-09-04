package org.jw.preechingsheet.api.exceptions.impl;

import java.io.Serial;

import org.jw.preechingsheet.api.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class NotValidParamException extends BaseException {
	@Serial
	private static final long serialVersionUID = 1025890851263783803L;

	public NotValidParamException(String param) {
		super("El parámetro `%s` contiene un valor no valido".formatted(param), HttpStatus.BAD_REQUEST);
	}
}
