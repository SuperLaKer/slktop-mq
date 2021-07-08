package slktop.rabbit.tutorials.a_java;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;

/**
 * Work Queues
 * <p>
 * 我们的NewTask.java类的最终代码
 * For more information on Channel methods and MessageProperties, you can browse the JavaDocs online
 * https://rabbitmq.github.io/rabbitmq-java-client/api/current/
 * <p>
 * Now we can move on to tutorial 3 and learn how to deliver the same message to many consumers
 * https://www.rabbitmq.com/tutorials/tutorial-three-java.html
 */
public class BA_WorkerQueues_Task {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            String message = String.join(" ", argv);

            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
