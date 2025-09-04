package org.jw.preechingsheet.api.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public interface IPictureGeneratorService {
	public Resource generateImageFromExcel(FileSystemResource excel, String range);
}
