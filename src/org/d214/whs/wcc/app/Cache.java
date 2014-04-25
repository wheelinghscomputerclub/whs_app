package org.d214.whs.wcc.app;

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
    	if (sameDate() && (upcomingEvents != null))
    		return upcomingEvents;
    	else
    	{
    		update();
    		return upcomingEvents;
    	}
    }
    
    public static String getTopNews()
    {
    	if (sameDate() && (topNews != null))
    		return topNews;
    	else
    	{
    		update();
    		return topNews;
    	}
    }
    
    public static String getDailyAnnouncements()
    {
    	if (sameDate() && (dailyAnnouncements != null))
    		return dailyAnnouncements;
    	else
    	{
    		update();
    		return dailyAnnouncements;
    	}
    }
}