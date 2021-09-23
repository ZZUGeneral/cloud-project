package top.yhl.cloud.rocketmq.bysql;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_4");
        // 只有订阅的消息有这个属性a, a >=0 and a <= 3
        /**
         * - 数值比较，比如：**>，>=，<，<=，BETWEEN，=；**
         * - 字符比较，比如：**=，<>，IN；**
         * - **IS NULL** 或者 **IS NOT NULL；**
         * - 逻辑符号 **AND，OR，NOT；**
         *
         * 常量支持类型为：
         *
         * - 数值，比如：**123，3.1415；**
         * - 字符，比如：**'abc'，必须用单引号包裹起来；**
         * - **NULL**，特殊的常量
         * - 布尔值，**TRUE** 或 **FALSE**
         */

        consumer.subscribe("TopicTest", MessageSelector.bySql("a between 0 and 3"));
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

    }
}
