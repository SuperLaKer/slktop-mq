package slktop.mq.order.mqBeans;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import slktop.constants.mq.transactions.MQConstants;

import java.io.IOException;

@Component
@RabbitListener(queues = MQConstants.ORDER_MESSAGE_QUEUE)
public class OrderMessageListener {

    @RabbitHandler
    public void orderMessageHandler(String msg, Message message, Channel channel) {

        try {
            if (System.currentTimeMillis() / 2 == 0) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
// [payload={"id":40,"messageStatus":"0","orderId":222,"productIdAndNum":{"102":2},"retry":5},
// headers={amqp_receivedDeliveryMode=PERSISTENT, amqp_receivedExchange=order_product_exchange,
// amqp_deliveryTag=1, amqp_consumerQueue=order_product_queue, amqp_redelivered=false,
// amqp_receivedRoutingKey=order_product_key, amqp_contentEncoding=UTF-8,
// spring_listener_return_correlation=486bb721-06bf-47a1-86a7-0848e38d7abd,
// spring_returned_message_correlation=222, id=8f9483ac-b634-c533-644c-acb32719962c,
// amqp_consumerTag=amq.ctag-HRoKak0s-NXASJVW3SdtCg, amqp_lastInBatch=false,
// contentType=text/plain, timestamp=1627984328720}]