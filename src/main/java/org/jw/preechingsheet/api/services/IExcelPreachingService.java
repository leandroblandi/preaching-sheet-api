package org.jw.preechingsheet.api.services;

import org.jw.preechingsheet.api.entities.WeeklyPreaching;
import org.jw.preechingsheet.api.models.FileResponse;

public interface IExcelPreachingService {
	public FileResponse create(WeeklyPreaching preaching);
}
