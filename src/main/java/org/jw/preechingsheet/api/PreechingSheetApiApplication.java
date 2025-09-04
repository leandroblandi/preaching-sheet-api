package org.jw.preechingsheet.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableAspectJAutoProxy
@SpringBootApplication
public class PreechingSheetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreechingSheetApiApplication.class, args);
	}

}
