import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class ReceiveErrorLogs {
    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String routingKey = "error";
    public static void main(String[] args) {
        try {
            Channel channel = RabbitUtils.getChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

            channel.basicConsume(queueName, true, (s, delivery) -> {
                System.out.println("接收到消息" + new String(delivery.getBody()));
            }, tag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
