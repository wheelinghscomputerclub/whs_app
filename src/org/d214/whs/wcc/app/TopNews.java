package org.d214.whs.wcc.app;

public class TopNews
{
    public String title, details;
    
    public TopNews(String newsTitle, String newsDetails)
    {
        if ((newsTitle == null) || (newsDetails == null))
            throw new NullPointerException();
        title = newsTitle;
        details = newsDetails;
    }
}