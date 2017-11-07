package com.example.controller;

import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.service.DownService;

@Controller
public class DownLoadExcelController {
	
	@Autowired
	private DownService downService;
	
	@Value("${web.download-path}")
	private String filePath;
	
	@RequestMapping(value="/excel",method=RequestMethod.GET)
	public void downLoad(HttpServletResponse rep) throws Exception{
		downService.down(filePath);
		String fileName = "表格.xlsx";
		String file = new String(fileName.getBytes("gb2312"),"ISO8859-1");
		rep.setContentType("application/vnd.ms-excel;charset=utf-8");
		rep.setCharacterEncoding("utf-8");
		rep.setHeader("Content-Disposition", "attachment;filename=" + file);
		byte[] buff = new byte[1024];
		FileInputStream fis = new FileInputStream(filePath);
		ServletOutputStream ops = rep.getOutputStream();
		while(fis.read(buff)!=-1){
			ops.write(buff);
		}
		ops.close();
		fis.close();
	}
}
