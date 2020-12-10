package com.zbc.spring.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zbc.spring.activemq.MQReceiver;
import com.zbc.spring.activemq.MQSender;

@Controller
@RequestMapping(value="/mqTest")
public class MQTestController {

	@Resource(name="mQSender")
 	private MQSender mqSender;
	
	@Resource(name="mQReceiver")
 	private MQReceiver mqReceiver;
	

	/**
	 * mq发送
	 */
	@RequestMapping(value="/sendMsgTest",method=RequestMethod.POST)
	public ModelAndView  sendMsgTest() {
		ModelAndView mv = new ModelAndView();
		mqSender.sendMsg("不露声色~~~~~~~~~~~~~~~~~~~myqueue"+Math.random());
		mv.addObject("result","消息已发送~~~~~~~~~~~~~~~");
		mv.setViewName("result");
		return mv;
    }
	
	/**
	 * mq发送
	 */
	@RequestMapping(value="/sendMsgToMyTopic",method=RequestMethod.POST)
	public ModelAndView  sendMsgToMyTopic() {
		ModelAndView mv = new ModelAndView();
		mqSender.sendMsgToMyTopic("不露声色~~~~~~~~~~~~~~~~~~~mytopic"+Math.random());
		mv.addObject("result","消息已发送~~~~~~~~~~~~~~~");
		mv.setViewName("result");
		return mv;
    }
	
	/**
	 * mq接收，实际项目不建议主动调用，应项目启动自动开始mq接收消息
	 */
	@RequestMapping(value="/receiveMsgTest",method=RequestMethod.POST)
	public ModelAndView receiveMsgTest() {
		ModelAndView mv = new ModelAndView();
		String result = mqReceiver.receive();
		if(!"".equals(result) && result!=null) {
			mv.addObject("result","接收到消息："+result);
		}else {
			mv.addObject("result","未接收到消息~~~~~~~");
		}
		mv.setViewName("result");
		return mv;
    }
}
