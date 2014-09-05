/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package s2gx2014.messaging;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * A simple Boot app that sends a message and collect the reply using the new
 * Annotated listener endpoint feature of Spring 4.1.
 *
 * @author Stephane Nicoll
 */
@ComponentScan
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private JmsMessagingTemplate messagingTemplate;

	@Override
	public void run(String... args) throws Exception {
		Order order = new Order("123", new Date(), "customer-foo-123");
		Message<Order> message = MessageBuilder.withPayload(order)
				.setHeader("orderType", "sell").build();
		logger.info("----------------------");
		logger.info("Sending " + order);
		logger.info("----------------------");

		Message<?> reply = messagingTemplate.sendAndReceive("order", message);
		logger.info("++++++++++++++++++++++");
		logger.info("Received " + reply);
		logger.info("++++++++++++++++++++++");
	}

	@Component
	public static class OrderMessageHandler {

		@JmsListener(destination = "order")
		public OrderStatus process(Order order, @Header String orderType) {
			return new OrderStatus(order, orderType + "-12345");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
