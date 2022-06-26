package com.BugTracker.service.impl;

import com.BugTracker.service.GenerateService;

import com.lowagie.text.DocumentException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

@Service
public class PdfGenerateServiceImpl implements GenerateService{
    private Logger logger = LoggerFactory.getLogger(PdfGenerateServiceImpl.class);

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${pdf.directory}")
    private String pdfDirectory;

    @Override
    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pdfDirectory + pdfFileName);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, true);
            renderer.finishPDF();
            
            System.out.println("done pdf");
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

	@Override
	public void generateExcelFile(String templateName, Map<String, Object> data, String pdfFileName) {
	      Context context = new Context();
	        context.setVariables(data);
	        String htmlContent = templateEngine.process(templateName, context);
	        
	        
	        try {
	        	
	        	 FileOutputStream fileOutputStream = new FileOutputStream(pdfDirectory + pdfFileName);
	        	  ITextRenderer renderer = new ITextRenderer();
	              renderer.setDocumentFromString(htmlContent);
	              renderer.layout();
	              
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		
	}
}