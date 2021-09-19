package slktop.mq.order;

import aa.slkenv.dataBase.ShowSql;
import aa.slkenv.dataBase.anno.EnableSpringDataSource;
import aa.slkenv.utils.redis.RedisUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import slktop.constants.product.ConstSecKillRedisKey;
import slktop.mq.order.appBeans.ExecutorConfig;
import slktop.mq.order.redistools.OrderServiceRedisLua;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@Import({ShowSql.class, RedisUtils.class})
@EnableSpringDataSource(dataBaseName = "mq_order")
@MapperScan(basePackages = "slktop.mq.order.webmvc.mapper")
public class OrderApplication {
    public static void main(String[] args) throws InterruptedException {

        ConfigurableApplicationContext ac = SpringApplication.run(OrderApplication.class, args);
        System.out.println(ac.getBeanDefinitionNames()[0]);

        try {
            OrderServiceRedisLua orderServiceRedisLua = ac.getBean(OrderServiceRedisLua.class);
            Long aLong = orderServiceRedisLua.useLua(ConstSecKillRedisKey.FLASH_KEY + 1, 10);
            System.out.println(aLong);
        } catch (BeansException e) {
            e.printStackTrace();
        } finally {
            TimeUnit.SECONDS.sleep(2);
            // ac.close();
        }


        // asyncMethod(ac);
    }

    private static void asyncMethod(ConfigurableApplicationContext ac) {
        ExecutorConfig executorConfig = ac.getBean(ExecutorConfig.class);
        Future<String> stringFuture = executorConfig.printSome();
        System.out.println("主方法完结...");
        String s = null;
        try {
            s = stringFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }
}
