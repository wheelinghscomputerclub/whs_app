package org.d214.whs.wcc.app;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DailyAnnouncementsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");
		WheelingApp app = new WheelingApp();
		resp.getWriter().println(app.getDailyAnnouncementsJson());
	}
}
