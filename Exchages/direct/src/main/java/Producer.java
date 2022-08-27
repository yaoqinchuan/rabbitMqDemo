import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Producer {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) {
        try {
            Channel channel = RabbitUtils.getChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            Map<String, String> bindingKeyMap = new HashMap<>();
            bindingKeyMap.put("info", " 普通 info  信息");
            bindingKeyMap.put("error", " 错误 error  信息");
            //debug  没有消费这接收这个消息 所有就丢失了
            bindingKeyMap.put("debug", " 调试 debug  信息");
            for (Map.Entry<String, String> bindingKeyEntry : bindingKeyMap.entrySet()) {
                String bindingKey = bindingKeyEntry.getKey();
                String message = bindingKeyEntry.getValue();
                channel.basicPublish(EXCHANGE_NAME, bindingKey, null, message.getBytes("UTF-8"));
                System.out.println(" 生产者发出消息:" + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
