package slktop.rabbit.tutorials.c_high.B_ConfirmReturnListener.listeners;

import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

/**
 * 成功发送到了broken，回调handleAck
 *
 * handleAck: 确保server收到消息(网络调用修改消息状态)，handleAck远程调用网络异常(producer定时任务)
 *
 *
 */
public class AppConfirmListener implements ConfirmListener {

    /**
     * 回调异常：
     */
    @Override
    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("ConfirmListener: 已经发送到了server...");
    }

    @Override
    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("ConfirmListener: 没有发送到server...");
    }
}
