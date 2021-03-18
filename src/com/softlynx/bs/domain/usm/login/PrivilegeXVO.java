package com.softlynx.bs.domain.usm.login;

import com.softlynx.bs.common.base.BaseVO;

public class PrivilegeXVO extends BaseVO {

	private static final long serialVersionUID = 6269805445105737484L;
	private String roleType;
	private String roleTypeName;
	private String userType;
	private String roleId;

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}