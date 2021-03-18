package com.softlynx.bs.domain.usm.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.softlynx.bs.common.base.BaseVO;

@Component
@SessionAttributes("userContextVO")
public class UserContextVO extends BaseVO {

	private static final long serialVersionUID = 7933044140226375244L;
	private String userCode;
	private String userName;
	// Hold the Path of the Opened Menu
	private String selectedMenuPath;

	public static UserContextVO getUserContext(HttpServletRequest request) {
		return (UserContextVO) request.getSession().getAttribute("user_context");
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSelectedMenuPath() {
		return selectedMenuPath;
	}

	public void setSelectedMenuPath(String selectedMenuPath) {
		this.selectedMenuPath = selectedMenuPath;
	}
}