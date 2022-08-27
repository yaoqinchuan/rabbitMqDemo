import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class ReceiveLogs02 {
    private static final String EXCHANGE_NAME = "logs";
    public static void main(String[] args) {
        try {
            Channel channel = RabbitUtils.getChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");

            channel.basicConsume(queueName, true, (s, delivery) -> {
                System.out.println("接收到消息" + new String(delivery.getBody()));
            }, tag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
