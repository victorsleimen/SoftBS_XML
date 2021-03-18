package com.softlynx.bs.common.cbc.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.softlynx.bs.domain.usm.login.UserContextVO;

public class SecurityHelper {

	/**
	 * Checking the security since not Allowed or Enter all Sections
	 * @param request HttpServletRequest
	 * @param adminTest boolean to Seperate Dev Section From Admin Section
	 * @throws Exception
	 */
	private static boolean checkLogin(HttpServletRequest request) {
		// String clientIp = request.getRemoteAddr();
		// System.out.println("User try to Logged clientIp ["+clientIp+"] at
		// ["+DateUtil.format(new Date(), "dd/MM/yyyy hh:mm:ss")+"]");
		boolean accessGranted = false;

		try {
			Object tmp = UserContextVO.getUserContext(request);
			if (tmp != null) {
				UserContextVO userContext = (UserContextVO) tmp;
				String userCode = (String) request.getSession().getAttribute("userCode");
				if (userContext.getUserCode().equals(userCode)) {
					accessGranted = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessGranted;
	}

	/**
	 * @param request
	 * @return
	 */
	public static Object[] applySecurity(HttpServletRequest request) {

		System.out.println("[SecurityHelper] - applySecurity()......................................");
		Object[] result = new Object[] {Boolean.valueOf(true), null };
		if (!checkLogin(request)) // User Does Not Exist
		{
			result[0] = Boolean.valueOf(false);
			result[1] = new ModelAndView("login/Login.zul");
		}
		return result;
	}
}