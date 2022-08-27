import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("admin");
        factory.setPassword("123456");
        System.out.println(" 等待接收消息 ......... ");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicConsume(QUEUE_NAME, true,
                    (consumerTag, delivery) -> {
                        System.out.println(new String(delivery.getBody()));
                    },
                    (consumerTag) -> {
                        System.out.println("消息消费失败");
                    });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
