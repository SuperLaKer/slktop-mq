package slktop.mq.order;

import aa.slkenv.dataBase.anno.EnableSpringDataSource;
import aa.slkenv.utils.redis.RedisUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RedisUtils.class)
@EnableSpringDataSource(dataBaseName = "mq_order")
@MapperScan("slktop.mq.order.webmvc.mapper")
public class ProductApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(ProductApplication.class, args);
        System.out.println(ac.getBeanDefinitionNames()[0]);
    }
}
