import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

public class Producer {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) {
        try {
            Channel channel = RabbitUtils.getChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            Map<String, String> bindingKeyMap = new HashMap<>();
            bindingKeyMap.put("info.normal", " 普通 info  信息");
            bindingKeyMap.put("info.special", " 特殊 info  信息");
            bindingKeyMap.put("warning.normal", " 普通 warning  信息");
            bindingKeyMap.put("warning.special", " 普通 special  信息");
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
