package com.softlynx.bs.domain.usm.login;

import org.apache.ibatis.type.Alias;

import com.softlynx.bs.common.base.BaseVO;

@Alias("loginVO")
public class LoginVO extends BaseVO {

	private static final long serialVersionUID = -7097788381063651267L;
	private int id;
	private String name;
	private String password;

	public LoginVO(int int1, String string, String string2) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}