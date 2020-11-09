package com.m7.kafka;


import java.time.Clock;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 *
 * Title: KafkaProducerTest
 * Description:
 * kafka 生产者demo
 * Version:1.0.0
 * @author 黄潇
 * @date 2020年10月9日
 */
public class KafkaProducerTest implements Runnable {

    private final KafkaProducer<String, String> producer;
    private final String topic;
    public KafkaProducerTest(String topicName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.21.0.32:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("request.required.acks", "1");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        this.producer = new KafkaProducer<String, String>(props);
        this.topic = topicName;
    }

    @Override
    public void run() {
        int messageNo = 1;
        try {
            for(;;) {
                String messageStr =
                        "action=NewWebchat&data=" +
                        "{\"account\":\"N00000002827\",\"sid\":\"55ada3a0-14f8-11eb-85e9-b3537fe351fa\",\"messageId\":\""+"UUID.randomUUID().toString()"+"\"," +
                        "\"createTime\":\"2020-10-27 10:59:01\",\"msgType\":\"newMsg\",\"accessId\":\"00ed78d0-134b-11eb-b2be-bd81b0bdfb26\",\"content\":" +
                        "messageNo"+",\"platform\":\"pc\",\"contentType\":\"text\",\"" +
                        "nickName\":\"mjme1uPnMn\",\"notShow\":\"undefined\",\"showHtml\":false,\"when\":1603767541148,\"ip\":\"111.202.78.82\",\"dealCustomerMsg\":false}";
//                messageStr = "action=NewWebchat&data=" +messageNo;
                //                String messageStr=
//                "@common,N00000003636,10018224," +
//                        UUID.randomUUID().toString()+","+UUID.randomUUID().toString();

//                Future future = producer.send(new ProducerRecord<String, String>(
//                        topic, String.valueOf(Clock.systemDefaultZone().millis()), messageStr));
                Future future = producer.send(new ProducerRecord<String, String>(
                        topic, "55ada3a0-14f8-11eb-85e9-b3537fe351fa", messageStr));

                System.out.println(future.get());
                //生产10条就打印
                if(messageNo%10==0){
                    System.out.println("发送的信息:" + messageStr);
                }
                //生产100条就退出
                if(messageNo%2000==0){
                    System.out.println("成功发送了"+messageNo+"条");
                    break;
                }
                messageNo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    public static void main(String args[]) {
        KafkaProducerTest test = new KafkaProducerTest("im_gateway2cc_test");
        Thread thread = new Thread(test);
        thread.start();
    }
}