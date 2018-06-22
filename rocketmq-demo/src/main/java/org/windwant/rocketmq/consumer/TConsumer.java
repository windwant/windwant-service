package org.windwant.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullStatus;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.windwant.rocketmq.Constants;
import org.windwant.rocketmq.producer.TProducer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/10/30.
 */
public class TConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TProducer.class);
    static {
        Properties p = new Properties();
        InputStream in = TConsumer.class.getClassLoader().getResourceAsStream("config.properties");
        if(in != null) {
            try {
                p.load(in);
                Enumeration<Object> keys = p.keys();
                while (keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    System.setProperty(key, p.getProperty(key));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private DefaultMQPushConsumer consumer;
    private DefaultMQPullConsumer pullCconsumer;


    private String groupName;

    private String nameServer;

    public TConsumer(String groupName, String nameServer){
        this.groupName = groupName;
        this.nameServer = nameServer;
    }

    public void init() {
        consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(nameServer);
        consumer.setVipChannelEnabled(false);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setConsumeThreadMin(1);
        consumer.setConsumeThreadMax(5);

        pullCconsumer = new DefaultMQPullConsumer(groupName);
        pullCconsumer.setNamesrvAddr(nameServer);
        pullCconsumer.setVipChannelEnabled(false);
    }

    public void nomalConsume(String topic, String tags) {
        try {
            consumer.subscribe(topic, tags);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                AtomicLong consumeTimes = new AtomicLong(0);
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {
                    logger.info("{} Receive New Messages: {}", Thread.currentThread().getName(), msgs);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

                }
            });
            consumer.start();
            logger.info("Consumer Started");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void broadCastConsume(String topic, String tags){
        consumer.setMessageModel(MessageModel.BROADCASTING);
        try {
            consumer.subscribe(topic, topic);
            consumer.registerMessageListener(new MessageListenerConcurrently() {

                AtomicLong consumeTimes = new AtomicLong(0);

                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {
                    logger.info("{} Receive New Messages: {}", Thread.currentThread().getName(), msgs);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

                }
            });
            consumer.start();
            logger.info("Consumer Started");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void orderConsume(String topic, String tags){
        try {
            consumer.subscribe(topic, tags);
            consumer.registerMessageListener(new MessageListenerOrderly() {
                AtomicLong consumeTimes = new AtomicLong(0);
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                           ConsumeOrderlyContext context) {
                    context.setAutoCommit(false);
                    logger.info("{} Receive New Messages: {}", Thread.currentThread().getName(), msgs);
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
            logger.info("Consumer Started");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费端拉取信息.
     *
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    public void fetchMessage(String topic){
        try {
            pullCconsumer.start();
            Set<MessageQueue> queues = consumer.fetchSubscribeMessageQueues(topic);
            for(MessageQueue queue: queues){
                PullResult temp = pullCconsumer.pull(queue, null, 0, Integer.MAX_VALUE);
                if(temp.getPullStatus().equals(PullStatus.FOUND)){
                    List<MessageExt> msgs = temp.getMsgFoundList();
                    for (MessageExt msg: msgs){
                        System.out.println(msg);
                    }
                }
            };
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        TConsumer consumer = new TConsumer(Constants.consumerGroup, Constants.nameServer);
        consumer.init();
        consumer.nomalConsume("orderlysend", "syn || asyn || oneway || orderly");
    }
}
