import com.rabbitmq.client.Channel;

public class ManualAck2 {
    private static final String TASK_QUEUE_NAME = "manualAck";

    public static void main(String[] args) {
        try {
            Channel channel = RabbitUtils.getChannel();
            channel.basicQos(1);
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
            boolean autoAck = false;
            channel.basicConsume(TASK_QUEUE_NAME, autoAck, (s, delivery) -> {
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                System.out.println("接收到消息" + new String(delivery.getBody()));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }, tag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
