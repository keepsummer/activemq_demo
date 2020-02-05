package com.lizhimin.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import sun.jvm.hotspot.debugger.win32.coff.TestDebugInfo;

import javax.jms.*;

public class ActiveMQDemo2 {

    public static final String ACTIVEMQ_URL ="tcp://10.211.55.9:61616";
    public static final String QUEUE_NAME ="queue01";
    public static void main(String[] args) throws JMSException {
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
            System.out.println("接收消息完成");

    }
}
