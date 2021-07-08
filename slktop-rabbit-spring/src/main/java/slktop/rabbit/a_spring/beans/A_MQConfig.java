package slktop.rabbit.a_spring.beans;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import slktop.rabbit.a_spring.Constants;

@Configuration
@SuppressWarnings("all")
public class A_MQConfig {

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("/");
        factory.setConnectionTimeout(10000);
        factory.setCloseTimeout(10000);
        return factory;
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 默认就是true, spring容器启动加载
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(Constants.exchange_name_topic, false, false, null);
    }

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange(Constants.exchange_name_fanout, false, false, null);
    }

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(Constants.exchange_name_direct, false, false, null);
    }

    // 声明多个
    @Bean
    Queue demoQueue(){
        return new Queue(Constants.queue_name, false, false, false, null);
    }

    @Bean
    Binding topicExchangeQueueBinding(){
        return BindingBuilder.bind(demoQueue()).to(topicExchange()).with(Constants.demo_routing_key_1);
    }
}
