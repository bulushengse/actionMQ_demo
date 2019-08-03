package com.zbc.spring.activemqListener;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;

public class MQReceiverListenerTopic implements MessageListener{

	
    @Resource(name="jmsTemplate")
    private JmsTemplate jmsTemplate;
    
    /**
     * 实际项目中采用的是这种方式，当项目启动，通过配置一个listener来实现自动开启MQ消息接收
     */
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if (message instanceof TextMessage) {
            try {
                TextMessage txtMsg = (TextMessage) message;
                System.out.println("接收到消息===" + txtMsg.getText()+"\n");
              //实际项目中的接收到的message(通常是JSON字符串)之后，会进行反序列化成对象，做进一步的处理
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }

	}
	

}
