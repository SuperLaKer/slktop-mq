package slktop.mq.order.webmvc.entity.vo;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
public class FlashOrderVo implements Serializable {
    // 数据库存在，和登录用户信息一直
    private Integer user_id;
    // 秒杀，一件商品
    @Size(min = 1, max = 1)
    private List<OrderProduct> productIdNum;
}
