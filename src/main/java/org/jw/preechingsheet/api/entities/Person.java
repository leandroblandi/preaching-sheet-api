package org.jw.preechingsheet.api.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jw.preechingsheet.api.enums.MinistryRoleEnum;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "persons")
public class Person {
	
	@Id
	@JsonProperty("uuid")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;
	
	@NotBlank
	private String fullName;
	
	@Builder.Default
	private boolean active = true;
	
	@Builder.Default
	@NotNull
	@Enumerated(EnumType.STRING)
	private MinistryRoleEnum role = MinistryRoleEnum.PUBLISHER;
	
	@CreationTimestamp
	@JsonProperty("created_at")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@JsonProperty("update_timestamp")
	private LocalDateTime updatedAt;
}
