package slktop.mq.order.webmvc.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderProduct implements Serializable {
    private Integer product_id;
    private Integer product_num;
}
