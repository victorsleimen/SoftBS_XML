package com.softlynx.bs.common.cbc.security;

import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.au.AuService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;

public class SoftBlockService implements AuService {

	@Override
	public boolean service(AuRequest request, boolean everError) {
		final Component comp = request.getComponent();
		return (comp instanceof Button) && "onClick".equals(request.getCommand());
	}
}