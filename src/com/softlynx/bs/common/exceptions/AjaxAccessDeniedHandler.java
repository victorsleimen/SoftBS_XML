package com.softlynx.bs.common.exceptions;

import java.util.Map;

import org.zkoss.spring.security.SecurityUtil;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.GenericInitiator;

public class AjaxAccessDeniedHandler extends GenericInitiator {

	public void doInit(Page page, Map<String, Object> args) throws Exception {

		// when this initiator has been executed that means users encounter access denied problem.
		Execution exec = Executions.getCurrent();

		if (null == SecurityUtil.getAuthentication()) { // unauthenticated user
			exec.sendRedirect("/popupLogin.zul");
		} else {
			// display error's detail
			Executions.createComponents("/accessdenied.zul", null, args);
		}
	}
}