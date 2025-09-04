package org.jw.preechingsheet.api.models;

import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponse {
	
	@JsonProperty("file")
	private Resource file;
	
	@JsonProperty("file_name")
	private String fileName;
}
