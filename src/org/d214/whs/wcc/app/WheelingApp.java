package org.d214.whs.wcc.app;

import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;


public class WheelingApp
{
	public String getUpcomingEventsJson() {
		Gson gson = new Gson();
		return gson.toJson(getUpcomingEvents());
	}
	
	public String getTopNewsJson()
	{
		Gson gson = new Gson();
		return gson.toJson(getTopNews());
	}
	
	public String getDailyAnnouncementsJson()
	{
		Gson gson = new Gson();
		return gson.toJson(getDailyAnnouncements());
	}
	
    public ArrayList<UpcomingEvent> getUpcomingEvents()
    {
        ArrayList<UpcomingEvent> result = new ArrayList<UpcomingEvent>(4);
        
        String[] dates, titles;
        dates = getDates();
        titles = getTitles();
        
        for (int i = 0; i < 4; i++)
        {
            UpcomingEvent current = new UpcomingEvent(dates[i], titles[i]);
            result.add(current);
        }
        
        return result;
    }
    
    protected String[] getDates()
    {
        try
        {
            Document doc = Jsoup.connect("http://whs.d214.org/").get();
            Elements eventItems = doc.select(".eventitem .time");
            
            String[] result = new String[4];
            int i = 0;
            for (Iterator iterator = eventItems.iterator(); iterator.hasNext();)
            {
                Element element = (Element) iterator.next();
                String elementText = element.text();
                elementText = cleanString(elementText);
                result[i] = elementText;
                i++;
            }
            
            return result;
        }
        catch (Exception exc)
        {
            System.err.println(exc.getLocalizedMessage());
            exc.printStackTrace();
        	return new String[]{"ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR"};
        }
    }
    
    protected String[] getTitles()
    {
        try
        {
            Document doc = Jsoup.connect("http://whs.d214.org/").get();
            Elements eventItems = doc.select(".eventitem .descr .bold .bold");
            
            String[] result = new String[4];
            int i = 0;
            for (Iterator iterator = eventItems.iterator(); iterator.hasNext();)
            {
                Element element = (Element) iterator.next();
                String elementText = element.text();
                elementText = cleanString(elementText);
                result[i] = elementText;
                i++;
            }
            
            return result;
        }
        catch (Exception exc)
        {
        	System.err.println(exc.getLocalizedMessage());
            exc.printStackTrace();
        	return new String[]{"ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR"};
        }
    }
    
    protected Element getAnnouncementsTable() throws IOException {
    	Document doc = Jsoup.connect("http://whs.d214.org/student_resources/daily_announcements.aspx").get();
        Element table = doc.select(".bdywrpr .corwrpr-2clm-lr .cormain-2clm-lr table").first();
        return table;
    }
    
    public ArrayList<DailyAnnouncement> getDailyAnnouncements()
    {
        try
        {
            Element table = getAnnouncementsTable(); 
            ArrayList<DailyAnnouncement> result = new ArrayList<DailyAnnouncement>(5);
            
            Iterator<Element> ite = table.select("td").iterator();
            int counter = 0;
            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
            while (ite.hasNext())
            {
                ite.next();
                String s = ite.next().text();
                s = trim(s);
                
                //Separate into date and text
                int index = s.indexOf(days[counter]);
                index += days[counter].length();
                String date = s.substring(0, index);
                String text = s.substring(index);
                date = trim(date);
                text = trim(text);
                
                date = cleanString(date);
                text = cleanString(text);
                date = convertDate(date);
                
                DailyAnnouncement current = new DailyAnnouncement(date, text);
                result.add(current);
                
                counter++;
            }
            
            return result;
        }
        catch (Exception exc)
        {
        	System.err.println(exc.getLocalizedMessage());
            exc.printStackTrace();
        	DailyAnnouncement error = new DailyAnnouncement("ERRORERROR", "ERRORERROR");
            ArrayList<DailyAnnouncement> mistake = new ArrayList<DailyAnnouncement>(1);
            mistake.add(error);
            return mistake;
        }
    }
    
    public ArrayList<TopNews> getTopNews()
    {
        String[] titles = getNewsTitles();
        String[] news = getNews();
        ArrayList<TopNews> result = new ArrayList<TopNews>(titles.length);
        for (int i = 0; i < titles.length; i++)
        {
            TopNews current = new TopNews(titles[i], news[i]);
            result.add(current);
        }
        return result;
    }
    
    protected String[] getNewsTitles()
    {
        try
        {
            Document doc = Jsoup.connect("http://whs.d214.org/").get();
            Elements table = doc.select(".bdywrpr .corwrpr-3clm .wrpr2clm .cormain-hm #CT_Main_0_cache_pnlModule h1");
            Iterator iter = table.iterator();
            ArrayList<String> titles = new ArrayList<String>();
            String temp;
            while (iter.hasNext())
            {
                temp = iter.next().toString();
                temp = temp.substring(temp.indexOf(">") + 1, temp.indexOf("<", temp.indexOf(">"))); //remove h1 tags
                temp = trim(temp);
                temp = cleanString(temp);
                titles.add(temp);
            }
            titles.trimToSize();
            return titles.toArray(new String[]{null}); //convert ArrayList<String> to String[]
        }
        catch (Exception exc)
        {
        	System.err.println(exc.getLocalizedMessage());
            exc.printStackTrace();
        	return new String[]{"ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR"};
        }
    }
    
