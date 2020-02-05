package com.lizhimin.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQDemo {

    public static final String ACTIVEMQ_URL ="tcp://10.211.55.9:61616";
    public static final String QUEUE_NAME ="queue01";

    public static void main(String[] args){
        //1、创建连接工厂 ,按照给定的URL地址采取默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2、通过连接工厂，获取连接connection并启动访问
        try {
            Connection connection = activeMQConnectionFactory.createConnection();
            connection.start();
        //3、创建会话session
            //两个参数：第一个事务，第二个签收
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4、创建目的地（可以是队列也可以是主题topic）
            Queue queue = session.createQueue(QUEUE_NAME);
        //5、创建消息的生产者
            MessageProducer messageProducer = session.createProducer(queue);
        //6、通过使用messageProducer生产3条消息发送到MQ的队列中
            for(int i=1;i<=3;i++){
                //7、创建消息
                TextMessage textMessage = session.createTextMessage("msg" + i);
                //8、通过messageProducer发送给MQ
                messageProducer.send(textMessage);
            }
            messageProducer.close();
            session.close();
            connection.close();


        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("消息发送到MQ");

    }
}
