package slktop.rabbit.tutorials.c_high;

import com.rabbitmq.client.Channel;
import slktop.rabbit.tutorials.c_high.B_ConfirmReturnListener.listeners.AppConfirmListener;

import java.io.IOException;

public class TheMain {
    public static Channel channel;


    public static void main(String[] args) throws IOException {

        /**
         * 生产者发送大量消息如何处理？消费端限流
         * 消费消息时autoAck关闭，手动listener
         * consumer端限流
         * prefetchSize: 消息大小
         * prefetchCount: 消息处理量。需要启用ack才能消费多条消息
         *
         */
        channel.basicQos(0, 1, false);


        // 启用confirmListener
        channel.confirmSelect();

        // 添加一个消息确认者
        channel.addConfirmListener(new AppConfirmListener());

        // 消息成功发送到broker. 后续操作见listener
        channel.basicPublish(null, null, null, null);
    }


}
