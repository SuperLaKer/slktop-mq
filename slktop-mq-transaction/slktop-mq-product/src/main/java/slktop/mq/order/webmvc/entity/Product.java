package slktop.mq.order.webmvc.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 库存
     */
    private Integer productStore;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品创建时间
     */
    private LocalDateTime createTime;

    /**
     * 上架时间
     */
    private LocalDateTime beginTime;

    /**
     * 下架时间
     */
    private LocalDateTime endTime;

    /**
     * 商品价格
     */
    private String productMoney;

    /**
     * 是否是秒杀商品
     */
    private String isSeckill;


}
