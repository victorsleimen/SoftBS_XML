package com.softlynx.bs.common.jms;

import org.springframework.jms.core.JmsTemplate;

public class SoftJmsProducer {

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

	public void sendMessage(final String msg) {
		
		getJmsTemplate().convertAndSend(msg);
		/*
		System.out.println("Producer sends " + msg);
		if (destination == null) {
			jmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(msg);
				}
			});
		} else {
			jmsTemplate.send(destination, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(msg);
				}
			});
		}
		*/
	}
}