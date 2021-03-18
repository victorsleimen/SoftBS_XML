package com.softlynx.bs.domain.usm.login;

import com.softlynx.bs.common.base.BaseVO;

public class PrivilegeVO extends BaseVO {

	private static final long serialVersionUID = 7859178184418478438L;
	private String settingId;
	private String settingParamName;
	private String settingParamCode;
	private String settingParamFormat;

	public String getSettingId() {
		return settingId;
	}

	public void setSettingId(String settingId) {
		this.settingId = settingId;
	}

	public String getSettingParamName() {
		return settingParamName;
	}

	public void setSettingParamName(String settingParamName) {
		this.settingParamName = settingParamName;
	}

	public String getSettingParamCode() {
		return settingParamCode;
	}

	public void setSettingParamCode(String settingParamCode) {
		this.settingParamCode = settingParamCode;
	}

	public String getSettingParamFormat() {
		return settingParamFormat;
	}

	public void setSettingParamFormat(String settingParamFormat) {
		this.settingParamFormat = settingParamFormat;
	}
}