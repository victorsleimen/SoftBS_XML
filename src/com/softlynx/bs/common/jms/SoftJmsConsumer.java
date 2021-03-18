package com.softlynx.bs.common.jms;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;

public class SoftJmsConsumer {

	private JmsTemplate jmsTemplate;
	/*
	private Destination destination;
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	*/
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}


	public String receiveMessage() throws JMSException {
		
		TextMessage textMessage = (TextMessage) getJmsTemplate().receiveAndConvert();
		/*
		if (destination == null) {
			textMessage = (TextMessage) jmsTemplate.receive();
		} else {
			textMessage = (TextMessage) jmsTemplate.receive(destination);
		}
		*/
		return textMessage.getText();
	}
}