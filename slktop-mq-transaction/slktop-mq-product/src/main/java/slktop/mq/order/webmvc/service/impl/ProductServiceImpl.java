package slktop.mq.order.webmvc.service.impl;

import aa.slkenv.utils.redis.RedisUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import slktop.constants.product.ConstSecKillRedisKey;
import slktop.mq.order.webmvc.entity.Product;
import slktop.mq.order.webmvc.entity.vo.ToSecKillProductVo;
import slktop.mq.order.webmvc.mapper.ProductMapper;
import slktop.mq.order.webmvc.service.IProductService;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    @SuppressWarnings("all")
    ProductMapper productMapper;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public String createSecKillProduct(ToSecKillProductVo toSecKillProductVo) {
        // 假设使用注解校验
        Integer product_id = toSecKillProductVo.getProduct_id();
        Integer product_store = toSecKillProductVo.getProduct_store();
        // 结束时间 - 开始时间
        Integer last_time = 3600*10;
        try {
            redisUtils.set(ConstSecKillRedisKey.FLASH_KEY+product_id, product_store);
            return "ok";
        } catch (Exception e) {
            return "上架失败, 商品编号为："+product_id;
        }
    }
}
