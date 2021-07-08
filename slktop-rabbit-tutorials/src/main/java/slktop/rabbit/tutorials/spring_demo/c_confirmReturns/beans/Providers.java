package slktop.rabbit.tutorials.spring_demo.c_confirmReturns.beans;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import slktop.rabbit.tutorials.spring_demo.c_confirmReturns.config.AppConfirmCallback;
import slktop.rabbit.tutorials.spring_demo.c_confirmReturns.config.AppMessagePostProcessor;
import slktop.rabbit.tutorials.spring_demo.c_confirmReturns.config.AppReturnCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Component
@SuppressWarnings("all")
public class Providers {
    private static final String QUEUE = "spring.publisher.sample";
    private final CountDownLatch listenLatch = new CountDownLatch(1);
    @Autowired
    RabbitTemplate rabbitTemplate;

    Boolean isSet = Boolean.FALSE;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    private void runDemo() throws Exception {
        if (!isSet) {
            rabbitTemplate.setConfirmCallback(new AppConfirmCallback());
            rabbitTemplate.setReturnCallback(new AppReturnCallback());
            isSet = Boolean.TRUE;
        }
        // 正确的消息
        CorrelationData correlationData = new CorrelationData("Correlation for message 1");
        this.rabbitTemplate.convertAndSend("", QUEUE, "foo", correlationData);
        CorrelationData.Confirm confirm = correlationData.getFuture().get(10, TimeUnit.SECONDS);
        System.out.println("收到了ack: " + confirm.isAck());
        // 10秒内consumer执行：this.listenLatch.countDown();否则执行else
        if (this.listenLatch.await(10, TimeUnit.SECONDS)) {
            System.out.println("消息已经被接收");
        } else {
            System.out.println("消息没有接收");
        }
        // 异常消息，queue不存在
        // AppMessagePostProcessor: 配置消息属性
        correlationData = new CorrelationData("Correlation for message 2");
        this.rabbitTemplate.convertAndSend("", QUEUE+QUEUE, "bar",
                new AppMessagePostProcessor(), correlationData);
        correlationData.getFuture().get(10, TimeUnit.SECONDS);
        System.out.println("Return received:" + correlationData.getReturnedMessage());
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, false, false, true);
    }

    @RabbitListener(queues = QUEUE)
    public void listen(String in) {
        System.out.println("监听到了: " + in);
        this.listenLatch.countDown();

    }
}
