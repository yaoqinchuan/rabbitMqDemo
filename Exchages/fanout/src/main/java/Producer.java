import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

public class Producer {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {
        try {
            Channel channel = RabbitUtils.getChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String message = scanner.next();
                channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
                System.out.println(" 发送消息完成:"+message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
