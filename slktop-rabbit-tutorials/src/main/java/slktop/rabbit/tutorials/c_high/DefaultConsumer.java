package slktop.rabbit.tutorials.c_high;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;

public class DefaultConsumer implements DeliverCallback {

    protected Channel channel;

    @Override
    public void handle(String consumerTag, Delivery message) throws IOException {

    }

    public DefaultConsumer(Channel channel) {
        this.channel = channel;
    }
}
