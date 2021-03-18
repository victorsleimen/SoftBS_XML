package com.softlynx.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.io.FileReader;
import org.zkoss.io.FileWriter;
import org.zkoss.lang.Library;
import org.zkoss.zk.ui.Executions;

import com.softlynx.bs.domain.usm.login.LoginVO;

public class UtilSoftBS {

	@SuppressWarnings("unused")
	private void createHTMLFile(final Collection<LoginVO> htmlResult, final String fileName,
			final HttpServletRequest request) {

		FileWriter output = null;
		FileReader reader = null;
		String readline = "";
		try {
			String filePath = request.getServletContext().getRealPath("") + "\\repository\\" + fileName + ".html";
			File file = new File(filePath);
			if (!file.createNewFile()) {
				if (file.delete()) {
					file = new File(filePath);
					file.createNewFile();
				}
			}

			System.out.println(file);
			output = new FileWriter(file, filePath);

			BufferedWriter writer = new BufferedWriter(output);
			StringBuffer sb = new StringBuffer();
			Iterator<LoginVO> it = htmlResult.iterator();
			LoginVO userVO = null;
			sb.append(
					"<html><body><table id='myProductTbl' width='500px'  border ='1' cellspacing=0 cellpadding=0 style='border-collapse:collapse'>");
			sb.append("<thead><tr><th>product Id</th> <th> Product Name</th> <th> Price </th></tr></thead><tbody>");
			while (it.hasNext()) {
				userVO = it.next();
				sb.append("<tr><td>").append(userVO.getId()).append("</td>").append("<td>")
						.append(userVO.getName()).append("</td>");
				sb.append("<td>").append(userVO.getPassword()).append("</td>").append("</tr>").append("\n");
			}
			sb.append("</tbody></table></body></html>");
			System.out.println(sb.toString());
			output.write(sb.toString());
			output.close();

			reader = new FileReader(file, filePath);
			BufferedReader bufferedReader = new BufferedReader(reader);

			sb = new StringBuffer();
			while ((readline = bufferedReader.readLine()) != null) {
				sb.append(readline).append("\n");
			}

			bufferedReader.close();
			System.out.println("read file=" + sb.toString());

		} catch (IOException io) {
			try {
				if (output != null) {
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			io.printStackTrace();
		}
	}

	/**
	 * Dynamically Switching Themes Using Library Property
	 * @param themName
	 */
	public static void switchTheme(String themName) {
		Library.setProperty("org.zkoss.theme.preferred", themName);
		Executions.sendRedirect("");
	}

}
