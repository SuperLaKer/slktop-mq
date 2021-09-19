package slktop.rocket.examples.b_order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Producer: 把顺序Message对象发送到同一个queue
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        // tags过滤消息
        String[] tags = new String[]{"TagA", "TagC", "TagD"};
        // 订单状态有序：创建、付款、完成、推送、完成
        // 两个订单共10条消息，订单id一样的放到同一个queue中
        List<OrderStep> orderList = new Producer().buildOrders();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String dateStr = sdf.format(date);
        try {
            for (int i = 0; i < 10; i++) {
                // 订单编号
                long orderId = orderList.get(i).getOrderId();
                String rowMsg = dateStr +" " +orderList.get(i);
                Message message = new Message("TopicTest", tags[i % tags.length], "KEY" + i, rowMsg.getBytes());
                // selector保证一组消息发送到同一个queue
                // 同步发送消息等待result
                message.setDelayTimeLevel(3);
                SendResult sendResult = producer.send(message, new ProducerQueueSelector(), orderId);//订单id
                System.out.printf("SendResult status:%s, queueId:%d, body:%s%n",
                        sendResult.getSendStatus(),
                        sendResult.getMessageQueue().getQueueId(),
                        rowMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }

    }

    /**
     * 订单的步骤
     */
    private static class OrderStep {
        private long orderId;
        private String desc;

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "OrderStep{" +
                    "orderId=" + orderId +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }

    /**
     * 生成模拟订单数据
     */
    private List<OrderStep> buildOrders() {
        List<OrderStep> orderList = new ArrayList<OrderStep>();

        long orderId_1 = 15103111039L;
        long orderId_2 = 15103111065L;
        long orderId_3 = 15103117235L;

        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderId(orderId_1);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(orderId_2);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(orderId_3);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(orderId_1);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(orderId_2);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(orderId_3);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(orderId_1);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(orderId_1);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(orderId_2);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(orderId_3);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        return orderList;
    }
}