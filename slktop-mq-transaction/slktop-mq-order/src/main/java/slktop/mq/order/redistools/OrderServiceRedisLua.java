package slktop.mq.order.redistools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;

@Configuration
public class OrderServiceRedisLua {

    @Autowired
    @SuppressWarnings("all")
    RedisTemplate redisTemplate;

    public Long useLua(String product_key, Object... args) {

        StringBuilder stringBuilder = new StringBuilder()
                .append("local store =  tonumber(redis.call('get', KEYS[1])) ")
                .append("local needNum = tonumber(ARGV[1]) ")
                .append("local last = (store - needNum)")
                .append("if last > 0 then ")
                .append("   redis.call('set', KEYS[1], store - needNum) ")
                .append("   return 1 ")
                .append("end ")
                .append("if last == 0 then ")
                .append("   redis.call('set', KEYS[1], store - needNum) ")
                .append("   return 0 ")
                .append("end ")
                .append("return -1 ");
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(new String(stringBuilder), Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        return (Long) redisTemplate.execute(redisScript, Collections.singletonList(product_key), args);
    }
}
