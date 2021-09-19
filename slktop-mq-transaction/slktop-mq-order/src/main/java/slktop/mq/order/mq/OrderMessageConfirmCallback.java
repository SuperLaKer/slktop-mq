package slktop.mq.order.mq;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import slktop.mq.order.webmvc.mapper.OrderMapper;

@Component
@Primary
public class OrderMessageConfirmCallback implements RabbitTemplate.ConfirmCallback {


    @Autowired
    @SuppressWarnings("all")
    OrderMapper orderMapper;

    /**
     * ack为true：表示成功发送消息到服务器
     * 发送消息时必须有correlationData
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("进入ack");
        if (correlationData == null) {
            return;
        }
        String order_id = correlationData.getId();
        // 更新order_message_status为2，表示成功发送到broker
        if (ack) {
            int i = orderMapper.updateMessageByOrderId(2, order_id);
            System.out.println("ack");
        } else {
            int i = orderMapper.updateMessageByOrderId(3, order_id);
            System.out.println("nack");
        }
    }
}
