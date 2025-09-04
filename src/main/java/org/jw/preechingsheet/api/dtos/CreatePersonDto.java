package org.jw.preechingsheet.api.dtos;

import org.jw.preechingsheet.api.enums.MinistryRoleEnum;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePersonDto {
	
	@NotBlank
	@JsonProperty("full_name")
	private String fullName;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@JsonProperty("role")
	private MinistryRoleEnum role;
	
	private boolean active;
}
