package com.m7.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.kafka.common.serialization.StringDeserializer;

/**
     *
     * Title: KafkaConsumerTest
     * Description:
     *  kafka消费者 demo
     * Version:1.0.0
     * @author haungxiao
     * @date 2020年10月9日
     */
    public class KafkaConsumerTest implements Runnable {

        private final KafkaConsumer<String, String> consumer;
        private ConsumerRecords<String, String> msgList;
        private final String topic;
        private static final String GROUPID = "im_assign_chat_six";

        public KafkaConsumerTest(String topicName) {
            Properties props = new Properties();
            props.put("bootstrap.servers","172.21.0.32:9092");
            props.put("group.id", GROUPID);
            props.put("enable.auto.commit", "false");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("auto.offset.reset", "earliest");
            props.put("key.deserializer", StringDeserializer.class.getName());
            props.put("value.deserializer", StringDeserializer.class.getName());
            this.consumer = new KafkaConsumer<String, String>(props);
            this.topic = topicName;
            this.consumer.subscribe(Arrays.asList(topic));
        }

        @Override
        public void run() {
            int messageNo = 1;
            System.out.println("---------开始消费---------");
            try {
                for (;;) {
                    msgList = consumer.poll(1000);
                    if(null!=msgList&&msgList.count()>0){
                        for (ConsumerRecord<String, String> record : msgList) {
                            //消费100条就打印 ,但打印的数据不一定是这个规律的
//                            if(messageNo%100==0){
                                System.out.println(messageNo+"=======receive: key = " + record.key() + ", value = " + record.value()+" offset==="+record.offset());
//                            }
                            //当消费了1000条就退出
                            if(messageNo%1000==0){
                                break;
                            }
                            messageNo++;
                        }
                    }else{
                        Thread.sleep(100);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                consumer.close();
            }
        }
        public static void main(String args[]) {
            ProduceThreadPool produceThreadPool = new ProduceThreadPool();
            ThreadPoolExecutor threadPoolExecutor = produceThreadPool.threadPoolExecutor();
            KafkaConsumerTest test1 = new KafkaConsumerTest("im_assign_chat_six");
            for (int i = 0;i<10;i++){
                threadPoolExecutor.submit(test1);
            }

//            Thread thread1 = new Thread(test1);
//            thread1.start();
        }
    }

