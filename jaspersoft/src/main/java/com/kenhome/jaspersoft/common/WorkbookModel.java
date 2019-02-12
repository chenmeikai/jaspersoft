package com.kenhome.jaspersoft.common;

import org.apache.poi.ss.usermodel.CellType;

import java.util.List;


/**
* Filename : HSSFWorkbookModel.java
* Author : fzl
* Creation time : 上午10:09:05 - 2018年8月30日
* Description : 生成Excel时所用的model
*/

public class WorkbookModel {
	
	public WorkbookModel(String sheetName, List<String> titleNames, List<CellType> cellTypes,
                         List<List<String>> datas) {
		super();
		this.sheetName = sheetName;
		this.titleNames = titleNames;
		this.cellTypes = cellTypes;
		this.datas = datas;
	}

	/**页名*/
	private String sheetName;
	
	/**标题*/
	private List<String> titleNames;
	
	/**各列数据的数据类型*/
	private List<CellType> cellTypes;
	
	/**数据*/
	private List<List<String>> datas;

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<String> getTitleNames() {
		return titleNames;
	}

	public void setTitleNames(List<String> titleNames) {
		this.titleNames = titleNames;
	}

	public List<CellType> getCellTypes() {
		return cellTypes;
	}

	public void setCellTypes(List<CellType> cellTypes) {
		this.cellTypes = cellTypes;
	}

	public List<List<String>> getDatas() {
		return datas;
	}

	public void setDatas(List<List<String>> datas) {
		this.datas = datas;
	}
}
