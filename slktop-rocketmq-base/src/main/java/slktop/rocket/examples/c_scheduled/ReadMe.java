package slktop.rocket.examples.c_scheduled;

/**
 * 比如电商里，提交了一个订单就可以发送一个延时消息，
 * 1h后去检查这个订单的状态，如果还是未付款就取消订单释放库存。
 * 订单创建---发送延时消息---consumer---删除订单（前提：订单是未支付的状态）
 *
 * private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
 * 现在RocketMq并不支持任意时间的延时，需要设置几个固定的延时等级，
 * 从1s到2h分别对应着等级1到18 消息消费失败会进入延时消息队列，
 * 消息发送时间与设置的延时等级和重试次数有关，详见代码SendMessageProcessor.java
 */
public class ReadMe {
}
