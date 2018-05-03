package org.rocketmq.test;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Hello world!
 *
 */
public class TProducer
{
    private static final Logger logger = LoggerFactory.getLogger(TProducer.class);
    public static void main( String[] args ) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        synProducer();
    }

    /**
     * Application: Reliable synchronous transmission is used in extensive scenes, such as important notification messages, SMS notification, SMS marketing system, etc..
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    public static void synProducer() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr("192.168.126.128:9876");
        // Launch producer
        producer.start();
        int totalMessagesToSend = 1000;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Thread.sleep(500);
            Message message = new Message("TestTopic",
                    "TagSyn",
                    ("Hello Syn message " + i).getBytes());
            // This message will be delivered to consumer 10 seconds later.
//            message.setDelayTimeLevel(3);
            // Send the message
            producer.send(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }

    public static void batchProducer() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr("localhost:9876");
        // Launch producer
        producer.start();
        int totalMessagesToSend = 23;
        List<Message> batch = new ArrayList<Message>();
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic",
                    "TagBatch",
                    ("Hello Syn message " + i).getBytes());
            batch.add(message);
            if(batch.size() == 5){
                producer.send(batch);
                logger.info("batch send: {}", batch);
                batch.clear();
            }
        }
        if(!batch.isEmpty()){
            producer.send(batch);
            logger.info("batch send: {}", batch);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }

    /**
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    public static void scheduledProducer() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr("localhost:9876");
        // Launch producer
        producer.start();
        int totalMessagesToSend = 10;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic",
                    "TagSyn",
                    ("Hello Syn message " + i).getBytes());
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(3);
            // Send the message
            producer.send(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }

    /**
     * Application: asynchronous transmission is generally used in response time sensitive business scenarios.
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    public static void aSyncProducer() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr("localhost:9876");
        // Launch producer
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        int totalMessagesToSend = 10;
        String keys = "OrderID" + String.valueOf(ThreadLocalRandom.current().nextInt(100, 200));
        for (int i = 0; i < totalMessagesToSend; i++) {
            final int index = i;
            Message message = new Message("TestTopic",
                    "Tag-Async",
                    keys,
                    ("Hello async message " + i).getBytes());
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(3);
            // Send the message
            producer.send(message, new SendCallback() {
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }

                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }

        // Shutdown producer after use.
        producer.shutdown();
    }

    /**
     * Application: One-way transmission is used for cases requiring moderate reliability, such as log collection
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    public static void oneWayProducer() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr("localhost:9876");
        // Launch producer
        producer.start();
        int totalMessagesToSend = 10;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic",
                    "Tag-One-Way",
                    ("Hello one-way message " + i).getBytes());
            message.putUserProperty("a", String.valueOf(i));
            // Send the message
            producer.sendOneway(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }

    public static void orderProducer() throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        DefaultMQProducer producer = new DefaultMQProducer("ordergroup");
        producer.setNamesrvAddr("localhost:9876");
        // Launch producer
        producer.start();
        int totalMessagesToSend = 10;
        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < totalMessagesToSend; i++) {
            int orderId = i % 2;
            Message message = new Message("orderTopic",
                    tags[i % tags.length], "KEY" + i,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // Send the message
            producer.send(message, new MessageQueueSelector() {
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, orderId);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }
}
