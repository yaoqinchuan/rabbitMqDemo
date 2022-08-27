import com.rabbitmq.client.Channel;
import java.util.Scanner;

public class Producer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        try {
            Channel channel = RabbitUtils.getChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String message = scanner.next();
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
                System.out.println(" 发送消息完成:"+message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
