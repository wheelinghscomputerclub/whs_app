package org.d214.whs.wcc.app;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class WCC_PrototypeServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");
		
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("\"one\",");
		sb.append("\"two\",");
		sb.append("\"three\",");
		sb.append("\"four\",");
		sb.append("\"five\"");
		sb.append("]");
		
		WheelingApp app = new WheelingApp();
		
		resp.getWriter().println(app.getUpcomingEventsJson());
		//resp.getWriter().println(sb.toString());
		//hello world
		//new comment
	}
}
