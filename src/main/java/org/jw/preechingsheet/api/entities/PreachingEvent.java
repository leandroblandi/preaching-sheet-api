package org.jw.preechingsheet.api.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "preachings")
public class PreachingEvent {
	@Id
	@JsonProperty("id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("date")
	private LocalDate date;
	
	@JsonProperty("time")
	private LocalTime time;
	
	@NotBlank
	@JsonProperty("place")
	private String appointmentPlace;
	
	@JsonProperty("conductor")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	private Person assignee;
	
	@JsonProperty("group")
	private String preachingGroup;
	
	@JsonProperty("territories")
	private String territories;
	
	@CreationTimestamp
	@JsonProperty("created_at")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@JsonProperty("update_timestamp")
	private LocalDateTime updatedAt;
	
	@JsonProperty("is_special_event")
	@Builder.Default
	private boolean specialEvent = false;
}
