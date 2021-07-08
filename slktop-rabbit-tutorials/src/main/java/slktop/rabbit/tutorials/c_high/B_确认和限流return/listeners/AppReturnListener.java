package slktop.rabbit.tutorials.c_high.B_确认和限流return.listeners;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 异常消息：没有发送到exchange,手误写错了exchange的名字
 */
public class AppReturnListener implements ReturnListener {
    @Override
    public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("return: "+new String(body, Charset.defaultCharset()));
    }
}
