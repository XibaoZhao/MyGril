package com.example.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.dao.GirlRepository;
import com.example.domain.Girl;
import com.example.domain.GridPageBean;

@Service
public class GirlService {
	
	@Autowired
	private GirlRepository girlRepository;
	
	//查
	public GridPageBean<Girl> findAll(String cupSize,int pageNo){
		Specification<Girl> spe = buildSpecification(cupSize);
		int rows = 5;
		Page<Girl> data = girlRepository.findAll(spe,new PageRequest(pageNo-1,rows));
		GridPageBean<Girl> result = new GridPageBean<Girl>();
		result.setPage(pageNo);
		result.setRecords(data.getTotalElements());
		result.setRows(data.getContent());
		result.setTotal(data.getTotalPages());
		return result;
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
	
	private Specification<Girl> buildSpecification(final String cupSize){
		return new Specification<Girl>() {
			@Override
			public Predicate toPredicate(Root<Girl> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if(!StringUtils.isEmpty(cupSize)){
					expressions.add(cb.equal(root.<String> get("cupSize"),cupSize));
				}
				return predicate;
			}
		};
	}
}
