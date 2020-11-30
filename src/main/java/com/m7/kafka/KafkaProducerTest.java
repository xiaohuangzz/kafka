package com.m7.kafka;


import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

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
    private String str;
    public KafkaProducerTest(String topicName,String str) {
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
        this.str= str;
    }

    @Override
    public void run() {
        int messageNo = 1;
        try {

            for(;;) {
//                String str1 = RandomUtil.generateRandomString(28);
//                str1 = "oFfXMvqRJ5DgyExa9gqti85fB7CI";
                String messageStr =
                        "action=NewWebchat&data=" +
                        "{\"account\":\"N00000002827\",\"sid\":\""+str +"\",\"messageId\":\""+"UUID.randomUUID().toString()"+"\"," +
                        "\"createTime\":\"2020-10-27 10:59:01\",\"msgType\":\"newMsg\",\"accessId\":\"00ed78d0-134b-11eb-b2be-bd81b0bdfb26\",\"content\":" +
                        "\"messageNo\""+",\"platform\":\"pc\",\"contentType\":\"text\",\"" +
                        "nickName\":\"mjme1uPnMn\",\"notShow\":\"undefined\",\"showHtml\":false,\"when\":1603767541148,\"ip\":\"111.202.78.82\",\"dealCustomerMsg\":false}";
//                messageStr = "action=NewWebchat&data=" +messageNo;
                //                String messageStr=
//                "@common,N00000003636,10018224," +
//                        UUID.randomUUID().toString()+","+UUID.randomUUID().toString();

//                Future future = producer.send(new ProducerRecord<String, String>(
//                        topic, String.valueOf(Clock.systemDefaultZone().millis()), messageStr));
//                String s = "oFfXMvqRJ5DgyExa9gqti85fB7CI";
                Date date = new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
                String time = sdf.format(date);
                 messageStr = "action=NewWebchat&data={\"account\":\"N00000002714\",\"sid\":\"" + this.str+"\",\"messageId\":\"c1ef2c80-2274-11eb-904a-0545745df73d\"," +
                         "\"createTime\":\"" +time + "\",\"msgType\":\"newMsg\",\"accessId\":\"wx77104d76233ae5ae\",\"content\":\"https%253A%252F%252Ffs-im-kefu.7moor.com%252Fc1ccd770-2274-11eb-9007-0bf4f946a7d7.mp3\"," +
                         "\"platform\":\"weixin\",\"contentType\":\"voice\",\"nickName\":\""+UUID.randomUUID().toString() +"\",\"showHtml\":false,\"when\":1604917043017,\"dealCustomerMsg\":false,\"isNewVisitor\":false}";
                Future future = producer.send(new ProducerRecord<String, String>(
                        topic, this.str, messageStr));

                System.out.println(future.get());
                //生产10条就打印
                if(messageNo%10==0){
                    System.out.println("发送的信息:" + messageStr);
                }
                //生产100条就退出
                if(messageNo%100==0){
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
        ProduceThreadPool produceThreadPool = new ProduceThreadPool();
        ThreadPoolExecutor threadPoolExecutor = produceThreadPool.threadPoolExecutor();
        for (int i = 0;i<10;i++){
            String str1 = RandomUtil.generateRandomString(28);
            KafkaProducerTest test = new KafkaProducerTest("im_gateway2cc_eight",str1);
            threadPoolExecutor.submit(test);
        }
//        Thread thread = new Thread(test);
//        thread.start();
    }
}