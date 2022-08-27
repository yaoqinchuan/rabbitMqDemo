import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SyncConfirmProducer {
    private static Integer MESSAGE_COUNT = 1000;

    public static void main(String[] args) {
        try {
            String queueName = UUID.randomUUID().toString();
            Channel channel = RabbitUtils.getChannel();
            channel.confirmSelect();
            channel.queueDeclare(queueName, false, false, false, null);
            /**
             *  线程安全有序的一个哈希表，适用于高并发的情况 
             * 1. 轻松的将序号与消息进行关联 
             * 2. 轻松批量删除条目 只要给到序列号 
             * 3. 支持并发访问 
             */
            ConcurrentHashMap<Long, String> outstandingConfirms = new ConcurrentHashMap<>();

            /**
             *  确认收到消息的一个回调
             * 1. 消息序列号
             * 2.true  可以确认小于等于当前序列号的消息
             * false  确认当前序列号消息
             */
            ConfirmCallback ackCallBack = (sequenceNumber, multiple) -> {
                if (multiple) {
                    for (int i = 0; i <= sequenceNumber; i++) {
                        outstandingConfirms.remove(i);
                    }
                } else {
                    outstandingConfirms.remove(sequenceNumber);
                }
            };

            ConfirmCallback nackCallBack = (sequenceNumber, multiple) -> {
                String message = outstandingConfirms.get(sequenceNumber);
                System.out.println(" 发布的消息" + message + " 未被确认，序列号" + sequenceNumber);
            };

            channel.addConfirmListener(ackCallBack, nackCallBack);
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                String message = "消息" + i;
                outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
                channel.basicPublish("", queueName, null, message.getBytes());
            }
            long end = System.currentTimeMillis();
            System.out.println(" 发布" + MESSAGE_COUNT + " 个单独确认消息, 耗时" + (end - startTime) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
