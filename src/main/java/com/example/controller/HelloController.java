package com.example.controller;

import com.example.dao.GirlRepository;
import com.example.domain.Girl;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@Autowired
	private GirlRepository girlRepository;

	@RequestMapping(value = { "/all" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public List<Girl> say(HttpServletRequest request) {
		String isUse = request.getParameter("isUse");
		String id = request.getParameter("id");
		return this.girlRepository.findAll();
	}
	
	@RequestMapping(value = { "/add" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public String add(HttpServletRequest request) {
		String result = "SUCCESS";
		try {
			String cupSize = request.getParameter("cupSize");
			String ageStr = request.getParameter("age");
			Integer age = Integer.valueOf(Integer.parseInt(ageStr));
			String id = request.getParameter("id");
			Girl girl;
			if (!StringUtils.isEmpty(id))
				girl = (Girl) this.girlRepository.findOne(Integer.valueOf(Integer.parseInt(id)));
			else {
				girl = new Girl();
			}
			girl.setAge(age);
			girl.setCupSize(cupSize);
			girl.setIsUse("1");
			this.girlRepository.save(girl);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result = "FALSE";
		}
		return result;
	}

	@RequestMapping(value = { "/delete" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public String delete(HttpServletRequest request) {
		String id = request.getParameter("id");
		String result = "SUCCESS";
		try {
			if (!StringUtils.isEmpty(id)) {
				Girl girl = (Girl) this.girlRepository.findOne(Integer.valueOf(Integer.parseInt(id)));
				if (girl != null) {
					girl.setIsUse("0");
					this.girlRepository.save(girl);
					return result;
				}
			}
		} catch (Exception e) {
			result = "FALSE";
			e.printStackTrace();
		}
		return result;
	}
}