package com.wolfman.distribute.kafka;

import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MyKafkaSubareaConsumer extends ShutdownableThread {

    private final KafkaConsumer<Integer, String> consumer;

    Logger log = LoggerFactory.getLogger(this.getClass());

    private List<ConsumerRecord> buffer=new ArrayList<ConsumerRecord>();

    public MyKafkaSubareaConsumer() {
        super("KafkaConsumerTest",false);
        Properties properties=new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_BROKER_LIST);
        //GroupId 消息所属的分组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"wdd");
        //是否自动提交消息:offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        //自动提交的间隔时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        //设置使用最开始的offset偏移量为当前group.id的最早消息
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //设置心跳时间
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");
        //对key和value设置反序列化对象
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer=new KafkaConsumer<Integer, String>(properties);
        TopicPartition p0=new TopicPartition(KafkaProperties.TOPIC,0);
        this.consumer.assign(Arrays.asList(p0));
    }

    @Override
    public void doWork() {
        ConsumerRecords<Integer,String> records=consumer.poll(1000);
        for(ConsumerRecord record:records){
            System.out.println("["+record.partition()+"]receiver message:" +
                    "["+record.key()+"->"+record.value()+"],offset:"+record.offset()+"");
        }
    }

    public static void main(String[] args) {
        MyKafkaSubareaConsumer consumer=new MyKafkaSubareaConsumer();
        consumer.start();
    }

}
