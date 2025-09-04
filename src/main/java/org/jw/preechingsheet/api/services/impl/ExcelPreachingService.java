package org.jw.preechingsheet.api.services.impl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jw.preechingsheet.api.entities.PreachingEvent;
import org.jw.preechingsheet.api.entities.WeeklyPreaching;
import org.jw.preechingsheet.api.exceptions.impl.ExcelGenerationException;
import org.jw.preechingsheet.api.models.FileResponse;
import org.jw.preechingsheet.api.services.IExcelPreachingService;
import org.jw.preechingsheet.api.utils.DateUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ExcelPreachingService implements IExcelPreachingService {
	
	// Title names
	private static final String SHEET_HEADER_NAME = "Predicación de casa en casa";
	private static final String SHEET_HEADER_WEEK_DESCRIPCION = "Semana del %d al %d de %s";
	private static final List<String> HEADERS = List.of("Día", "Hora", "Punto de Encuentro", "Conductor", "Grupo", "Territorio");
	
	/// Colors
	private static final Color HEADER_COLUMNS_COLOR = new Color(74, 135, 233);
	private static final Color HEADER_TITLE_COLOR = new Color(206, 226, 242);
	private static final Color EVEN_CELL_COLOR = new Color(159, 197, 232);
	private static final Color ODD_CELL_COLOR = HEADER_TITLE_COLOR;
	private static final Color SPECIAL_EVENT_COLOR = new Color(63, 110, 186);

	
	// Font configuration
	private static final String FONT_NAME = "Rubik";
	private static final int CONTENT_FONT_SIZE_PT = 11;
	private static final int TITLE_FONT_SIZE_PT = 20;
	private static final int WEEK_FONT_SIZE_PT = 16;
	
	// Indexes, widths, heights
	private static final int CONTENT_ROW_INDEX_START = 4;
	private static final int PLACE_CELL_WIDTH = 12250;
	private static final int DAY_TERRITORY_CELL_WIDTH = 5000;
	private static final float ROW_HEIGHT_PT = 30f;
	
	// Cell combinations range addresses
	private static final CellRangeAddress HEADER_TITLE_RANGE_ADDRESS = new CellRangeAddress(1, 1, 1, 6);
	private static final CellRangeAddress HEADER_DESCRIPCION_RANGE_ADDRESS = new CellRangeAddress(2, 2, 1, 6);

	private Workbook workbook;
	private Sheet sheet;
	
	@Override
	public FileResponse create(WeeklyPreaching preaching) {
		String sheetName = "Sheet1";
		
		this.initWorkBook(sheetName);
		this.fillData(preaching);
		this.autosizeSheet();
		
		return FileResponse.builder().file(this.generate()).fileName(sheetName).build();
	}
	
	/**
	 * Initializes the Woorkbook, and the sheet with the name specified
	 * 
	 * @param sheetName The sheet name
	 */
	private void initWorkBook(String sheetName) {
		this.workbook = new XSSFWorkbook();
		this.sheet = workbook.createSheet(sheetName);
	}
	
	/**
	 * 
	 * Fills the sheet with WeeklyPreaching data
	 * 
	 * @param preaching The Preaching object
	 */
	private void fillData(WeeklyPreaching preaching) {
		String weekDescription = buildSheetName(preaching);
		
		// 1nd Header
		Row titleRow = sheet.createRow(1);
		titleRow.setHeightInPoints(40f);
		
		Cell titleCell = titleRow.createCell(1);
		titleCell.setCellValue(SHEET_HEADER_NAME.toUpperCase());
		titleCell.setCellStyle(createCellStyle(TITLE_FONT_SIZE_PT, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, HEADER_TITLE_COLOR));
		
		// 2nd Header
		Row weekRow = sheet.createRow(2);
		Cell weekCell = weekRow.createCell(1);
		weekCell.setCellValue(weekDescription.toUpperCase());
		weekCell.setCellStyle(createCellStyle(WEEK_FONT_SIZE_PT, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, HEADER_TITLE_COLOR));
		weekRow.setHeightInPoints(30f);
		
		// Header columns
		Row headerRow = sheet.createRow(3);
		headerRow.setHeightInPoints(30f);
		
		
		for (int i = 0; i < HEADERS.size(); i++) {
			Cell cell = headerRow.createCell(i + 1);
			cell.setCellValue(HEADERS.get(i).toUpperCase());
			cell.setCellStyle(createCellStyle(CONTENT_FONT_SIZE_PT, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, HEADER_COLUMNS_COLOR));
		}
		
		int rowIndex = CONTENT_ROW_INDEX_START;
		List<PreachingEvent> events = preaching.getPreachingEvents();
		
		for (int i = 0; i < events.size(); i++) {
			
			PreachingEvent event = events.get(i);
		    Row row = sheet.createRow(rowIndex++);
			Color color = EVEN_CELL_COLOR;
			boolean isSpecialEvent = event.isSpecialEvent();
			
			if (rowIndex % 2 == 0) {
				color = ODD_CELL_COLOR;
			}
			
			// Si es evento especial, usar color especial
			if (isSpecialEvent) {
				color = SPECIAL_EVENT_COLOR;
			}

		    // Day
		    Cell dayCell = row.createCell(1);
		    String dayName = event.getDate().getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, Locale.of("es"));
		    String formattedDay = StringUtils.capitalize(dayName) + " " + event.getDate().getDayOfMonth();
		    
		    dayCell.setCellValue(formattedDay);
		    dayCell.setCellStyle(createCellStyle(11, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, color));


		    if (i != 0 && event.getDate().equals(events.get(i - 1).getDate())) {
		    	dayCell.setCellValue("");
		    }
		    
		    // Hour
		    Cell timeCell = row.createCell(2);
		    if (isSpecialEvent) {
		    	timeCell.setCellValue("");
		    	timeCell.setCellStyle(createCellStyle(11, true, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, color));
		    } else {
		    	timeCell.setCellValue(event.getTime().toString());
		    	timeCell.setCellStyle(createCellStyle(11, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, color));
		    }
		    
		    // Place
		    Cell placeCell = row.createCell(3);
		    placeCell.setCellValue(event.getAppointmentPlace());
		    placeCell.setCellStyle(createCellStyle(11, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, color));

		    
		    // Asignee
		    Cell conductorCell = row.createCell(4);
		    if (isSpecialEvent) {
		    	conductorCell.setCellValue("");
		    	conductorCell.setCellStyle(createCellStyle(11, true, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, color));
		    } else {
		    	conductorCell.setCellValue(
		        	event.getAssignee() != null ? event.getAssignee().getFullName() : ""
		    	);
		    	conductorCell.setCellStyle(createCellStyle(11, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, color));
		    }


		    // Preaching group
		    Cell groupCell = row.createCell(5);
		    if (isSpecialEvent) {
		    	groupCell.setCellValue("");
		    	groupCell.setCellStyle(createCellStyle(11, true, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, color));
		    } else {
		    	groupCell.setCellValue(event.getPreachingGroup());
		    	groupCell.setCellStyle(createCellStyle(11, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, color));
		    }

		    
		    // Territory assigned
		    Cell territoryCell = row.createCell(6);
		    if (isSpecialEvent) {
		    	territoryCell.setCellValue("");
		    	territoryCell.setCellStyle(createCellStyle(11, true, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, color));
		    } else {
		    	territoryCell.setCellValue(event.getTerritories());
		    	territoryCell.setCellStyle(createCellStyle(11, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, color));
		    }
		    
		    row.setHeightInPoints(ROW_HEIGHT_PT);
		}

		// Headers cell merge
		sheet.addMergedRegion(HEADER_TITLE_RANGE_ADDRESS);
		sheet.addMergedRegion(HEADER_DESCRIPCION_RANGE_ADDRESS);
	}
	
	/**
	 * Autosizes the sheet by data length, and resizes the "Appointment place" column
	 */
	private void autosizeSheet() {
		for (int i = 0; i < HEADERS.size(); i++) {
            sheet.autoSizeColumn(i);
        }
		
		// Change size to place column
		this.sheet.setColumnWidth(1, DAY_TERRITORY_CELL_WIDTH);
		this.sheet.setColumnWidth(3, PLACE_CELL_WIDTH);
		this.sheet.setColumnWidth(6, DAY_TERRITORY_CELL_WIDTH);
	}
	
	/**
	 * Builds the sheet name by the preaching name
	 * 
	 * @param preaching The Preaching object
	 * @return The sheet name
	 */
	private String buildSheetName(WeeklyPreaching preaching) {
		return SHEET_HEADER_WEEK_DESCRIPCION.formatted(preaching.getStartDate().getDayOfMonth(), preaching.getEndDate().getDayOfMonth(), DateUtils.getMonthDescription(preaching.getEndDate()));
	}
	
	private Resource generate() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			workbook.write(output);
		} catch (IOException e) {
			throw new ExcelGenerationException(sheet.getSheetName());
		}
		return new InputStreamResource(new ByteArrayInputStream(output.toByteArray()));
	}
	
	private CellStyle createCellStyle(int fontSize, boolean bold, HorizontalAlignment alignmentX, VerticalAlignment alignmentY) {
		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(FONT_NAME);
        font.setFontHeightInPoints((short) fontSize);
		font.setBold(bold);
		style.setAlignment(alignmentX);
        style.setVerticalAlignment(alignmentY);
        style.setFont(font);
        return style;
	}
	
	private CellStyle createCellStyle(int fontSize, boolean bold, boolean italic, HorizontalAlignment alignmentX, VerticalAlignment alignmentY) {
		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(FONT_NAME);
        font.setFontHeightInPoints((short) fontSize);
		font.setBold(bold);
		font.setItalic(italic);
		style.setAlignment(alignmentX);
        style.setVerticalAlignment(alignmentY);
        style.setFont(font);
        return style;
	}
	
	private CellStyle createCellStyle(int fontSize, boolean bold, HorizontalAlignment alignmentX, VerticalAlignment alignmentY, Color color) {
		CellStyle style = createCellStyle(fontSize, bold, alignmentX, alignmentY);
		style.setFillForegroundColor(new XSSFColor(color, null));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}
	
	private CellStyle createCellStyle(int fontSize, boolean bold, boolean italic, HorizontalAlignment alignmentX, VerticalAlignment alignmentY, Color color) {
		CellStyle style = createCellStyle(fontSize, bold, italic, alignmentX, alignmentY);
		style.setFillForegroundColor(new XSSFColor(color, null));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}
}
