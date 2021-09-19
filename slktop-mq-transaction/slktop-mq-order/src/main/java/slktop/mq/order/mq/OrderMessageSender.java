/*
 * Copyright 2015 the original author or authors.
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
package slktop.mq.order.mq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import slktop.constants.mq.transactions.MQConstants;
import slktop.mq.order.webmvc.entity.message.OrderMessage;

/**
 * @author Gary Russell
 * @author Scott Deeg
 */
@Configuration
public class OrderMessageSender implements InitializingBean {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange orderProductExchange;

    // @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send(OrderMessage message) {
        String messageString = JSON.toJSONString(message);
        CorrelationData correlationData = new CorrelationData(message.getOrder_id());
        rabbitTemplate.convertAndSend(orderProductExchange.getName(),
                MQConstants.ORDER_MESSAGE_ROUTING_KEY, messageString, correlationData);
        System.out.println(" [x] Sent '" + message + "'");
    }

    @Autowired
    OrderMessageConfirmCallback orderMessageConfirmCallback;
    @Autowired
    OrderMessageReturnCallback orderMessageReturnCallback;

    @Override
    public void afterPropertiesSet() {
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(orderMessageConfirmCallback);
        rabbitTemplate.setReturnsCallback(orderMessageReturnCallback);
    }
}
