package org.jw.preechingsheet.api.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateWeeklyPreachingDto {
	@NotEmpty
	@JsonProperty("preaching_events")
	private List<CreatePreachingDto> preachingEvents;
}
