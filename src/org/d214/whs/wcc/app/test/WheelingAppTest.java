package org.d214.whs.wcc.app.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.d214.whs.wcc.app.UpcomingEvent;
import org.d214.whs.wcc.app.WheelingApp;
import org.junit.Test;

public class WheelingAppTest {

	class WheelingAppTestable extends WheelingApp {
		
		  protected String[] getDates() {
			  String[] mockDates = new String[] {"Thursday October 21, 2004", "Monday October 31, 2004", "Wednesday November 3, 2004", "Friday November 5, 2004"};
			  return mockDates;
		  }
		  
		  protected String[] getTitles() {
			  String[] mockTitles = new String[] {"Something Cool", "Something Else", "Event of Awesome", "Nap Time"};
			  return mockTitles;
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

}
