package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.Girl;
import com.example.domain.GridPageBean;
import com.example.service.GirlService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneApplicationTests {
	
	@Autowired
	private GirlService girlService;
	
	@Test
	public void contextLoads() {
		String cupSize = "";
		int pageNo = 1;
		GridPageBean<Girl> result = girlService.findAll(cupSize, pageNo);
		System.out.println(result.getTotal());
	}

}
