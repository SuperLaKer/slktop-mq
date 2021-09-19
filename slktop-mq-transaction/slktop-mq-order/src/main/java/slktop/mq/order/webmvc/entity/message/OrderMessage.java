package slktop.mq.order.webmvc.entity.message;

import lombok.Data;
import slktop.mq.order.webmvc.entity.vo.OrderProduct;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderMessage implements Serializable {
    private String order_id;
    private Integer user_id;
    private List<OrderProduct> productIdNum;
}
