package slktop.rabbit.tutorials.spring_demo.c_confirmReturns.config;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;

public class AppConfirmCallback implements ConfirmCallback {

    static AppConfirmCallback appConfirmCallback;

    public static AppConfirmCallback getInstance() {
        if (appConfirmCallback == null) {
            appConfirmCallback = new AppConfirmCallback();
        }
        return appConfirmCallback;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (correlationData != null) {
            System.out.println(".........AppConfirmCallback.........:"+correlationData+ack);
        }
    }
}
