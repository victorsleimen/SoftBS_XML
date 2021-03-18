package com.softlynx.bs.common.base;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zkmax.ui.util.DesktopRecycle;

public class SoftDesktopRecycle extends DesktopRecycle {
	
	protected boolean shallCache(Desktop desktop, String path, int cause) {
		return path.startsWith("/popupLogin");
	}

	protected boolean shallReuse(Desktop desktop, String path, int secElapsed) {
		return secElapsed >= 300;
	}
}