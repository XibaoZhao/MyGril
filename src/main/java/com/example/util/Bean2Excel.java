package com.example.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Bean2Excel {

	/**
	 * @param arrayList 需要导出的封装对象
	 * @param path 文件路径
	 * @param list 表第一行信息
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static void out(ArrayList<?> arrayList, String path, ArrayList<String> list)
			throws IllegalArgumentException, IllegalAccessException, IOException {
		// 声明一个工作簿
		XSSFWorkbook wb = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = wb.createSheet("sheet1");
		// 表格头行
		XSSFRow row = sheet.createRow(0);
		for (int i = 0; i < list.size(); i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(list.get(i));
		}
		// 表数据填充
		for (int j = 0; j < arrayList.size(); j++) {
			// 根据对象，获取对象的Class对象
			Class<? extends Object> clazz = arrayList.get(j).getClass();
			Field[] fields = clazz.getDeclaredFields();
			int i = 0;
			XSSFRow row1 = sheet.createRow(j + 1);
			for (Field field : fields) {
				// 私有属性不能被访问和修改，要进行权限的修改
				field.setAccessible(true);
				XSSFCell cell1 = row1.createCell(i);
				cell1.setCellValue(field.get(arrayList.get(j)).toString());
				i++;
			}
		}
		// write the output 2 a file
		FileOutputStream fileOut = new FileOutputStream(path);
		wb.write(fileOut);
		fileOut.close();
	}
}
