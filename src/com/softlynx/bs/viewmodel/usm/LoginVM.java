package com.softlynx.bs.viewmodel.usm;

import java.io.Serializable;

import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.softlynx.bs.services.usm.login.LoginBO;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class LoginVM implements Serializable {

	private static final long serialVersionUID = -1674702663409645644L;
	
	@WireVariable
    LoginBO loginBO;
}
