package slktop.rabbit.tutorials.a_java;

/**
 * 交换机类型:
 * fanout广播，routing_key没用
 * direct模式相当于在exchange和queue之间加了把锁
 * topic模式在exchange和queue之间进行正则过滤
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
