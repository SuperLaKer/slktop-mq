package slktop.rabbit.tutorials.spring_demo.c_confirmReturns.config;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;

/**
 * 处理一些异常（没有找到交换机什么的）
 */
public class AppReturnCallback implements ReturnsCallback {
    static AppReturnCallback appReturnCallback;

    public static AppReturnCallback getInstance() {
        if (appReturnCallback == null) {
            appReturnCallback = new AppReturnCallback();
        }
        return appReturnCallback;
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        System.out.println("异常了:"+returned.getMessage());
    }
}
