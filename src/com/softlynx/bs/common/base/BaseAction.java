package com.softlynx.bs.common.base;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import com.softlynx.bs.common.cbc.security.SecurityHelper;
import com.softlynx.bs.domain.usm.login.LoginVO;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class BaseAction extends SelectorComposer<Component> implements Controller {

	private static final long serialVersionUID = 4913955243685593583L;

	// Message Box Icon
	public final String MSG_ICO_INFORMATIVE = Messagebox.INFORMATION;
	public final String MSG_ICO_ERROR = Messagebox.ERROR;
	public final String MSG_ICO_EXCLAMITIVE = Messagebox.EXCLAMATION;

	// Logger
	public final static Logger Log = LoggerFactory.getLogger(BaseAction.class);

	@WireVariable
	private HashMap<String, String> pathMap;

	private BaseWindow baseWindow;

	protected BaseWindow getBaseWindow() {
		return baseWindow;
	}

	/**
	 * ZK doAfterCompose Method extends GenericForwardComposer class
	 * @param Component
	 * @throws Exception
	 */
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		baseWindow = new BaseWindow((Window) comp);
		doCompose();
	}

	/**
	 * Spring MVC Default Controller Method That generate The View <br>
	 * We Apply the Security Login in Order to Secure the redirection the DEV Implement <br>
	 * executeRequest Method if the Authentication success extends GenericForwardComposer class
	 * @param request
	 * @param response
	 * @return ModelAndView instance
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("[BaseAction] - handleRequest()..................");
		// Security Redirection
		Object[] obj = SecurityHelper.applySecurity(request);
		if (!(Boolean) obj[0]) {
			return (ModelAndView) obj[1];
		} else {
			return executeRequest(request, response);
		}
	}

	public abstract ModelAndView executeRequest(HttpServletRequest request, HttpServletResponse response);

	public abstract void doCompose();

	/**
	 * Get Tree Component
	 * @param fieldname
	 * @return
	 */
	public Tree getTree(String fieldname) {
		return baseWindow.getTree(fieldname);
	}

	public void showModalDialog(String Uri, Map<?, ?> Features, Map<?, ?> parameters) {
		
		// modal real path
		String realPath = pathMap.get(Uri);

		// features
		String width = (String) Features.get("width");
		String height = (String) Features.get("height");
		String title = (String) Features.get("title");

		Window newModalWindow = (Window) Executions.createComponents(realPath, (Component) baseWindow, parameters);

		if (width != null)
			newModalWindow.setWidth(width);
		if (height != null)
			newModalWindow.setHeight(height);
		if (title != null)
			newModalWindow.setTitle(title);

		newModalWindow.setBorder("normal");
		newModalWindow.setClosable(true);
		newModalWindow.doModal();
	}

	public int getUserCode() {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication == null) {
			return -1;
		}
		LoginVO loginVO = (LoginVO) authentication.getPrincipal();
		return loginVO.getId();
	}

	public void handleException(Exception e, String exceptionType) {
		
		if ((MSG_ICO_INFORMATIVE).equals(exceptionType)) {
			Messagebox.show(e.getMessage(), "Suite Warning", Messagebox.OK, MSG_ICO_INFORMATIVE, null);
		} else if ((MSG_ICO_EXCLAMITIVE).equals(exceptionType)) {
			Messagebox.show(e.getMessage(), "Suite Warning", Messagebox.OK, MSG_ICO_EXCLAMITIVE, null);
		} else if ((MSG_ICO_ERROR).equals(exceptionType)) {
			Messagebox.show(e.getMessage(), "Suite Warning", Messagebox.OK, MSG_ICO_ERROR, null);
		} else {
			Messagebox.show(e.getMessage());
		}
		Log.error(e.getMessage());
	}
	
	public static String getloggedDate(Locale locale) {
		
		// String[] weekDays = new DateFormatSymbols(locale).getWeekdays();
		String[] monThYear = new DateFormatSymbols(locale).getMonths();
		
		/*
		System.out.println("weekDays"+weekDays);
		System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
		System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) ;
		for(String str : weekDays) { 
			System.out.println(str);
		}
		*/

		Calendar logDate = Calendar.getInstance();
		// String dayOfWeekStr = weekDays[logDate.get(Calendar.DAY_OF_WEEK)];
		String monThYearkStr = monThYear[logDate.get(Calendar.MONTH)];
		String result = " "/* dayOfWeekStr.concat(" ") */
				.concat(monThYearkStr).concat(" ").concat(String.valueOf(logDate.get(Calendar.DAY_OF_MONTH)))
				.concat(", ").concat(String.valueOf(logDate.get(Calendar.YEAR)));

		return result;
	}
}