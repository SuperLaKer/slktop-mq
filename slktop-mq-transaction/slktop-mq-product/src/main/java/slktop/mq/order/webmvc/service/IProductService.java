package slktop.mq.order.webmvc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import slktop.mq.order.webmvc.entity.Product;
import slktop.mq.order.webmvc.entity.vo.ToSecKillProductVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author slk
 * @since 2021-08-02
 */
public interface IProductService extends IService<Product> {

    String createSecKillProduct(ToSecKillProductVo toSecKillProductVo);
}
