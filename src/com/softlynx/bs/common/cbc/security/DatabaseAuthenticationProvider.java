package com.softlynx.bs.common.cbc.security;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import oracle.jdbc.pool.OracleDataSource;

public class DatabaseAuthenticationProvider implements AuthenticationProvider {

	private String url;
	private String driverClassName;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

		String password = (String) authentication.getCredentials();

		try {
			OracleDataSource oracleDataSource = new OracleDataSource();
			oracleDataSource.setURL(this.getUrl());
			oracleDataSource.setUser(username);
			oracleDataSource.setPassword(password);

			JdbcTemplate jdbcTemplate = new JdbcTemplate(oracleDataSource);
			String query = "select * from user_privileges where username = ?";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(query, username);

			Iterator<Map<String, Object>> iterator = list.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> map = iterator.next();
				String role = (String) map.get("user_ privilege");
				authorities.add(new SimpleGrantedAuthority(role));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new UsernamePasswordAuthenticationToken(username, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
}
