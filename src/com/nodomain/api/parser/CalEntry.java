package com.nodomain.api.parser;

import java.time.LocalDateTime;

/* Sample calender entry:
 * 
 * BEGIN:VEVENT
 * UID:module_339827@skemasys.efif.dk
 * DTSTAMP:20160201T093000Z
 * DTSTART:20160201T093000Z
 * DTEND:20160201T110000Z
 * SUMMARY:Algorithms and Datastructures JHAJ
 * LOCATION:SH-A1.19B
 * END:VEVENT
 */
/**
 * Class that contains the data for a calendar entry.
 * 
 * @author Anders Grøn
 *
 */
public class CalEntry {
	private LocalDateTime stamp, start, end;
	private String summary;
	private String location;
	
	public CalEntry(LocalDateTime stamp, LocalDateTime start, LocalDateTime end, String summary, String location) {
		this.stamp = stamp;
		this.start = start;
		this.end = end;
		this.summary = summary;
		this.location = location;
	}
	
	public LocalDateTime getStamp() {return stamp;}
	public void setStamp(LocalDateTime stamp) {this.stamp = stamp;}
	
	public LocalDateTime getStart() {return start;}
	public void setStart(LocalDateTime start) {this.start = start;}
	
	public LocalDateTime getEnd() {return end;}
	public void setEnd(LocalDateTime end) {this.end = end;}
	
	public String getSummary() {return summary;}
	public void setSummary(String summary) {this.summary = summary;}
	
	public String getLocation() {return location;}
	public void setLocation(String location) {this.location = location;}
}	
