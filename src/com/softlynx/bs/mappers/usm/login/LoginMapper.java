package com.softlynx.bs.mappers.usm.login;

import java.util.Collection;
import java.util.HashMap;

import com.softlynx.bs.domain.usm.login.sc.LoginSC;

public interface LoginMapper {

	Collection<HashMap<String, String>> getUserInfo(LoginSC criteria) throws Exception;
	HashMap<String, String> getNbrFormatForSetting(String settingId) throws Exception;
}