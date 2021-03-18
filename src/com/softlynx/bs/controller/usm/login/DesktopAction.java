package com.softlynx.bs.controller.usm.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Center;
import org.zkoss.zul.Include;

import com.softlynx.bs.common.base.BaseAction;
import com.softlynx.bs.domain.usm.login.UserContextVO;

public class DesktopAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Function used to open the page
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @throws Exception
	 * @return ModelAndView
	 */
	public ModelAndView executeRequest(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("[DesktopAction] - executeRequest()...............................");
		ModelAndView mav = new ModelAndView("login/Desktop.zul");
		try {
			HttpSession session = (HttpSession) request.getSession();
			if (session != null) {
				UserContextVO userContextVO = null;

				if ((userContextVO = UserContextVO.getUserContext(request)) != null) {
					mav.addObject("user_context", userContextVO);
					mav.addObject("loggedDate", getloggedDate(request.getLocale()));
				}
				/*
				 * if ((theVO = (ChooseAppVO) session.getAttribute("defaultAppUsrVO")) != null)
				 * { mav.addObject("clientParamId",
				 * StringUtils.trimToEmpty(theVO.getClientId()));
				 * mav.addObject("clientParamName",
				 * StringUtils.trimToEmpty(theVO.getClientName()));
				 * mav.addObject("applicationParamId",
				 * StringUtils.trimToEmpty(theVO.getApplicationId()));
				 * mav.addObject("applicationParamName",
				 * StringUtils.trimToEmpty(theVO.getApplicationName()));
				 * mav.addObject("versionParamId",
				 * StringUtils.trimToEmpty(theVO.getVersionId()));
				 * mav.addObject("versionParamName",
				 * StringUtils.trimToEmpty(theVO.getVersionName())); }
				 */
			}
		} catch (Exception ex) {
			System.out.println("\n\n\n\n==Ex" + ex.getMessage());
		}
		return mav;
	}

	public void doCompose() {

		System.out.println("[DesktopAction] - doCompose()...............................");
		HttpServletRequest reuest = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		String selectedMenuPath = UserContextVO.getUserContext(reuest).getSelectedMenuPath();
		if (StringUtils.trimToEmpty(selectedMenuPath).length() != 0) {
			loadMiddleLayout(selectedMenuPath);
		}

	}

	/**
	 * Load Center Layout When Choosing Menu Item
	 */
	private void loadMiddleLayout(String path) {

		Center centerLayout = (Center) super.getBaseWindow().getCenterlayout("centerLayout");
		centerLayout.getChildren().clear();
		Include incluseWin = new Include();
		incluseWin.setSrc(path);
		centerLayout.appendChild(incluseWin);
	}

	/**
	 * Function used to LogIn to the application
	 * @param void
	 * @return void
	 * @throws Exception
	 */
	@Listen("onClick=#logoutButton")
	public void logoutButton() {

		System.out.println("[DesktopAction] - logoutButton()...............................");
		String forward = "/loginAction.do";
		try {
			HttpSession hses = (HttpSession) Sessions.getCurrent().getNativeSession();
			removeSessionVariables(hses);
			Executions.sendRedirect(forward);
		} catch (Exception ex) {
			System.out.println("\n\n\n===Ex=" + ex.getMessage());
		}
	}

	/**
	 * Function used to remove and clean all session attributes
	 * @param HttpSession
	 */
	private void removeSessionVariables(HttpSession hses) {
		hses.removeAttribute("user_context");
		hses.removeAttribute("userCode");
		hses.removeAttribute("userLang");
		hses.removeAttribute("systemLang");
		hses.removeAttribute("userName");
		hses.removeAttribute("userNbFormats");
		hses.removeAttribute("defaultAppUsrVO");
		hses.removeAttribute("qmsUserTypes");
	}
}