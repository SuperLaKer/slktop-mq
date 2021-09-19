package slktop.rocket.examples.b_order;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Random;

public class ConsumerMessageListener implements MessageListenerOrderly {
    final Random random = new Random();
    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        context.setAutoCommit(true);
        for (MessageExt msg : msgs) {
            // 可以看到每个queue有唯一的consume线程来消费, 订单对每个queue(分区)有序
            System.out.println("consumeThread=" + Thread.currentThread().getName()
                    + " queueId=" + msg.getQueueId()+ " content:" + new String(msg.getBody()));
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}