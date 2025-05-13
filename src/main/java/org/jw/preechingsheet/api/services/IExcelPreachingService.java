package org.jw.preechingsheet.api.services;

import org.jw.preechingsheet.api.entities.WeeklyPreaching;
import org.springframework.core.io.Resource;

public interface IExcelPreachingService {
	public Resource create(WeeklyPreaching preaching);
}
