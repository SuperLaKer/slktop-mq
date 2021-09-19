package slktop.mq.order.webmvc.entity.vo;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 商品上架为秒杀商品
 */
@Data
public class ToSecKillProductVo implements Serializable {

    // todo 用户校验，用户是否有权限上架商品

    @NotNull(message = "商品id不能为空")
    private Integer product_id;
    @NotNull(message = "库存不能为空")
    @DecimalMin(value = "1", message = "库存必须大于等于1")
    private Integer product_store;
    private Timestamp begin_time;
    private Timestamp end_time;
}
