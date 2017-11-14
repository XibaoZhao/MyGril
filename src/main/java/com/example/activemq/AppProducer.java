package com.example.activemq;

import javax.jms.Destination;
import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppProducer {
	private static Logger logger = LoggerFactory.getLogger(AppProducer.class);
	
	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	
	public void sendMessage(Destination destination,final String message){
		//1.创建ConnectionFactory
		//2.创建connect
		//3.启动连接
		//4.创建会话
		jmsTemplate.convertAndSend(destination, message);
	}
}
