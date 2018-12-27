package com.kenhome.jaspersoft.utils;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class GenerateReportUtil {
	
	public static void exportPdf(String sourceFileName, Map<String, Object> params, Connection conntction, String destFileName) throws JRException {
		JasperPrint jasperPrint = null;
		if(conntction == null){
			jasperPrint = JasperFillManager.fillReport(sourceFileName, params, new JREmptyDataSource());
		}else{
			jasperPrint = JasperFillManager.fillReport(sourceFileName, params, conntction);
		}
		JasperExportManager.exportReportToPdfFile(jasperPrint,  destFileName);
	}
	
	public static void exportPdf(List<String> sourceFileNames, Map<String, Object> params,Connection conntction, String destFileName) throws JRException {
		JasperPrint jasperPrintSummary = null;
		for(final String sourceFileName:sourceFileNames){
			JasperPrint jasperPrint = null;
			if(conntction == null){
				jasperPrint = JasperFillManager.fillReport(sourceFileName, params, new JREmptyDataSource());
			}else{
				jasperPrint = JasperFillManager.fillReport(sourceFileName, params, conntction);
			}
			if(jasperPrintSummary==null){
				jasperPrintSummary = jasperPrint;
				continue;
			}
			for(final JRPrintPage page : jasperPrint.getPages()){
				jasperPrintSummary.addPage(page);
			}
		}
		JasperExportManager.exportReportToPdfFile(jasperPrintSummary,  destFileName);
	}
	
	public static void exportMgrPdf(Map<String, Map<String, Object>> mgrParams,Connection conntction, String destFileName) throws JRException {
		JasperPrint jasperPrintSummary = null;
		for(final String jasperPath : mgrParams.keySet()){
			JasperPrint jasperPrint = null;
			if(conntction == null){
				jasperPrint = JasperFillManager.fillReport(jasperPath, mgrParams.get(jasperPath), new JREmptyDataSource());
			}else{
				jasperPrint = JasperFillManager.fillReport(jasperPath, mgrParams.get(jasperPath), conntction);
			}
			if(jasperPrintSummary==null){
				jasperPrintSummary = jasperPrint;
				continue;
			}
			for(final JRPrintPage page : jasperPrint.getPages()){
				jasperPrintSummary.addPage(page);
			}
		}
		JasperExportManager.exportReportToPdfFile(jasperPrintSummary,  destFileName);
	}

}
