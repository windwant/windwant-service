package org.windwant.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.windwant.rocketmq.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Hello world!
 *
 */
public class TProducer{
    private DefaultMQProducer producer;

    private static final Logger logger = LoggerFactory.getLogger(TProducer.class);

    private String groupName;

    private String nameServer;

    TProducer(){};

    public TProducer(String groupName, String nameServer){
        this.groupName = groupName;
        this.nameServer = nameServer;
    }

    public void init(){
        producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(nameServer);
        // Launch producer
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
    /**
     * 构建消息
     * @param topic
     * @param tag
     * @param msg
     * @param delay
     * @return
     */
    public Message composeMsg(String topic, String tag, String msg, int delay){
        Message message = new Message(topic, tag, msg.getBytes());
        if (delay > 0) {
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(delay);
        }
        return message;
    }

    /**
     * Application: Reliable synchronous transmission is used in extensive scenes, such as important notification messages, SMS notification, SMS marketing system, etc..
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    public void synSend(String topic, String tag, String msg, int delay){
        // Send the message
        SendResult result = null;
        try {
            result = producer.send(composeMsg(topic, tag, msg, delay));
            logger.info("syn send result: {}", result);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量发送
     * @param topic
     * @param tag
     * @param msg
     * @param batchSize
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    public void batchSend(final String topic, final String tag, List<String> msg, int batchSize) {
        List<Message> batch = new ArrayList();
        msg.stream().forEach(m -> {
            batch.add(composeMsg(topic, tag, m, -1));
        });
        try {
            if (batchSize > 0 && batch.size() >= batchSize) {
                for (int i = 0; i < batch.size() / batchSize; i++) {
                    producer.send(batch.subList(batchSize * 0, batchSize));
                    logger.info("batch send batch " + i + ": {}", batch);
                }
            }
            if (batch.size() % batchSize != 0) {
                producer.send(batch.subList(batch.size() - batch.size() % batchSize, batch.size()));
                logger.info("batch send last: {}", batch);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Application: asynchronous transmission is generally used in response time sensitive business scenarios.
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    public void asynSend(String topic, String tag, String msg, int delay){
        Message message = new Message(topic, tag, msg.getBytes());
        if (delay > 0) {
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(delay);
        }
        // Send the message
        try {
            producer.send(message, new SendCallback() {
                public void onSuccess(SendResult sendResult) {
                    logger.info("msg {} send result: {}", topic, sendResult);
                }

                public void onException(Throwable e) {
                    logger.info("msg {} send exception: {}", topic, e.getMessage());
                    e.printStackTrace();
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Application: One-way transmission is used for cases requiring moderate reliability, such as log collection
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    public void oneWaySend(String topic, String tag, String msg, int delay) {
        try {
            producer.sendOneway(composeMsg(topic, tag, msg, delay));
            logger.info("oneway send msg {}", topic);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据msg 选择 queue
     * @param topic
     * @param tag
     * @param msg
     * @param delay
     */
    public void orderProducer(String topic, String tag, String msg, int delay) {
        Message message = composeMsg(topic, tag, msg, delay);
        // Send the message
        try {
            SendResult result = producer.send(message, (mqs, msg1, arg) -> {
                return mqs.get(ThreadLocalRandom.current().nextInt(mqs.size()));
            }, msg);
            logger.info("orderly send msg: {}, result: {}", result);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TProducer producer = new TProducer(Constants.producerGroup, Constants.nameServer);
        producer.init();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
//        producer.synSend("synsend", "syn", "synmessage", -2);
//        producer.synSend("synsend", "syn delay", "synmessage", 3);
//        producer.batchSend("batchsend", "batch", Arrays.asList("batchmsg1", "batchmsg2", "batchmsg3", "batchmsg4", "batchmsg5", "batchmsg6", "batchmsg7", "batchmsg8"), 3);
//        producer.asynSend("asynsend", "asyn", "asynmessage", -1);
//        producer.oneWaySend("onewaysend", "oneway", "onewaymessage", -1);
            producer.orderProducer("orderlysend", "orderly", "orderlymessage" + i, -1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
