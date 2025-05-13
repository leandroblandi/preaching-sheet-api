package org.jw.preechingsheet.api.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * Clase utilizada para estandarizar las respuestas HTTP de la API Externa
 * 
 * @param <T> El tipo de dato a envolver en la respuesta HTTP
 * @author lblandi
 */
@Data
@Builder
public class ApiResponse <T> {
	
	@Builder.Default
	@JsonProperty("success")
	private boolean success = true;
	
	@Builder.Default
	@JsonProperty("data")
	private T data = null;
	
	@Builder.Default
	@JsonProperty("message")
	private String message = "";

	@Builder.Default
	@JsonProperty("timestamp")
	private LocalDateTime timestamp = LocalDateTime.now();
	
	
	/**
	 * 
	 * Genera un objeto ApiResponse<T> con data incluida, indicando que es una
	 * respuesta exitosa
	 * 
	 * @param <T>  El tipo de dato a incluir en la respuesta
	 * @param data El/Los dato/s
	 * @return Un objeto ApiResponse con la data pasada por parámetro
	 */
	public static <T> ApiResponse<T> ok(T data) {
		return ApiResponse.<T>builder()
				.data(data)
				.build();
	}
	
	/**
	 * Genera un objeto ApiResponse<Void> con mensaje, util para indicar que se
	 * trata de una respuesta de informacion
	 * 
	 * @param message El mensaje
	 * @return Un objeto ApiResponse con el mensaje pasado por parámetro
	 */
	public static ApiResponse<Void> info(String message) {
		return ApiResponse.<Void>builder()
				.message(message)
				.build();
	}
}

