package org.d214.whs.wcc.app;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class WheelingApp
{
    public static ArrayList<UpcomingEvent> getUpcomingEvents()
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
    
    private static String[] getDates()
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
                result[i] = elementText;
                i++;
            }
            
            return result;
        }
        catch (Exception exc)
        {
            return new String[]{"ERROR", "ERROR", "ERROR", "ERROR"};
        }
    }
    
    private static String[] getTitles()
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
                result[i] = elementText;
                i++;
            }
            
            return result;
        }
        catch (Exception exc)
        {
            return new String[]{"ERROR", "ERROR", "ERROR", "ERROR"};
        }
    }
    
    public static ArrayList<DailyAnnouncement> getDailyAnnouncements()
    {
        try
        {
            Document doc = Jsoup.connect("http://whs.d214.org/student_resources/daily_announcements.aspx").get();
            Element table = doc.select(".bdywrpr .corwrpr-2clm-lr .cormain-2clm-lr table").first();
            
            ArrayList<DailyAnnouncement> result = new ArrayList<DailyAnnouncement>(5);
            
            Iterator<Element> ite = table.select("td").iterator();
            int counter = 0;
            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
            while (ite.hasNext())
            {
                ite.next();
                String s = ite.next().text();
                s = s.trim();
                
                //Separate into date and text
                int index = s.indexOf(days[counter]);
                index += days[counter].length();
                String date = s.substring(0, index);
                String text = s.substring(index);
                date = date.trim();
                text = text.trim();
                
                DailyAnnouncement current = new DailyAnnouncement(date, text);
                result.add(current);
                
                counter++;
            }
            
            return result;
        }
        catch (Exception exc)
        {
            DailyAnnouncement error = new DailyAnnouncement("ERROR", "ERROR");
            ArrayList<DailyAnnouncement> mistake = new ArrayList<DailyAnnouncement>(1);
            mistake.add(error);
            return mistake;
        }
    }
    
    public static ArrayList<TopNews> getTopNews()
    {
        try
        {
            Document doc = Jsoup.connect("http://whs.d214.org/").get();
            Elements table = doc.select(".bdywrpr .corwrpr-3clm .wrpr2clm .cormain-hm #CT_Main_0_cache_pnlModule h1/p");
            Iterator iter = table.iterator();
            while (iter.hasNext())
                System.out.println(iter.next());
        }
        catch (Exception exc)
        {
            TopNews error = new TopNews("ERROR", "ERROR");
            ArrayList<TopNews> mistake = new ArrayList<TopNews>(1);
            mistake.add(error);
            return mistake;
        }
        return null;
    }
    
    /**Get emergency closing information*/
}