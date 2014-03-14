package org.d214.whs.wcc.app;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

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
		
		resp.getWriter().println(WheelingApp.getUpcomingEvents());
		//resp.getWriter().println(sb.toString());
		//hello world
		//new comment
	}
}
