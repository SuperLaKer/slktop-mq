package com.baomidou.web.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author slk
 * @since 2021-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MqOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态（Java用Integer）1新建默认、2消息发送到server、3待支付、4已支付、5超时
     */
    private Boolean status;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 订单描述(秒杀订单、其他订单)
     */
    private String describe;

    /**
     * 购买的商品信息
     */
    private String productIdNum;

    /**
     * 1.刚刚创建 2.broker_ack 3.broker_nack 4.consumer_ack 5.consumer_nack
     */
    private Boolean orderMessageStatus;

    /**
     * 1.订单创建 2.订单提交 3.待支付 4.已支付 5.已超时 6.用户取消
     */
    private Boolean orderStatus;

    /**
     * 全局唯一id，订单唯一id也是消息id。一般使用主键。容错使用
     */
    private String onlyId;


}
