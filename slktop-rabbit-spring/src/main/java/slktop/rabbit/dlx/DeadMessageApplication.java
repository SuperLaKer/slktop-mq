package slktop.rabbit.dlx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 死信队列
 * 没有被消费、消息过期，消息会被转到死信队列中
 * 消息数量大于queue容量
 *
 */
@EnableScheduling
@SpringBootApplication
public class DeadMessageApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(DeadMessageApplication.class, args);
        System.out.println(ac.getBeanDefinitionNames()[0]);
    }


}
