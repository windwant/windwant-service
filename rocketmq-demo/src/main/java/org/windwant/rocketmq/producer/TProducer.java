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
import java.util.Arrays;
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

    /**
     * 初始化producer
     */
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
            //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            message.setDelayTimeLevel(delay);
        }
        return message;
    }

    /**
     *
     * @param topic
     * @param tag
     * @param msg
     * @param delay message delay
     * @param type 1 syn 2 asyn 3 oneway 4 orderly
     */
    public void send(String topic, String tag, String msg, int delay, int type){
        Message message = composeMsg(topic, tag, msg, -1);
        if (delay > 0) {
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(delay);
        }
        switch (type){
            case 1: synSend(message); break;
            case 2: asynSend(message); break;
            case 3: oneWaySend(message); break;
            case 4: orderSend(message); break;
        }
    }

    /**
     *
     * @param topic
     * @param tag
     * @param msg
     * @param type 1 syn 2 asyn 3 oneway 4 orderly
     */
    public void send(String topic, String tag, String msg, int type){
        send(topic, tag, msg, -1, type);
    }

    /**
     * @param topic
     * @param tag
     * @param msg
     */
    public void send(String topic, String tag, String msg){
        send(topic, tag, msg, -1, 1);
    }

    /**
     * use topic as tag
     * @param topic
     * @param msg
     */
    public void send(String topic, String msg){
        send(topic, topic, msg, -1, 1);
    }


    /**
     * Application: Reliable synchronous transmission is used in extensive scenes, such as important notification messages, SMS notification, SMS marketing system, etc..
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    private void synSend(Message message){
        try {
            SendResult result = producer.send(message);
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
    private void asynSend(Message message){
        try {
            producer.send(message, new SendCallback() {
                public void onSuccess(SendResult sendResult) {
                    logger.info("msg {} send result: {}", message, sendResult);
                }

                public void onException(Throwable e) {
                    logger.info("msg {} send exception: {}", message, e.getMessage());
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
    private void oneWaySend(Message message) {
        try {
            producer.sendOneway(message);
            logger.info("oneway send msg {}", message);
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
     */
    private void orderSend(Message message) {
        // Send the message
        try {
            SendResult result = producer.send(message, (mqs, msg1, arg) -> {
                return mqs.get(ThreadLocalRandom.current().nextInt(mqs.size()));
            }, message);
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

    public void shutdown(){
        if(producer != null){
            producer.shutdown();
        }
    }

    public static void main(String[] args) {
        TProducer producer = new TProducer(Constants.producerGroup, Constants.nameServer);
        producer.init();
        for (int i = 0; i < 10; i++) {
            producer.send("synsend", "syn", "synmessage", 1);
            producer.send("synsend", "syn delay", "synmessage", 3, 1);
//            producer.batchSend("batchsend", "batch", Arrays.asList("batchmsg1", "batchmsg2", "batchmsg3", "batchmsg4", "batchmsg5", "batchmsg6", "batchmsg7", "batchmsg8"), 3);
            producer.send("asynsend", "asyn", "asynmessage", 2);
            producer.send("onewaysend", "oneway", "onewaymessage", 3);
            producer.send("orderlysend", "orderly", "orderlymessage" + i, 4);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }
}
