import com.rabbitmq.client.Channel;

public class Worker01 {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) {
        try {
            Channel channel = RabbitUtils.getChannel();
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
