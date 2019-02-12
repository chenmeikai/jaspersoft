package com.kenhome.jaspersoft.utils;

import com.kenhome.jaspersoft.common.WorkbookModel;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
* Filename : ExcelUtil.java
* Author : fzl
* Creation time : 下午5:58:18 - 2018年8月29日
* Description : excel工具类
*/
public class ExcelUtil {
	
	/**
	 * 創造XSSFWorkbook（單頁）
	 * @param workbookModel
	 * @return
	 */
	public static XSSFWorkbook createWorkbook(WorkbookModel workbookModel) {
		List<WorkbookModel> workbookModels = new ArrayList<>(1);
		workbookModels.add(workbookModel);
		return ExcelUtil.createWorkbook(workbookModels);
	}

	/**
	 * 創造XSSFWorkbook（多頁）
	 * @param workbookModels
	 * @return
	 */
	public static XSSFWorkbook createWorkbook(List<WorkbookModel> workbookModels) {
		XSSFWorkbook xssfWorkbook = null;
		if (workbookModels != null && workbookModels.size() > 0) {
			xssfWorkbook = new XSSFWorkbook();
			for (int i = 0; i < workbookModels.size(); i++) {
				WorkbookModel workbookModel = workbookModels.get(i);
				/*对传入的数据进行判断，如果格式不正确则报错*/
				String sheetName = workbookModel.getSheetName() == null ? "sheet" + (i + 1) : workbookModel.getSheetName().trim();
				List<String> titleNames = workbookModel.getTitleNames();
				List<CellType> cellTypes = workbookModel.getCellTypes();
				List<List<String>> datas = workbookModel.getDatas();
				
				/*创建sheet,设置sheetName*/
				XSSFSheet sheet = xssfWorkbook.createSheet();
				xssfWorkbook.setSheetName(i, sheetName);
				/*创建表头*/
				if (titleNames != null && titleNames.size() > 0) {
					XSSFRow headRow = sheet.createRow(0);
					for (int j = 0; j < titleNames.size(); j++) {
						/*写入数据*/
						String titleName = titleNames.get(j);
						XSSFCell cell = headRow.createCell(j, CellType.STRING);
						cell.setCellValue(titleName);
						/*设置表头样式*/
						cell.setCellStyle(getDefualtTitleCellStyle(xssfWorkbook));
						/*自動調節colunm寬度*/
//						sheet.autoSizeColumn(j);只能适应英文和数字，中文无效
						sheet.setColumnWidth(j, titleName.getBytes().length * 256 + 50);
					}
				}
				/*写入数据*/
				if (datas != null && datas.size() > 0) {
					for (int k = 1; k < datas.size(); k++) {
						XSSFRow xssfRow = sheet.createRow(k);
						List<String> data = datas.get(k - 1);
						for (int l = 0; l < data.size(); l++) {
							XSSFCell xssfCell = xssfRow.createCell(l);
							/*是否有指定cellType，有则使用，无则默认为STRING*/
							if (cellTypes != null && cellTypes.size() >= l) {
								xssfCell.setCellType(cellTypes.get(l));
							} else {
								xssfCell.setCellType(CellType.STRING);
							}
							xssfCell.setCellValue(data.get(l));
						}
					}
				}
			}
		}
		return xssfWorkbook;
	}
	
	/**
	 * 通过HttpServletResponse以字节流的形式生成excel并给予下载
	 * @param response 网络请求的返回体
	 * @param fileName excel文件名
	 * @param xssfWorkbook 需要输出的excel
	 */
	public static void downloadExcelByHttpServletResponse(HttpServletResponse response, String fileName, XSSFWorkbook xssfWorkbook) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			xssfWorkbook.write(os);
			/*设置文件名 */
			fileName = fileName + System.currentTimeMillis() + ".xlsx";
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			/*设置response参数，可以打开下载页面 */
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (final IOException e) {
				e.printStackTrace();
			} finally {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 獲取默認表頭樣式
	 * @param wb
	 * @return
	 */
	private static XSSFCellStyle getDefualtTitleCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle();
		XSSFFont titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short)10);
		titleFont.setBold(true);
		cellStyle.setFont(titleFont);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		return cellStyle;
	}

	public static void download(HttpServletResponse response, String fileName, WorkbookModel workbookModel) {
		downloadExcelByHttpServletResponse(response, fileName, createWorkbook(workbookModel));
	}

	public static void download(HttpServletResponse response, String fileName, List<WorkbookModel> workbookModelList) {
		downloadExcelByHttpServletResponse(response, fileName, createWorkbook(workbookModelList));
	}


}
