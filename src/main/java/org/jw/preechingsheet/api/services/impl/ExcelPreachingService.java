package org.jw.preechingsheet.api.services.impl;

import java.util.List;

import org.jw.preechingsheet.api.entities.WeeklyPreaching;
import org.jw.preechingsheet.api.services.IExcelPreachingService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ExcelPreachingService implements IExcelPreachingService {

	@Override
	public Resource create(WeeklyPreaching preaching) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<String> createHeaders() {
		return List.of("Día", "Hora", "Punto de Encuentro", "Conductor", "Grupo", "Territorio");
	}
}
