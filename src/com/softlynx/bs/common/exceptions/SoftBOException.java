package com.softlynx.bs.common.exceptions;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SoftBOException {
	
	private static final Logger logger = LoggerFactory.getLogger(SoftBOException.class);

	@ExceptionHandler(SQLException.class)
	public String handleBOException(HttpServletRequest request, Exception ex) {
		logger.info("BOException Occured:: URL=" + request.getRequestURL());
		return "database_error";
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
	@ExceptionHandler(IOException.class)
	public void handleIOException() {
		logger.error("BOException handler executed");
	}
}