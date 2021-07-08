/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package slktop.rabbit.tutorials.b_springboot;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Gary Russell
 * @author Scott Deeg
 * @author Arnaud Cogoluègnes
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan("slktop.rabbit.springboot.tut1")
public class RabbitAmqpTutorialsApplication {

	@Bean
	public CommandLineRunner tutorial() {
		return new RabbitAmqpTutorialsRunner();
	}

	public static void main(String[] args) {
		SpringApplication.run(RabbitAmqpTutorialsApplication.class, args);
	}


	public RabbitTemplate rabbitTemplateCallbackWrapper(RabbitTemplate rabbitTemplate) {


		/*
		 * Confirms/returns enabled in application-confirms.properties - add the callbacks here.
		 */
		rabbitTemplate.setConfirmCallback((correlation, ack, reason) -> {
			if (correlation != null) {
				System.out.println("Received " + (ack ? " ack " : " nack ") + "for correlation: " + correlation);
			}
		});
		rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
			System.out.println("Returned: " + message + "\nreplyCode: " + replyCode
					+ "\nreplyText: " + replyText + "\nexchange/rk: " + exchange + "/" + routingKey);
		});

		return rabbitTemplate;
	}

}
