package com.example.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.GirlRepository;
import com.example.domain.Girl;
import com.example.util.Bean2Excel;

@Service
public class DownService {
	
	@Autowired
	private GirlRepository girlRepository;
	
	public void down(String path) throws IllegalArgumentException, IllegalAccessException, IOException{
		ArrayList<Girl> list = (ArrayList<Girl>) girlRepository.findAll();
		ArrayList<String> info = new ArrayList<>();
		info.add("ID");
		info.add("年龄");
		info.add("cup");
		info.add("可用");
		Bean2Excel.out(list, path, info);
	}
	
}
