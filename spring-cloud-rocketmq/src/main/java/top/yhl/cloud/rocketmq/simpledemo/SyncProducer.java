package top.yhl.cloud.rocketmq.simpledemo;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

// 可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        // 实例化消息生产者 Producer
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 设置 NameServer 地址
        producer.setNamesrvAddr("localhost:9876");
        // 启动 producer 实例
        producer.start();
        for (int i = 0; i < 100; i++) {
            // 创建消息，并指定 Topic， Tag 和消息体
            Message msg = new Message("TopicTest", "TagA", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 发送消息到一个 Broker
            SendResult sendResult = producer.send(msg);
            // 通过 sendResult 返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        // 如果不再发送消息，关闭 producer 实例
        producer.shutdown();
    }
}
