package org.d214.whs.wcc.app;

import java.io.*;
import java.util.Calendar;

public class Cache
{
    private Cache() {}
    
    private static String upcomingEvents, topNews, dailyAnnouncements;
    private static Calendar lastUpdated;
    
    //Used for accessing WheelingApp's non-static getJson() methods.
    private final static WheelingApp wa = new WheelingApp();
    
    //Tests whether the current get request time is already the same date as
    //the last time the cache was updated
    //Tests whether the DAY is equal, not hours/minutes/seconds
    public static boolean sameDate()
    {
        if (lastUpdated == null)
            return false;
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.YEAR) == lastUpdated.get(Calendar.YEAR))
            return (c.get(Calendar.DAY_OF_YEAR) ==
                lastUpdated.get(Calendar.DAY_OF_YEAR));
        else
            return false;
    }
    
    //Updates the String values.
    private static void update()
    {
        upcomingEvents = wa.getUpcomingEventsJson();
        topNews = wa.getTopNewsJson();
        dailyAnnouncements = wa.getDailyAnnouncementsJson();
        lastUpdated = Calendar.getInstance();
    }
    
    public static String getUpcomingEvents()
    {
        if (sameDate())
        {
            if (upcomingEvents != null)
                return upcomingEvents;
            else
                return wa.getUpcomingEventsJson();
        }
        else
        {
            update();
            return upcomingEvents;
        }
    }
    
    public static String getTopNews()
    {
        if (sameDate())
        {
            if (topNews != null)
                return topNews;
            else
                return wa.getTopNewsJson();
        }
        else
        {
            update();
            return topNews;
        }
    }
    
    public static String getDailyAnnouncements()
    {
        if (sameDate())
        {
            if (dailyAnnouncements != null)
                return dailyAnnouncements;
            else
                return wa.getDailyAnnouncementsJson();
        }
        else
        {
            update();
            return dailyAnnouncements;
        }
    }
    
    
    
    /*      Persistence section     */
    
    //Write cache to file.
    public static void writeCache()
    {
        try
        {
            File out = new File("whs_app_cache");
            FileOutputStream fos = new FileOutputStream(out);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            //write values
            oos.writeObject(upcomingEvents);
            oos.writeObject(topNews);
            oos.writeObject(dailyAnnouncements);
            oos.writeObject(lastUpdated); //yes, Calendar is serializable
            
            oos.close();
            fos.close();
        }
        catch (IOException exc)
        {
            System.err.println(exc);
        }
    }
    
    //returns boolean because true if there was a cache file to read from,
    //false if there wasn't a cache file
    public static boolean readCache()
    {
        try
        {
            File in = new File("whs_app_cache");
            if (!in.exists())
                return false;
            
            FileInputStream fis = new FileInputStream(in);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            upcomingEvents = (String) ois.readObject();
            topNews = (String) ois.readObject();
            dailyAnnouncements = (String) ois.readObject();
            lastUpdated = (Calendar) ois.readObject();
            
            ois.close();
            fis.close();
            
            return true;
        }
        catch (Exception exc)
        {
            System.err.println(exc);
            return false;
        }
    }
}