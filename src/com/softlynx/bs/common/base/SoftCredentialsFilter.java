package com.softlynx.bs.common.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SoftCredentialsFilter implements Filter {

	private UserCredentialsDataSourceAdapter dataSourceAdapter;

	public UserCredentialsDataSourceAdapter getDataSourceAdapter() {
		return dataSourceAdapter;
	}

	public void setDataSourceAdapter(UserCredentialsDataSourceAdapter dataSourceAdapter) {
		this.dataSourceAdapter = dataSourceAdapter;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();

		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			Object credentials = authentication.getCredentials();
			dataSourceAdapter.setCredentialsForCurrentThread(principal.toString(), credentials.toString());
		}
		try {
			chain.doFilter(request, response);
		} finally {
			dataSourceAdapter.removeCredentialsFromCurrentThread();
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}