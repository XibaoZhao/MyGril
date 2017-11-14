package com.example.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeixinLogin {
	
	@RequestMapping(value="/wxlogin",method=RequestMethod.GET)
	@ResponseBody
	public String login(String jscode) throws Exception{
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
		String appid="wxe50b5599a4a64d16";
		String secret = "172c13432b731e37b1559ac8aaeb86d7";
		url = url.replaceAll("APPID", appid);
		url = url.replaceAll("SECRET", secret);
		url = url.replaceAll("JSCODE", jscode);
		String openid = "";
		URLConnection connection = new URL(url).openConnection();
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		try {
			StringBuffer buffer = new StringBuffer();
			String temp = null;
			while((temp = br.readLine())!=null){
				buffer.append(temp);
			}
			JSONObject json = new JSONObject(buffer.toString());
			openid = (String) json.get("openid");
			String sessionKey = (String) json.get("session_key");
			System.out.println(openid);
			System.out.println(sessionKey);
		} finally{
			br.close();
			inputStream.close();
		}
		return openid;
	}
	
}
