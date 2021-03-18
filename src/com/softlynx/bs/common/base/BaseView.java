package com.softlynx.bs.common.base;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Window;

public abstract class BaseView extends BaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Base Window Reference
	 */
	private BaseWindow baseWindow;

	protected BaseWindow getBaseWindow() {
		return baseWindow;
	}

	/**
	 * ZK doAfterCompose Method extends GenericForwardComposer class
	 * 
	 * @param Component
	 * @throws Exception
	 */
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);
		baseWindow = new BaseWindow((Window) comp);
		doCompose();
	}
}