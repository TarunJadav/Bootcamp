package com.BugTracker.service;

import java.util.Map;

public interface GenerateService {
	
    void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);
    
    void generateExcelFile(String templateName, Map<String, Object> data, String pdfFileName);
}
