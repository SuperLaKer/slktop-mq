package slktop.rocket.base;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class B_AsyncProducer {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 100;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0; i < messageCount; i++) {
            try {
                final int index = i;
                Message msg = new Message("Jodie_topic_1023", "TagA", "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 异步
                producer.send(msg, new AppSendCallback(countDownLatch, index));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 5秒内许要执行countDownLatch.countDown()
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}


/**
 * 消息可靠性保障
 */
class AppSendCallback implements SendCallback {

    CountDownLatch countDownLatch;
    int index;

    public AppSendCallback(CountDownLatch countDownLatch, int index) {
        this.countDownLatch = countDownLatch;
        this.index = index;
    }

    // 消息成功发送
    @Override
    public void onSuccess(SendResult sendResult) {
        countDownLatch.countDown();
        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
    }

    // 消息发送失败
    @Override
    public void onException(Throwable e) {
        countDownLatch.countDown();
        System.out.printf("%-10d Exception %s %n", index, e);
        e.printStackTrace();
    }
}