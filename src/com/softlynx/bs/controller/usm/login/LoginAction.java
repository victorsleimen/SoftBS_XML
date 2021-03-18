package com.softlynx.bs.controller.usm.login;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;

import com.softlynx.bs.common.base.BaseAction;
import com.softlynx.bs.domain.usm.login.UserContextVO;
import com.softlynx.bs.domain.usm.login.sc.LoginSC;
import com.softlynx.bs.services.usm.login.LoginBO;
import com.softlynx.util.UtilBase64;

public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@WireVariable
	private LoginBO loginBO;

	@Wire
	private Textbox userNameBox;
	@Wire
	private Textbox passwordBox;
	@Wire
	private Textbox domainBox;

	/**
	 * In This Case I will Not Use executeRequest Since the User is not yet Logged In
	 * @return
	 */
	public ModelAndView executeRequest(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[LoginAction] - executeRequest()..........................");
		return null;
	};

	/**
	 * Function launched to initialize component
	 */
	public void doCompose() {
		System.out.println("[LoginAction] - doCompose()..........................");
	};

	/**
	 * Function used to open the page In This Case I will Not Use executeRequest
	 * Since the User is not yet Logged In
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @throws Exception
	 * @return ModelAndView
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("[LoginAction] - handleRequest()..........................");
		ModelAndView mav = new ModelAndView("login/Login.zul");
		try {
			// Retreiving the fail login parameter
			String errMsg = "";
			if (StringUtils.trimToEmpty(request.getParameter("fail")).length() > 0) {
				errMsg = "Invalid Credentials";
			}

			// Setting the calendar year for copyright label
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);

			mav.addObject("currentYear", currentYear);
			mav.addObject("errMsg", errMsg);
		} catch (Exception ex) {
			System.out.println("\n\n==Ex=" + ex.getMessage());
		}
		return mav;
	}

	/**
	 * Function used to LogIn to the application when Pressing Enter
	 * @param void
	 * @return void
	 * @throws Exception
	 */
	@Listen("onOK=#passwordBox")
	public void checkOkButton() {
		System.out.println("[LoginAction] - checkOkButton()..........................");
		this.checkButton();
	}
	
	/**
	 * Function used to LogIn to the application
	 * @param void
	 * @return void
	 * @throws Exception
	 */
	@Listen("onClick=#checkButton")
	public void checkButton() {
		
		//System.out.println("[LoginAction] - checkButton()..........................");
		String userCode = "";
		String userLang = "";
		String sysLang = "1";
		String userName = userNameBox.getValue();
		String password = passwordBox.getValue();
		String forward = "/popupLogin.zul?fail=true";

		try {
			LoginSC theSC = new LoginSC();
			theSC.setLoginName(userName);
			theSC.setLoginPassword(UtilBase64.base64Encode(password));
			Collection<HashMap<String, String>> userInfo = loginBO.getUserInfo(theSC);
			System.out.println("[LoginAction] - userInfo.size() ======================== "+userInfo.size());
		
			if (userInfo != null && userInfo.size() > 0) {
			
				HashMap<String, String> theInfo = (HashMap<String, String>) userInfo.iterator().next();

				if (StringUtils.trimToEmpty(theInfo.get("lanCode")).length() > 0) {
					userLang = new String(theInfo.get("lanCode").toString());
				}
				if (StringUtils.trimToEmpty(theInfo.get("sysLanguage")).length() > 0) {
					sysLang = theInfo.get("sysLanguage").toString();
				}
				if (StringUtils.trimToEmpty(theInfo.get("usrCode")).length() > 0) {
					userCode = theInfo.get("usrCode").toString();
				}
				
				theSC = new LoginSC();
				theSC.setUserCode(userCode);

				// filling the number formats of the user that should be read from database when ready
				//HashMap<String, String> theNbrFormatHash = loginBO.getNbrFormatForUser(theSC);
				
				// Saving User Info
				UserContextVO userContextVO = new UserContextVO();
				userContextVO.setUserCode(userCode);
				userContextVO.setUserName(userName);

				// Saving necessary information using the http session
				HttpSession hses = (HttpSession) Sessions.getCurrent().getNativeSession();
				hses.setAttribute("user_context", userContextVO);
				hses.setAttribute("userCode", userCode);
				hses.setAttribute("userLang", userLang);
				hses.setAttribute("systemLang", sysLang);
				hses.setAttribute("userName", userName);
				hses.setAttribute("userNbFormats", null);
				hses.setAttribute("userType", null);
			
				// Forwarding to the main page
				forward = "desktopApps.zul";
			}
			
		} catch (Exception ex) {
			System.out.println("\n\n\n\n\n\n\n=ex=" + ex.getMessage());
		}
		Executions.createComponents("/WEB-INF/views/usm/desktop/desktopApps.zul", null, null); 
		/*
		Executions.sendRedirect(forward);
		*/
	}
}