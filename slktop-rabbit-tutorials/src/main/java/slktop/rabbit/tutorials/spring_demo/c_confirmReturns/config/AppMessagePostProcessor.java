package slktop.rabbit.tutorials.spring_demo.c_confirmReturns.config;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

public class AppMessagePostProcessor implements MessagePostProcessor {
    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        System.out.println(message);
        message.getMessageProperties().setContentEncoding("UTF-8");
        return message;
    }
}
