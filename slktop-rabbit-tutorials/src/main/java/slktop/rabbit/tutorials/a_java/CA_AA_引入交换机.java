package slktop.rabbit.tutorials.a_java;

/**
 * 交换机类型:
 * fanout广播，所有的消费者都能消费消息
 * direct: exchange和queue，只有和消息key完全匹配的queue才能接受消息
 * topic: exchange和queue，消息key和queue的正则只要匹配就行
 * headers模式 todo
 *
 * 案例：
 * 日志广播，一个消费者持久化日志另一个消费者在屏幕上输出日志
 * exchange类型fanout，每个consumer收到相同的日志做不一样的事
 */
@SuppressWarnings("all")
public class CA_AA_引入交换机 {
    public static void main(String[] args) {

    }
}
