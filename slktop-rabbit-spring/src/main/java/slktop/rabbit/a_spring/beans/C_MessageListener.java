package slktop.rabbit.a_spring.beans;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SuppressWarnings("all")
public class C_MessageListener {
    @Autowired
    Queue demoQueue;

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReceiveTimeout(50000);
        return rabbitTemplate;
    }

    /**
     * 消息监听器的容器：此容器用于存储、配置消息的监听器
     */
    @Bean
    SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        commonConfig(listenerContainer);  // container的通用配置
        // 监听器适配器及配置
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new B_MessageListenerDelegate());

        // 根据message的类型选择不同的policy
        MessageListenerAdapter defaultMethodPolicyAdapter = MessageListenerAdapters.defaultMethodPolicy(messageListenerAdapter);
        MessageListenerAdapter jsonHandlerPolicyAdapter = MessageListenerAdapters.jsonHandlerPolicy(messageListenerAdapter);
        listenerContainer.setMessageListener(defaultMethodPolicyAdapter);
        return listenerContainer;
    }


    // listenerContainer的通用配置
    private void commonConfig(SimpleMessageListenerContainer listener) {
        listener.setQueues(demoQueue);  // 监听的队列（...可以配置多个）
        listener.setConcurrentConsumers(5);  // 消费者数量
        listener.setMaxConcurrentConsumers(5);  // 最大消费者数量
        // 设置自动签收模式
        listener.setAcknowledgeMode(AcknowledgeMode.AUTO);
        // 拒绝重回队列
        listener.setDefaultRequeueRejected(false);
    }
}


