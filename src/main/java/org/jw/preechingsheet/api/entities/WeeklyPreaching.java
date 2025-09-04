package org.jw.preechingsheet.api.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "weekly_preachings")
public class WeeklyPreaching {
	@Id
	@JsonProperty("id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("preaching_events")
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	private List<PreachingEvent> preachingEvents;
	
	@NotNull
	@JsonProperty("start")
	private LocalDate startDate;
	
	@NotNull
	@JsonProperty("end_date")
	private LocalDate endDate;
	
	@NotBlank
	@JsonProperty("month_name")
	private String monthName;
	
	@CreationTimestamp
	@JsonProperty("created_at")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@JsonProperty("update_timestamp")
	private LocalDateTime updatedAt;
}
