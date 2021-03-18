package com.softlynx.bs.common.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.softlynx.bs.domain.usm.login.LoginVO;

public class SoftMessageConverter implements MessageConverter {

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		LoginVO loginVO = (LoginVO) object;
		MapMessage message = session.createMapMessage();
		message.setInt("userCode", loginVO.getId());
		message.setString("loginName", loginVO.getName());
		message.setString("password", loginVO.getPassword());
		return message;
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		MapMessage mapMessage = (MapMessage) message;
		LoginVO person = new LoginVO (mapMessage.getInt("userCode"), mapMessage.getString("loginName"), mapMessage.getString("password"));
		return person;
	}
}