    protected String[] getNews()
    {
        try
        {
            Document doc = Jsoup.connect("http://whs.d214.org/").get();
            Elements table = doc.select(".bdywrpr .corwrpr-3clm .wrpr2clm .cormain-hm #CT_Main_0_cache_pnlModule p");
            Iterator iter = table.iterator();
            ArrayList<String> titles = new ArrayList<String>();
            String temp;
            while (iter.hasNext())
            {
                temp = iter.next().toString();
                temp = fix(temp);
                temp = trim(temp);
                temp = trim(fix2(temp));
                temp = cleanString(temp);
                titles.add(temp);
            }
            titles.trimToSize();
            return titles.toArray(new String[]{null}); //convert ArrayList<String> to String[]
        }
        catch (Exception exc)
        {
        	System.err.println(exc.getLocalizedMessage());
            exc.printStackTrace();
        	return new String[]{"ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR", "ERRORERROR"};
        }
    }
    
    //Take off <p> tags
    private static String fix(String temp)
    {
        int a = temp.indexOf("<p>");
        int b = temp.indexOf("</p>");
        return temp.substring(a + 3, b);
    }
    
    /**ALSO strip off possible <img>*/
    private static String fix2(String temp)
    {
        int a = temp.indexOf("<a");
        String result1 = temp.substring(0, a);
        result1 = trim(result1);
        if (result1.charAt(result1.length() - 1) == '.') //Considered complete if last character is a period
            return result1;
        else
            return retrieveCompleteStory(temp);
    }
    
    //extract <a> link, go to that page, and retrieve story
    private static String retrieveCompleteStory(String string)
    {
        //Find <a> link
        int a = string.indexOf("<a");
        int b = string.indexOf(">", a);
        String c = string.substring(a, b);
        int d = c.indexOf("href=\"");
        int e = c.indexOf("\"", d + "href=\"".length());
        String link = c.substring(d + "href=\"".length(), e);
        link = "http://whs.d214.org" + link;
        
        //Get story text
        String g = null;
        String i = null;
        try
        {
            Document doc = Jsoup.connect(link).get();
            Element f = doc.select(".cormain-2clm-lr").first();
            g = f.text();
            Element h = doc.select(".cormain-2clm-lr h4").first(); //This includes the date of the news, which needs to be removed
            i = h.text();
        }
        catch (Exception exc)
        {
            return "ERROR";
        }
        
        //clean up String g
        String j = "Back to News"; //this has to be removed too
        int k = g.indexOf(i) + i.length();
        int l = g.indexOf(j);
        String m = g.substring(k, l);
        m = trim(m);
        
        return m;
    }
    
    //Similar to String.trim() but also gets rid of &nbsp;
    private static String trim(String s)
    {
        int frontOffset = 0;
        int backOffset = 0;
        
        char current = 0x0;
        for (int i = 0; i < s.length(); i++)
        {
            current = s.charAt(i);
            if ((current < 33) /*includes 0-32*/ || (current == 160))
                frontOffset++;
            else
                break;
        }
        
        if (frontOffset == s.length()) //if it was all whitespace
            return "";
        
        for (int i = s.length() - 1; i > -1; i--)
        {
            current = s.charAt(i);
            if ((current < 33) || (current == 160))
                backOffset++;
            else
                break;
        }
        
        String result = s.substring(frontOffset, s.length() - backOffset);
        return result;
    }
    
    //Converts all &nbsp; (character code 160) to regular spaces and converts "&quot;" to "\""
    private static String cleanString(String s)
    {
        char remove1 = 160;
        String remove2 = "&quot;";
        if (s.indexOf(remove1) > 0) //if found
            s = s.replace(remove1, ' '); //replace &nbsp; with a regular space
        if (s.contains(remove2))
            s = s.replace(remove2, "\"");
        return s;
    }
    
    //Accepts something like "5.1.14 Thursday" and returns "Thursday, May 1, 2014"
    private static String convertDate(String s)
    {
        String[] numbersAndDay = s.split(" ");
        String[] numbers = numbersAndDay[0].split("\\x2E");
        
        String month;
        int monthInt = Integer.parseInt(numbers[0]);
        switch (monthInt)
        {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            default:
                month = "December";
                break;
        }
        
        String result = numbersAndDay[1];
        result += ", ";
        result += month;
        result += " ";
        result += numbers[1];
        result += ", ";
        result += "20";
        result += numbers[2];
        
        return result;
    }
	
	public String getEmergencyJson()
    {
    	Gson g = new Gson();
    	return g.toJson(getEmergencyClosingInformation());
    }
    
    public static String getEmergencyClosingInformation()
    {
        try
        {
            Document doc = Jsoup.connect("http://www.d214.org/district_information/emergency.aspx").get();
            Elements stuff = doc.select("table tbody h3");
            Iterator<Element> iter = stuff.iterator();
            String s = iter.next().text();
            if (s.contains("<h3>")) //just in case we get HTML tags
            {
                int a = s.indexOf(">");
                int b = s.indexOf("<", a);
                String s2 = s.substring(a + 1, b);
                s2 = cleanString(s2);
                return s2;
            }
            else
            {
                s = cleanString(s);
                return s;
            }
        }
        catch (Exception exc)
        {
            System.err.println(exc.getLocalizedMessage());
            exc.printStackTrace();
            return "error retrieving the emergency closing information";
        }
    }
}