package slktop.rocket.examples.b_order;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class ProducerQueueSelector implements MessageQueueSelector {
    @Override
    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
        // 根据订单id选择发送queue
        Long id = (Long) arg;
        long index = id % mqs.size();
        return mqs.get((int) index);
    }
}
