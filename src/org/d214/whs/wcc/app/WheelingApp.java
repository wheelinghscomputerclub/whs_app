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
    
    //public static ArrayList<TopNews> getTopNews()
    
    private static String[] getNewsTitles()
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
                temp = temp.trim();
                titles.add(temp);
            }
            titles.trimToSize();
            return titles.toArray(new String[]{null});
        }
        catch (Exception exc)
        {
            return new String[]{"ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR"};
        }
    }
    
    //private
    //static String[] getNews()
    static void getNews()
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
                temp = temp.trim();
                temp = fix2(temp).trim();
                titles.add(temp);
                System.out.println(temp);
            }
            titles.trimToSize();
            //return titles.toArray(new String[]{null});
        }
        catch (Exception exc)
        {
            //return new String[]{"ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR"};
            System.out.println(exc);
        }
    }
    
    //Take off <p> tags
    private static String fix(String temp)
    {
        int a = temp.indexOf("<p>");
        int b = temp.indexOf("</p>");
        return temp.substring(a + 3, b);
    }
    
    //Take off the <a>Continue Reading</a>
    //If complete (complete sentence; don't have to keep reading), returns just the news.
    //If not complete, goes to the web page "continue reading" and returns the story.
    /**ALSO strip off possible <img>*/
    private static String fix2(String temp)
    {
        int a = temp.indexOf("<a");
        String result1 = temp.substring(0, a);
        if (result1.charAt(result1.length() - 1) == '.') //Complete if last character is a period
            return result1;
        else
        {
            return null;
        }
    }
    
    /**Get emergency closing information*/
}