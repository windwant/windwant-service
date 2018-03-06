package org.rocketmq.test;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/10/30.
 */
public class TConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TProducer.class);

    public static void main(String[] args) throws MQClientException, IOException {
        Properties p = new Properties();
        InputStream in = TConsumer.class.getClassLoader().getResourceAsStream("config.properties");
        if(in != null) {
            p.load(in);
            Enumeration<Object> keys = p.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                System.setProperty(key, p.getProperty(key));
            }
        }
        DOMConfigurator.configure("src/main/resources/log4j2-cus.xml");
        nomalConsumer();
    }

    public static void nomalConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("example_group_name");
        consumer.setNamesrvAddr("192.168.126.128:9876");
        consumer.setVipChannelEnabled(false);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TestTopic", "Tag-One-Way || Tag-Async || TagSyn || TagBatch");
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            AtomicLong consumeTimes = new AtomicLong(0);
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                       ConsumeConcurrentlyContext context) {
                logger.info("{} Receive New Messages: {}%n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

            }
        });
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }

    public static void broadCastConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("example_group_name");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setVipChannelEnabled(false);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.subscribe("TestTopic", "Tag-One-Way || Tag-Async || TagSyn");
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            AtomicLong consumeTimes = new AtomicLong(0);
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

            }
        });
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }

    public static void orderConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("example_group_name");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setVipChannelEnabled(false);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("orderTopic", "TagA || TagC || TagD");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            AtomicLong consumeTimes = new AtomicLong(0);
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                       ConsumeOrderlyContext context) {
                context.setAutoCommit(false);
                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
                this.consumeTimes.incrementAndGet();
                if ((this.consumeTimes.get() % 2) == 0) {
                    return ConsumeOrderlyStatus.SUCCESS;
                } else if ((this.consumeTimes.get() % 3) == 0) {
                    return ConsumeOrderlyStatus.ROLLBACK;
                } else if ((this.consumeTimes.get() % 4) == 0) {
                    return ConsumeOrderlyStatus.COMMIT;
                } else if ((this.consumeTimes.get() % 5) == 0) {
                    context.setSuspendCurrentQueueTimeMillis(3000);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                return ConsumeOrderlyStatus.SUCCESS;

            }
        });
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
