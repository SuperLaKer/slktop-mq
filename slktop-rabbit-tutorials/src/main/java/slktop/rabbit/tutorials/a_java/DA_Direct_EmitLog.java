package slktop.rabbit.tutorials.a_java;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import slktop.rabbit.tutorials.RabbitTools;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * 案例：持久化error日志，输出warning日志
 * 修改交换机类型为direct
 */
public class DA_Direct_EmitLog {

    private static final String EXCHANGE_NAME = "logs_system_direct_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitTools.getConnectionChannel();
        assert channel != null;

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        HashMap<String, String> map = new HashMap<>();
        map.put("message1---w", "warning");
        map.put("message2---e", "error");
        map.put("message3---e", "error");
        map.put("message4---w", "warning");
        map.put("message5---e", "error");
        map.put("message6---w", "warning");
        map.put("message7---i", "info");

        for (String key : map.keySet()) {
            String publish_key = map.get(key);
            channel.basicPublish(EXCHANGE_NAME, publish_key, null, key.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + map.get(key) + "':'" + key + "'");
            Thread.sleep(3000);
        }
    }
}