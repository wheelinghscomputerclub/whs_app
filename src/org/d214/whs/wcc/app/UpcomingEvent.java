package org.d214.whs.wcc.app;

public class UpcomingEvent
{
	public String eventDate, eventTitle;
    
    public UpcomingEvent(String date, String title)
    {
        if ((date == null) || (title == null))
            throw new NullPointerException();
        eventDate = date;
        eventTitle = title;
    }
}