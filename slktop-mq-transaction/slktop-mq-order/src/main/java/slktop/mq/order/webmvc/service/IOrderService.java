package slktop.mq.order.webmvc.service;

import slktop.mq.order.webmvc.controller.OrderResponseResult;
import slktop.mq.order.webmvc.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import slktop.mq.order.webmvc.entity.vo.FlashOrderVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author slk
 * @since 2021-08-02
 */
public interface IOrderService extends IService<Order> {

    OrderResponseResult createFlashOrder(FlashOrderVo flashOrderVo);
}
