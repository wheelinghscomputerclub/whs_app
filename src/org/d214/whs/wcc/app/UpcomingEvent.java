package org.d214.whs.wcc.app;

public class UpcomingEvent
{
    public String eventDate, eventTitle, eventDetails;
    
    public UpcomingEvent(String date, String title, String details)
    {
        if ((date == null) || (title == null) || (details == null))
            throw new NullPointerException();
        eventDate = date;
        eventTitle = title;
        eventDetails = details;
    }
}