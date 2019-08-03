package com.zbc.spring.activemq;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service("mQSender")
public class MQSender {

	 	@Resource(name="jmsTemplate")
	 	private JmsTemplate jmsTemplate;
	 
	 	@Resource(name="topicDestination")
	 	private Destination topicDestination;
	 	
	 	/**
	 	 * 默认消息目的地'myQueue'的发送方法
	 	 */
	    public void sendMsg(String msg){  
	    	Destination destination = jmsTemplate.getDefaultDestination();
	    	System.out.println("----------------------------------向默认queue:"+destination+"发送消息------");
	    	jmsTemplate.send(new MessageCreator() {            
	    		public Message createMessage(Session session) throws JMSException { 
	    			System.out.println("发送消息："+msg+"\n");
	    			return session.createTextMessage(msg);           
	    		}        
	    	});    
	    }
	    

	    /**
	 	 *指定消息目的地'myTopic'的发送方法
	 	 */
	    public void sendMsgToMyTopic(String msg){        
	    	System.out.println("----------------------------------向指定topic:"+topicDestination+"发送消息------");
	    	jmsTemplate.send(topicDestination,new MessageCreator() {            
	    		public Message createMessage(Session session) throws JMSException {  
	    			System.out.println("发送消息："+msg+"\n");
	    			return session.createTextMessage(msg);           
	    		}        
	    	});    
	    }
	    

}
