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
		
		resp.getWriter().println(getUpcomingEvents());
		//resp.getWriter().println(sb.toString());
		//hello world
		//new comment
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
    
    private String getDailyAnnouncements()
    {
        StringBuffer jsonResponse = new StringBuffer("[");
        
        try
        {
            Document doc = Jsoup.connect("http://whs.d214.org/student_resources/daily_announcements.aspx").get();
            Element table = doc.select(".bdywrpr .corwrpr-2clm-lr .cormain-2clm-lr table").first(); //still do
            
            Iterator<Element> ite = table.select("td").iterator();
            while (ite.hasNext())
            {
            	ite.next();
            	String s = ite.next().text();
            	jsonResponse.append(s);
            	System.out.println(s);
            }
            
            jsonResponse.deleteCharAt(jsonResponse.length() - 1);
        }
        catch (Exception exc)
        {
            jsonResponse.append("There was an error retrieving the daily announcements.");
        }
        
        jsonResponse.append("]");
        return jsonResponse.toString();
    }
    
    private String getTopNews()
    {
        StringBuffer jsonResponse = new StringBuffer("[");
        
        try
        {
            Document doc = Jsoup.connect("http://whs.d214.org/").get();
            //Elements table = doc.select("div#CT_Main_0_cache_pnlModule");
            Element table = doc.select(".bdywrpr .corwrpr-3clm .wrpr2clm .cormain-hm #CT_Main_0_cache_pnlModule").first();
            List<Node> childNodes = table.childNodes();
            //Iterator<Element> ite = table.select("td").iterator();
            Iterator<Node> ite = childNodes.iterator();
            
            while (ite.hasNext())
            {
            	//ite.next();
            	Node n = ite.next();
            	String s = n.nodeName();
            	String[] one;
            	String[] two;
            	jsonResponse.append(s);
            	System.out.println(s);
            	System.out.println("****");
            }
            
            jsonResponse.deleteCharAt(jsonResponse.length() - 1);
        }
        catch (Exception exc)
        {
            jsonResponse.append("There was an error retrieving the daily announcements.");
        }
        
        jsonResponse.append("]");
        return jsonResponse.toString();
    }
}
