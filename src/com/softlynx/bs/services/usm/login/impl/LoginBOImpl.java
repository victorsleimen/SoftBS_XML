package com.softlynx.bs.services.usm.login.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softlynx.bs.common.base.BaseBO;
import com.softlynx.bs.domain.usm.login.NumberDateSettingVO;
import com.softlynx.bs.domain.usm.login.sc.LoginSC;
import com.softlynx.bs.mappers.usm.login.LoginMapper;
import com.softlynx.bs.services.usm.login.LoginBO;

@Service("loginBO")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
@Transactional
public class LoginBOImpl extends BaseBO implements LoginBO, Serializable {
	
	private static final long serialVersionUID = 4477536169895449679L;
	@Autowired
	private LoginMapper loginMapper;

	/**
	 * Gets some of the User details like user code and user language given its user name.
	 * @param empUsrLogin String of the userSC
	 * @return Collection
	 * @throws SoftSolException DAO exception
	 */
	@Override
	public Collection<HashMap<String, String>> getUserInfo(LoginSC criteria) throws Exception {
		System.out.println("[LoginBOImpl] - getUserInfo()...........................");
		return loginMapper.getUserInfo(criteria);
	}

	/**
	 * Returns the Number Formats assigned to a certain User. If user has no
	 * predefined number formats, then the default Number Formats will be returned.
	 * @param empId String
	 * @return HashMap
	 * @throws SoftSolException DAO exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> getNbrFormatForUser(LoginSC criteria) throws Exception {
		System.out.println("[LoginBOImpl] - getNbrFormatForSetting()...........................");
		String settingId = null;
		if (!NumberDateSettingVO.settingByUserHash.containsKey(settingId)) {
			NumberDateSettingVO.settingByUserHash.put(settingId, loginMapper.getNbrFormatForSetting(settingId));
		}
		return new HashMap<String, String>((HashMap<String, String>) NumberDateSettingVO.settingByUserHash.get(settingId));
	}
}