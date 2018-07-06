package com.wolfman.distribute.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.Properties;

public class MypartitionProducer {

    private final KafkaProducer<Integer, String> producer;


    public MypartitionProducer() {
        Properties props = new Properties();

        props.put("bootstrap.servers",KafkaProperties.KAFKA_BROKER_LIST);
        props.put("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //设置自定义消息发送分区
        props.put("partitioner.class","com.wolfman.distribute.kafka.MyPartition");
        props.put("client.id","producerDemo");
        this.producer = new KafkaProducer<Integer, String>(props);
    }

    public void sendMsg(){
        producer.send(new ProducerRecord<Integer, String>(KafkaProperties.TOPIC, 1, "message"),
                new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                System.out.println("message send to:["+recordMetadata.partition()+"],offset:["+recordMetadata.offset()+"]");
            }
        });
    }

    public static void main(String[] args) throws IOException {
        MypartitionProducer producer=new MypartitionProducer();
        producer.sendMsg();
        System.in.read();
    }



}
