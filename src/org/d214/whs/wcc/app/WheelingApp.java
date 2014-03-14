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
        
        String[] dates, titles, details;
        dates = getDates();
        titles = getTitles();
        details = getDescriptions();
        
        for (int i = 0; i < 4; i++)
        {
            UpcomingEvent current = new UpcomingEvent(dates[i], titles[i], details[i]);
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
    
    /**STILL DO*/
    //private
    static String[] getDescriptions()
    {
        try
        {
            Document doc = Jsoup.connect("http://whs.d214.org/").get();
            Elements eventItems = doc.select(".eventitem .descr .bold .bold '.bold'");
            
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
                int index = s.indexOf(days[counter]);
                index += days[counter].length();
                String date = s.substring(0, index);
                String text = s.substring(index);
                date = date.trim();
                text = text.trim();
                date = fixDate(date, days[counter]);
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
    
    private static String fixDate(String input, String day)
    {
        int a = input.indexOf(day);
        String date = input.substring(0, a - 1);
        
        if (day.equals("Monday"))
            date = date.substring(1);
        else
            date = date.substring(2);
        
        String[] elements = date.split("\\D"); //non-digit characters
        String month;
        switch (Integer.parseInt(elements[0]))
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
            case 12:
                month = "December";
                break;
            default:
                month = null;
        }
        
        String year = "20" + elements[2];
        
        String result = day + ", " + month + " " + elements[1] + ", " + year;
        
        return result;
    }
}