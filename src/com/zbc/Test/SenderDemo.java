package com.zbc.Test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

//生产者
public class SenderDemo {
	
	
	/**
	 * 1.下载ActiveMQ 去官方网站下载：http://activemq.apache.org/
	 * 
	 * 2.运行ActiveMQ  解压缩apache-activemq-5.15.9-bin.zip，然后双击apache-activemq-5.15.9\bin\activemq.bat运行ActiveMQ程序。
	 *
	 * 3.启动ActiveMQ以后，登陆：http://localhost:8161/admin/，创建一个Queue，命名为myQueue。
	 * 
	 * com.zbc.Test包下  mq的简单使用
	 * com.zbc.spring包下  mq与spring的整合使用
	 */	 
	
	/**
	 * JMS中定义了两种消息模型：点对点（queue）和发布/订阅（topic）。主要区别就是是否能重复消费。
	 * 
	 * queue：消息生产者生产消息发送到queue中，然后消息消费者从queue中取出并且消费消息。
	 * 消息被消费以后，queue中不再有存储，所以此消息不可能被其他消费者接收并消费。
	 * 当消费者不存在时，消息会一直保存，直到有消费消费。
	 * 
	 * topic：消息生产者（发布）将消息发布到topic中，同时有多个消息消费者（订阅）消费。
	 * 当生产者发布消息，不管是否有消费者，都不会保存消息。
	 * 
	 */
	
	 //默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        //连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);

        try {
            //创建连接
            Connection connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session，一个发送或接收消息的线程
            // 第一个参数：是否开启事务。
    		// 第二个参数：当第一个参数为false时，才有意义。
            //1、自动应答    2、手动应答，客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法，jms服务器才会删除消息。
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //消息目的地，ActionMQ web控制台创建的队列名‘myQueue’
            Destination destination = session.createQueue("myQueue");
            //Topic topic = session.createTopic("myTopic");
            
            //消息生产者
            MessageProducer producer = session.createProducer(destination);
            //设置不持久化，实际根据项目决定
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            //发送消息
            for (int i = 0; i < 10; i++) {
                //创建一条文本消息
                TextMessage message = session.createTextMessage("不露声色~~~~~~~~~ " + i );
                //生产者发送消息
                producer.send(message);
                System.out.println("sender发送消息："+"不露声色~~~~~~~~~ " + i) ;
            }

            session.commit();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
