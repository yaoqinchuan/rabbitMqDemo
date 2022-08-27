import com.rabbitmq.client.Channel;

import java.util.UUID;

public class SingleConfirmProducer {
    private static Integer MESSAGE_COUNT = 1000;
    public static void main(String[] args) {
        try {
            String queueName = UUID.randomUUID().toString();
            Channel channel = RabbitUtils.getChannel();
            channel.confirmSelect();
            channel.queueDeclare(queueName, false, false, false, null);
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                String message = i + " ";
                channel.basicPublish("",queueName,null,message.getBytes());
                channel.waitForConfirms();
            }
            long end = System.currentTimeMillis();
            System.out.println(" 发布" + MESSAGE_COUNT + " 个单独确认消息, 耗时" + (end - startTime) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
