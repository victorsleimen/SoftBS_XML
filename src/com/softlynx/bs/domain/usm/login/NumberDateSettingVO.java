package com.softlynx.bs.domain.usm.login;

import java.util.HashMap;

import com.softlynx.bs.common.base.BaseVO;

public class NumberDateSettingVO extends BaseVO {

	private static final long serialVersionUID = -6488813808457194369L;

	public static HashMap<String, HashMap<?, ?>> settingByCurrencyHash = new HashMap<String, HashMap<?, ?>>();

	public static HashMap<String, HashMap<?, ?>> settingByUserHash = new HashMap<String, HashMap<?, ?>>();

	public static HashMap<?, ?> getSettingByCurrency(String curId) {
		return (HashMap<?, ?>) settingByCurrencyHash.get(curId);
	}

	public static void setSettingByCurrency(String curId, HashMap<?, ?> settingByCurrency) {
		settingByCurrencyHash.put(curId, settingByCurrency);
	}

	public static HashMap<?, ?> getSettingByUser(String settingId) {
		return (HashMap<?, ?>) settingByUserHash.get(settingId);
	}

	public static void setSettingByUser(String settingId, HashMap<?, ?> settingByUser) {
		settingByUserHash.put(settingId, settingByUser);
	}
}