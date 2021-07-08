package slktop.rabbit.tutorials;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class RabbitTools {


    public static Map<String, Object> propsMap = new HashMap<>();

    public static void main(String[] args) {
    }

    public static String getRandomString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int temp = new Random().nextInt(58) + 65;
            if (temp > 90 && temp < 97) {
                continue;
            }
            char tempChar = (char) temp;
            stringBuilder.append(tempChar);
        }
        return stringBuilder.toString();
    }

    public static Channel getConnectionChannel() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        try {
            Connection connection = factory.newConnection();
            return connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static AMQP.BasicProperties.Builder PropsBuilder() {
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
        builder.deliveryMode(2)  // 持久化
                .expiration("10000")  // 消息过期
                .contentEncoding("UTF-8")
                .correlationId(UUID.randomUUID().toString());  // ack有关
        if (propsMap != null) {
            builder.headers(propsMap);
        }
        return builder;
    }
}
