package com.nodomain.api.parser;

import java.time.ZonedDateTime;

import jdk.nashorn.internal.ir.annotations.Immutable;

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
@Immutable
public class CalEntry {
	private final String uid; //Probably not needed, but better safe then sorry
	private final ZonedDateTime stamp, start, end;
	private final String summary;
	private final String location;
	
	public CalEntry(String uid, ZonedDateTime stamp, ZonedDateTime start, ZonedDateTime end, String summary, String location) {
		this.uid = uid;
		this.stamp = stamp;
		this.start = start;
		this.end = end;
		this.summary = summary;
		this.location = location;
	}
	
	public String getUid() {return uid;}
	//public void setUid(String uid) {this.uid = uid;}
	
	public ZonedDateTime getStamp() {return stamp;}
	//public void setStamp(LocalDateTime stamp) {this.stamp = stamp;}
	
	public ZonedDateTime getStart() {return start;}
	//public void setStart(LocalDateTime start) {this.start = start;}
	
	public ZonedDateTime getEnd() {return end;}
	//public void setEnd(LocalDateTime end) {this.end = end;}
	
	public String getSummary() {return summary;}
	//public void setSummary(String summary) {this.summary = summary;}
	
	public String getLocation() {return location;}
	//public void setLocation(String location) {this.location = location;}
}	
