package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.dao.GirlRepository;
import com.example.dao.cache.RedisDao;
import com.example.domain.Girl;
import com.example.domain.GridPageBean;
import com.example.service.GirlService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneApplicationTests {
	
	@Autowired
	private GirlService girlService;
	
	@Autowired
	private GirlRepository girlDao;
	
	@Test
	public void contextLoads() {
		String cupSize = "";
		int pageNo = 1;
		GridPageBean<Girl> result = girlService.findAll(cupSize, pageNo);
		System.out.println(result.getTotal());
	}
	
	@Test
	public void redisTest(){
		RedisDao redis = new RedisDao("127.0.0.1",6379);
		Girl girl = redis.getGirl(1);
		System.out.println(girl);
	}
	@Test
	public void redisPut(){
		RedisDao redis = new RedisDao("127.0.0.1",6379);
		Girl girl = girlDao.findOne(1);
		redis.putGirl2Redis(girl);
		System.out.println(girl);
	}
}
