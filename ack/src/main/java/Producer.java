import com.rabbitmq.client.Channel;

import java.util.Scanner;

public class Producer {

    private static final String TASK_QUEUE_NAME = "manualAck";

    public static void main(String[] args) {
        try {
            Channel channel = RabbitUtils.getChannel();
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String message = scanner.next();
                channel.basicPublish("",TASK_QUEUE_NAME,null,message.getBytes());
                System.out.println(" 发送消息完成:"+message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
