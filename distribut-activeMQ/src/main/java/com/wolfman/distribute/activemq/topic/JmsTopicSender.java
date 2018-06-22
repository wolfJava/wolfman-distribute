package com.wolfman.distribute.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsTopicSender {

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://39.107.31.208:61616");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("bangpai");
            MessageProducer producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage("新来了两个人");
            producer.send(textMessage);
            session.commit();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
