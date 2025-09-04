package org.jw.preechingsheet.api.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePreachingDto {
	@JsonProperty("date")
	private LocalDate date;
	
	@JsonProperty("time")
	private LocalTime time;
	
	@NotBlank
	@JsonProperty("appointment_place")
	private String appointmentPlace;
	
	@JsonProperty("conductor_id")
	private Long conductorId;
	
	@JsonProperty("group")
	private String group;
	
	@JsonProperty("territories")
	private String territories;

	@JsonProperty("special_event")
	private boolean specialEvent;	
}
