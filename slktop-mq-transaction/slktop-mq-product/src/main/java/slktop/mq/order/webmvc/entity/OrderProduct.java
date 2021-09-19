package slktop.mq.order.webmvc.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author slk
 * @since 2021-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 商品购买数量
     */
    private Integer productNum;


}
