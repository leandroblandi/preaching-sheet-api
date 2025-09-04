package org.jw.preechingsheet.api.services.impl;

import org.jw.preechingsheet.api.services.IPictureGeneratorService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class PictureGeneratorServiceImpl implements IPictureGeneratorService {

	@Override
	public Resource generateImageFromExcel(FileSystemResource excel, String range) {
		// TODO Auto-generated method stub
		return null;
	}

}
