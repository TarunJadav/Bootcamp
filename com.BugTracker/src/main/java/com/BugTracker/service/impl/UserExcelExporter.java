package com.BugTracker.service.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.BugTracker.entity.Report;

public class UserExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Report> listReport;

	public UserExcelExporter(List<Report> listReport) {
		this.listReport = listReport;
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Reports");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "Project Name", style);
		createCell(row, 1, "Techonology", style);
		createCell(row, 2, "Status", style);
		createCell(row, 3, "Start Date", style);
		createCell(row, 4, "End Date", style);
		createCell(row, 5, "IsDeleted", style);
		createCell(row, 6, "Project Id", style);
		createCell(row, 7, "Total Bugs", style);
	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (Report report : listReport) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, report.getProjectname(), style);
			createCell(row, columnCount++, report.getTechonology(), style);
			createCell(row, columnCount++, report.getStatus(), style);
			createCell(row, columnCount++, report.getStartdate(), style);
			createCell(row, columnCount++, report.getEnd_date(), style);
			createCell(row, columnCount++, report.isIsdeleted(), style);
			createCell(row, columnCount++, report.getPid().getId().toString(), style);
			createCell(row, columnCount++, report.getTotalbugs().toString(), style);
		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}
}