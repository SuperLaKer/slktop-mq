package slktop.mq.order.webmvc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import slktop.mq.order.webmvc.entity.OrderProduct;
import slktop.mq.order.webmvc.mapper.OrderProductMapper;
import slktop.mq.order.webmvc.service.IOrderProductService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author slk
 * @since 2021-08-02
 */
@Service
public class OrderProductServiceImpl extends ServiceImpl<OrderProductMapper, OrderProduct> implements IOrderProductService {

}
