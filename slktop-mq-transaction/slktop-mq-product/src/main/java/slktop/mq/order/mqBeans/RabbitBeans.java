package slktop.mq.order.mqBeans;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import slktop.constants.mq.transactions.MQConstants;

@Configuration
public class RabbitBeans {
    @Bean
    public Queue orderProductQueue() {
        return new Queue(MQConstants.ORDER_MESSAGE_QUEUE);
    }

    @Bean
    public DirectExchange orderProductDirectExchange(){
        return new DirectExchange(MQConstants.ORDER_MESSAGE_EXCHANGE, false, false);
    }

    @Bean
    public Binding binding1a(DirectExchange orderProductDirectExchange, Queue orderProductQueue) {
        return BindingBuilder.bind(orderProductQueue).to(orderProductDirectExchange).with(MQConstants.ORDER_MESSAGE_ROUTING_KEY);
    }
}
