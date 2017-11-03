package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Girl;
import com.example.domain.GridPageBean;
import com.example.service.GirlService;

@Controller
public class HelloController {

	@Autowired
	private GirlService girlService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public GridPageBean<Girl> findAll(HttpServletRequest request) {
		String cupSize = request.getParameter("cupSize");
		String pageNoStr = request.getParameter("pageNo");
		System.out.println(pageNoStr);
		Integer pageNo = null;
		try {
			pageNo = Integer.parseInt(pageNoStr);
			if(pageNo<1){
				pageNo =1;
			}
		} catch (NumberFormatException e) {
			pageNo=1;
			e.printStackTrace();
		}
		return girlService.findAll(cupSize,pageNo);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@ResponseBody
	public String add(HttpServletRequest request) {
		String result = "SUCCESS";
		try {
			String cupSize = request.getParameter("cupSize");
			String ageStr = request.getParameter("age");
			Integer age = Integer.valueOf(Integer.parseInt(ageStr));
			String id = request.getParameter("id");
			girlService.addGirl(id, age, cupSize);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result = "FALSE";
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public String delete(HttpServletRequest request) {
		String id = request.getParameter("id");
		String result = "SUCCESS";
		try {
			if (!StringUtils.isEmpty(id)) {
				girlService.deleteGirl(id);
			}
		} catch (Exception e) {
			result = "FALSE";
			e.printStackTrace();
		}
		return result;
	}
}