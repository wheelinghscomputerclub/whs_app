package com.ryan.examples.wcc;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
		
		resp.getWriter().println(getUpcomingEvents());
		//resp.getWriter().println(sb.toString());
	}
	
	private String getUpcomingEvents() {
		StringBuffer jsonResponse = new StringBuffer("[");
		
		Document doc;
		try {
			doc = Jsoup.connect("http://whs.d214.org/").get();
			Elements eventItems = doc.select(".eventitem .descr .bold .bold");
			
			for (Iterator iterator = eventItems.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				String elementText = element.text();
				elementText = elementText.replace('"', '\'');
				jsonResponse.append(String.format("\"%s\",", elementText));
			}
			
			jsonResponse.deleteCharAt(jsonResponse.length() - 1);
		} catch (IOException e) {
			jsonResponse.append("Error: Could not parse the whs site");
		}
		
		jsonResponse.append("]");
		return jsonResponse.toString();
	}
}
