package com.zbc.Test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

//消费者
public class ReceiverDemo {

	// 默认连接用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// 默认连接密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// 默认连接地址
	private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

	/*			消费者有两种消费方法：：
	1、同步消费。通过调用消费者的receive方法接收消息。receive方法可以一直阻塞到消息到达。
	2、异步消费。为消费者注册一个消息监听器，当消息到达之后，自动调用监听器的onMessage方法。
	   
	 */
	
	//同步消费。
	public void demo1() {
		// 连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
		try {
			// 创建连接
			Connection connection = connectionFactory.createConnection();
			// 启动连接
			connection.start();
			// 创建session，一个发送或接收消息的线程
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// 消息目的地，ActionMQ web控制台创建的队列名‘myQueue’
			Destination destination = session.createQueue("myQueue");
			// 消息消费者
			MessageConsumer consumer = session.createConsumer(destination);
			
			while (true) {
			 System.out.println("-------------------------------------------等待接收消息..... "); 
			 TextMessage message = (TextMessage) consumer.receive(); 
			 if (message !=null) { 
				  System.out.println("接收到消息： " + message.getText()+"\n");  
			  }else {
				  break; 
			  }
			} 
			
			session.close();
			connection.close();
			
		} catch (JMSException  e) {
			e.printStackTrace();
		}

	}
	
	//异步消费。
	public void demo2() {
		// 连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
		try {
			// 创建连接
			Connection connection = connectionFactory.createConnection();
			// 启动连接
			connection.start();
			// 创建session，一个发送或接收消息的线程
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// 消息目的地，ActionMQ web控制台创建的队列名‘myQueue’
			Destination destination = session.createQueue("myQueue");
			// 消息消费者
			MessageConsumer consumer = session.createConsumer(destination);
			
			consumer.setMessageListener(new MessageListener() {
						@Override
						public void onMessage(Message message) {
							try {
								TextMessage textMessage = (TextMessage) message;
								System.out.println("接收到消息： " +textMessage.getText()+"\n");
							} catch (JMSException e) {
								e.printStackTrace();
							}
						}
				});
			
			if (session != null){    			    
				session.close();    		    
			}    		    
			if (connection != null){    			   
				connection.close();    		   
			}    	
			
		} catch (JMSException  e) {
			e.printStackTrace();
		}

	}
}
