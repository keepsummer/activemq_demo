package com.lizhimin.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
* @Description:  先订阅再启动生产者，否则发送的消息是一条废消息
* @Param:
* @return:
* @Author: 李志敏
* @Date: 2020-02-06
*/
public class ActiveMQTopticDemo2 {

    public static final String ACTIVEMQ_URL ="tcp://10.211.55.9:61616";
    public static final String TOPIC_NAME ="topic01";
    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("我是3号消费者");
        //1、创建连接工厂 ,按照给定的URL地址采取默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2、通过连接工厂，获取连接connection并启动访问

            Connection connection = activeMQConnectionFactory.createConnection();
            connection.start();
            //3、创建会话session
            //两个参数：第一个事务，第二个签收
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //4、创建目的地（可以是队列也可以是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
            //5、创建消费者
            MessageConsumer messageConsumer = session.createConsumer(topic);


        //使用监听的方式消费消息
        messageConsumer.setMessageListener((message)-> {
                if(null!=message&&message instanceof TextMessage){
                    TextMessage textMessage =(TextMessage)message;
                    try {
                        System.out.println("消息接收者topic："+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();



    }
}
