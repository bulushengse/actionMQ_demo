package com.zbc.spring.activemq;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service("mQReceiver")
public class MQReceiver{

    @Resource(name="jmsTemplate")
    private JmsTemplate jmsTemplate;
    
	@Resource(name="topicDestination")
 	private Destination topicDestination;
	
    /**
     * 不建议主动调用JmsTemplate的receive()调用，因为在JmsTemplate上的所有调用都是同步的。
     * 这意味着调用线程需要被阻塞，直到方法返回，这对性能影响很大
     */
    public String receive() {
    	Destination destination = jmsTemplate.getDefaultDestination();//jmsTemplate默认的消息队列‘myQueue’
        TextMessage tm = (TextMessage) jmsTemplate.receive(destination);
       // TextMessage tm2 = (TextMessage) jmsTemplate.receive(topicDestination);
        String text = "";
        try {
        	text = tm.getText();
            System.out.println("从队列" + destination.toString() + "收到了消息：\t"+ text+"\n");
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return text;
    }
	
	

	
}
