package com.lizhimin.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;


import javax.jms.*;

import java.io.IOException;

/**
* @Description:  使用两种方式接收消息
* @Param:
* @return:
* @Author: 李志敏
* @Date: 2020-02-06
*/
public class ActiveMQDemo2 {

    public static final String ACTIVEMQ_URL ="tcp://10.211.55.9:61616";
    public static final String QUEUE_NAME ="queue01";
    public static void main(String[] args) throws JMSException, IOException {
        //1、创建连接工厂 ,按照给定的URL地址采取默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2、通过连接工厂，获取连接connection并启动访问

            Connection connection = activeMQConnectionFactory.createConnection();
            connection.start();
            //3、创建会话session
            //两个参数：第一个事务，第二个签收
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //4、创建目的地（可以是队列也可以是主题topic）
            Queue queue = session.createQueue(QUEUE_NAME);
            //5、创建消费者
            MessageConsumer messageConsumer = session.createConsumer(queue);

        /**
         * 同步阻塞方式（receive）
         * 订阅者或接收者调用messageConsumer的receive（）接收消息，接收到消息（超时之前）一直阻塞
        while(true){
            TextMessage message = (TextMessage)messageConsumer.receive(300);
            if(message!=null){
                System.out.println("message:"+message.getText());
            }else{
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("接收消息完成");*/

        //使用监听的方式消费消息
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(null!=message&&message instanceof TextMessage){
                    TextMessage textMessage =(TextMessage)message;
                    try {
                        System.out.println("消息接收者："+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();



    }



}
