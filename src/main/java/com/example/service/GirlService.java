package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.dao.GirlRepository;
import com.example.domain.Girl;

@Service
public class GirlService {
	
	@Autowired
	private GirlRepository girlRepository;
	
	//查
	public List<Girl> findAll(){
		List<Girl> girlList = girlRepository.findAll();
		return girlList;
	}
	
	public Girl findOne(Integer id){
		Girl girl = girlRepository.findOne(id);
		return girl;
	}
	
	//增、改
	@Transactional
	public void addGirl(String id,Integer age,String cupSize){
		Girl girl = null;
		if (!StringUtils.isEmpty(id))
			girl = girlRepository.findOne(Integer.valueOf(Integer.parseInt(id)));
		else {
			girl = new Girl();
		}
		girl.setAge(age);
		girl.setCupSize(cupSize);
		girl.setIsUse("1");
		girlRepository.save(girl);
	}
	
	//删
	@Transactional
	public void deleteGirl(String id){
		Girl girl = girlRepository.findOne(Integer.valueOf(Integer.parseInt(id)));
		if (girl != null) {
			girl.setIsUse("0");
			girlRepository.save(girl);
		}
	}
	
}
