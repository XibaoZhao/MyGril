package com.example;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.activemq.AppProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueueTest {
	
	@Autowired
	private AppProducer producer;
	
	@Test
	public void a(){
		Destination destination = new ActiveMQQueue("my.queue");
		for(int i=0;i<100;i++){
			producer.sendMessage(destination,""+i);
		}
	}
	
}
