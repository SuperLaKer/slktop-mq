package slktop.mq.order.mq;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageReturnCallback implements RabbitTemplate.ReturnsCallback {
    /**
     * 发送消息失败原因
     */
    @Override
    public void returnedMessage(ReturnedMessage message) {
        int replyCode = message.getReplyCode();
        String replyText = message.getReplyText();
        String messageId = message.getMessage().getMessageProperties().getMessageId();
        System.out.println("发送消息失败=============" + messageId + replyText + replyCode);

    }
}
