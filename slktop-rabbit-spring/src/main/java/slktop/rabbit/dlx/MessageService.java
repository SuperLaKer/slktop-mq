package slktop.rabbit.dlx;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class MessageService {
    @Autowired
    RabbitTemplate template;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void sender(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = simpleDateFormat.format(new Date());

        // String routingKey, Object message, MessagePostProcessor messagePostProcessor
        template.convertAndSend("noListenedExchange", "noListenedQueueKey", "formatDate");
        System.out.println("已经发送消息..."+formatDate);
    }

    @RabbitListener(queues = "listenedQueue")
    public void receiver(String in){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = simpleDateFormat.format(new Date());
        System.out.println("formatDate收到消息："+in);
    }
}
