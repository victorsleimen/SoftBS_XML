package com.softlynx.util;

import com.softlynx.bs.domain.usm.login.LoginVO;

public interface UtilPropertyInterface {

	Object getProperty(LoginVO userVO, String string);

}