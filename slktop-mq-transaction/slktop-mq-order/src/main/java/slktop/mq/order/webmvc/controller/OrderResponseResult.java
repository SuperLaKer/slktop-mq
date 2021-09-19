package slktop.mq.order.webmvc.controller;

import lombok.Data;
import slktop.mq.order.webmvc.entity.Order;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderResponseResult implements Serializable {
    private String code;
    private String message;
    private List<Order> data;


    public static OrderResponseResult success(List<Order> order){
        OrderResponseResult orderResponseResult = new OrderResponseResult();
        orderResponseResult.code = "200";
        orderResponseResult.message = "请求成功";
        orderResponseResult.data = order;
        return orderResponseResult;
    }

    public static OrderResponseResult fail(String message){
        OrderResponseResult orderResponseResult = new OrderResponseResult();
        orderResponseResult.code = "200";
        orderResponseResult.message = message;
        orderResponseResult.data = null;
        return orderResponseResult;
    }


}
