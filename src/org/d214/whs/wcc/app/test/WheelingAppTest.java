package org.d214.whs.wcc.app.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.d214.whs.wcc.app.DailyAnnouncement;
import org.d214.whs.wcc.app.TopNews;
import org.d214.whs.wcc.app.UpcomingEvent;
import org.d214.whs.wcc.app.WheelingApp;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import static org.mockito.Mockito.*;

import org.jsoup.nodes.Document;

public class WheelingAppTest {

	class WheelingAppTestable extends WheelingApp {
		
		  private Element mockElement;

		  WheelingAppTestable() {
			  mockElement = null;
		  }
		  
	      WheelingAppTestable(Element mockElement) {
			  this.mockElement = mockElement;
		  }
	      
	      @Override
		  protected String[] getDates(Document doc) {
			  String[] mockDates = new String[] {"Thursday October 21, 2004", "Monday October 31, 2004", "Wednesday November 3, 2004", "Friday November 5, 2004", "Saturday November 6, 2004"};
			  return mockDates;
		  }
		  
	      @Override
		  protected String[] getTitles(Document doc) {
			  String[] mockTitles = new String[] {"Something Cool", "Something Else", "Event of Awesome", "Nap Time", "More Naps"};
			  return mockTitles;
		  }
		  
	      @Override
		  protected String[] getNewsTitles() {
			  String[] mockNewsTitles = new String[] {"News Title 1", "News Title 2"};
			  return mockNewsTitles;
		  }
		  
	      @Override
		  protected String[] getNews() {
			  String[] mockNews = new String[] {"Something happened at Wheeling on this day", "Something also happened at Wheeling on this day"};
			  return mockNews;
		  }
		  
	      @Override
		  protected Element getAnnouncementsTable() {
			  return mockElement;
		  }
	}
	
	@Test
	public void testGetUpcomingEvents() {
		WheelingAppTestable app = new WheelingAppTestable();
		
		ArrayList<UpcomingEvent> upcomingEvents = app.getUpcomingEvents();
		
		assertEquals(4, upcomingEvents.size());
		assertEquals("Thursday October 21, 2004", upcomingEvents.get(0).eventDate);
		assertEquals("Something Cool", upcomingEvents.get(0).eventTitle);
	}
	
	@Test
	public void testGetUpcomingEventsJsonify() {
		WheelingAppTestable app = new WheelingAppTestable();
		
		String jsonOutput = app.getUpcomingEventsJson();
		
		String expectedJson = "["
				+ "{\"eventDate\":\"Thursday October 21, 2004\","
				+ "\"eventTitle\":\"Something Cool\"},"
				+ "{\"eventDate\":\"Monday October 31, 2004\","
				+ "\"eventTitle\":\"Something Else\"},"
				+ "{\"eventDate\":\"Wednesday November 3, 2004\","
				+ "\"eventTitle\":\"Event of Awesome\"},"
				+ "{\"eventDate\":\"Friday November 5, 2004\","
				+ "\"eventTitle\":\"Nap Time\"}]";
		
		assertEquals(expectedJson, jsonOutput);
	}
	
	@Test
	public void testGetTopNews() {
		WheelingAppTestable app = new WheelingAppTestable();
		
		ArrayList<TopNews> news = app.getTopNews();
		
		assertEquals(2, news.size());
		assertEquals("News Title 1", news.get(0).title);
		assertEquals("Something happened at Wheeling on this day", news.get(0).details);
		
		assertEquals("News Title 2", news.get(1).title);
		assertEquals("Something also happened at Wheeling on this day", news.get(1).details);
	}
	
	@Test
	public void testGetTopNewsJsonify() {
		WheelingAppTestable app = new WheelingAppTestable();
		
		String jsonOutput = app.getTopNewsJson();
		
		String expectedJson = "["
				+ "{\"title\":\"News Title 1\","
				+ "\"details\":\"Something happened at Wheeling on this day\"},"
				+ "{\"title\":\"News Title 2\","
				+ "\"details\":\"Something also happened at Wheeling on this day\"}]";
		
		assertEquals(expectedJson, jsonOutput);
	}
	
	@Test
	public void testGetDailyAnnoucements() {
		Element mockElement = mock(Element.class);
		Elements mockElements = mock(Elements.class);
		Iterator<Element> mockIterator = mock(Iterator.class);
		
		Element firstMockElement = mock(Element.class);
		Element secondMockElement = mock(Element.class);
		
		when(firstMockElement.text()).thenReturn("5.5.14 Monday Prom guest forms are now available in the Attendance Office.");
		when(secondMockElement.text()).thenReturn("5.6.14 Tuesday If you normally see Mrs. B for a copy of your English books, please see her ASAP to pick up your Summer Reading 2014 book.");
		when(mockIterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockIterator.next()).thenReturn(null).thenReturn(firstMockElement).thenReturn(null).thenReturn(secondMockElement);
		when(mockElements.iterator()).thenReturn(mockIterator);
		when(mockElement.select("td")).thenReturn(mockElements);
		
		WheelingAppTestable app = new WheelingAppTestable(mockElement);
		
		ArrayList<DailyAnnouncement> announ = app.getDailyAnnouncements();
		
		assertEquals(2, announ.size());
		//assertEquals("Monday, May 5, 2014", announ.get(0).date);
		assertEquals("5.5.14 Monday", announ.get(0).date);
		assertEquals("Prom guest forms are now available in the Attendance Office.", announ.get(0).text);
		
		//assertEquals("Tuesday, May 6, 2014", announ.get(1).date);
		assertEquals("5.6.14 Tuesday", announ.get(1).date);
		assertEquals("If you normally see Mrs. B for a copy of your English books, please see her ASAP to pick up your Summer Reading 2014 book.", announ.get(1).text);
	}
	
	@Test
	public void testGetDailyAnnoucementsJsonify() {
			Element mockElement = mock(Element.class);
		Elements mockElements = mock(Elements.class);
		Iterator<Element> mockIterator = mock(Iterator.class);
		
		Element firstMockElement = mock(Element.class);
		Element secondMockElement = mock(Element.class);
		
		when(firstMockElement.text()).thenReturn("5.5.14 Monday Prom guest forms are now available in the Attendance Office.");
		when(secondMockElement.text()).thenReturn("5.6.14 Tuesday If you normally see Mrs. B for a copy of your English books, please see her ASAP to pick up your Summer Reading 2014 book.");
		when(mockIterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockIterator.next()).thenReturn(null).thenReturn(firstMockElement).thenReturn(null).thenReturn(secondMockElement);
		when(mockElements.iterator()).thenReturn(mockIterator);
		when(mockElement.select("td")).thenReturn(mockElements);
		
		WheelingAppTestable app = new WheelingAppTestable(mockElement);
		
		String jsonOutput = app.getDailyAnnouncementsJson();
	
		String expectedJson = "["
				+ "{\"date\":\"5.5.14 Monday\","
				+ "\"text\":\"Prom guest forms are now available in the Attendance Office.\"},"
				+ "{\"date\":\"5.6.14 Tuesday\","
				+ "\"text\":\"If you normally see Mrs. B for a copy of your English books, please see her ASAP to pick up your Summer Reading 2014 book.\"}]";
		
		assertEquals(expectedJson, jsonOutput);
	}
	
	
}
