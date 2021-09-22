package top.yhl.cloud.rocketmq.bysql;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.start();
        Message msg = new Message("TopicTest",
                "tag",
                ("Hello RocketMQ " + 1).getBytes(RemotingHelper.DEFAULT_CHARSET)
        );
        // 设置一些属性
        msg.putUserProperty("a", String.valueOf(1));
        SendResult sendResult = producer.send(msg);

        producer.shutdown();
    }
}
