package com.task.core;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoreApplicationTests {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	void alive_objects() {
		assertNotNull(rabbitTemplate);
	}

	@Test
	public void bombing_queue() {
		for (int i=0; i< 100; i++) {
			rabbitTemplate.convertAndSend("task", "dfdg"+i);
		}
	}

	@Test
	public void send_msg_to_queue() {
		rabbitTemplate.convertAndSend("task", "simple");
	}
}
