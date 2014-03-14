package org.d214.whs.wcc.app;

public class DailyAnnouncement
{
    public String date, text;
    
    public DailyAnnouncement(String eventDate, String eventText)
    {
        if ((eventDate == null) || (eventText == null))
            throw new NullPointerException();
        date = eventDate;
        text = eventText;
    }
}