package slktop.rabbit.dlx.beans;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    /**
     * 无监听
     */
    @Bean
    DirectExchange noListenedExchange(){
        return new DirectExchange("noListenedExchange");
    }
    @Bean
    public Queue noListenedQueue(){
        return QueueBuilder
                .nonDurable("noListenedQueue")
                // 过期后把消息发送到这个交换机
                .withArgument("x-message-ttl", 10000)
                .withArgument("x-dead-letter-exchange", "listenedExchange")
                .withArgument("x-dead-letter-routing-key", "listenedQueueKey")
                .build();
    }
    @Bean
    public Binding noBinding(DirectExchange noListenedExchange, Queue noListenedQueue){
        return BindingBuilder.bind(noListenedQueue).to(noListenedExchange).with("noListenedQueueKey");
    }


    /**
     * 死信
     */
    @Bean
    DirectExchange listenedExchange(){
        return new DirectExchange("listenedExchange");
    }
    @Bean
    public Queue listenedQueue(){
        return new Queue("listenedQueue", false);
    }
    @Bean
    public Binding listenedBinding(DirectExchange listenedExchange, Queue listenedQueue){
        return BindingBuilder.bind(listenedQueue).to(listenedExchange).with("listenedQueueKey");
    }
}
