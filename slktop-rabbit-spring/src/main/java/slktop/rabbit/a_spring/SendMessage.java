package slktop.rabbit.a_spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("slktop.rabbit.a_spring")
public class SendMessage {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SendMessage.class);
        RabbitTemplate rabbitTemplate = ac.getBean(RabbitTemplate.class);
        rabbitTemplate.convertAndSend("", "", "");

    }
}
