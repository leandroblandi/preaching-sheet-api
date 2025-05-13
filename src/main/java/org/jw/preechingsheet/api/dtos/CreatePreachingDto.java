package org.jw.preechingsheet.api.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.validator.constraints.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePreachingDto {
	@NotNull
	@JsonProperty("date")
	private LocalDate date;
	
	@NotNull
	@JsonProperty("time")
	private LocalTime time;
	
	@NotBlank
	@JsonProperty("appointment_place")
	private String appointmentPlace;
	
	@NotBlank
	@UUID
	@JsonProperty("conductor_uuid")
	private String conductorUuid;
	
	@NotBlank
	@JsonProperty("group")
	private String group;
	
	@NotBlank
	@JsonProperty("territories")
	private String territories;
}
