package com.example.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	@JmsListener(destination="mytest.queue")
	public void receiveQueue(String text){
		System.out.println(this.getClass().getName()+"接收的报文:"+text);
	}
	
}
