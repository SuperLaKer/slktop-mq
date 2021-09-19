package slktop.mq.order.webmvc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import slktop.mq.order.webmvc.entity.Order;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author slk
 * @since 2021-08-02
 */
public interface OrderMapper extends BaseMapper<Order> {


    @Update({
            "update mq_order set order_message_status = #{messageStatus} where only_id = #{order_id}"
    })
    int updateMessageByOrderId(@Param("messageStatus") Integer messageStatus, @Param("order_id") String order_id);

}
