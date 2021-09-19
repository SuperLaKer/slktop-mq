package slktop.mq.order.webmvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import slktop.mq.order.webmvc.entity.message.OrderMessage;
import slktop.mq.order.webmvc.entity.vo.OrderProduct;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@TableName("mq_order")
@EqualsAndHashCode(callSuper = false)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("create_time")
    private Timestamp createTime;
    @TableField("update_time")
    private Timestamp updateTime;


    /**
     * 订单描述(秒杀订单、其他订单)
     */
    @TableField("order_describe")
    private String describe = "秒杀订单";


    /**
     * 1.入库 2.broker_ack 3.broker_nack 4.consumer_ack 5.consumer_nack
     */
    @TableField("order_message_status")
    private Integer orderMessageStatus = 1;

    /**
     * 1.入库 2.订单提交 3.待支付 4.已支付 5.已超时 6.用户取消
     */
    @TableField("order_status")
    private Integer orderStatus = 1;


    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 购买的商品信息
     */
    @TableField(value = "product_id_num", typeHandler = JacksonTypeHandler.class)
    private List<OrderProduct> productIdNum;

    /**
     * 全局唯一id，订单唯一id也是消息id。一般使用主键。容错使用
     */
    @TableField("only_id")
    private String onlyId;
    @TableField("product_id")
    private Integer productId;
    @TableField("product_num")
    private Integer productNum;


    public OrderMessage getOrderMessage() {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOrder_id(onlyId);
        orderMessage.setUser_id(userId);
        orderMessage.setProductIdNum(productIdNum);
        return orderMessage;
    }
}
