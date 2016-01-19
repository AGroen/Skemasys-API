package com.nodomain.api.parser;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class that contains all data read from an .ics file
 * 
 * @author Anders Grøn
 *
 */
public class icsContainer {
	private String calScale, timezone, calName, calDesc;
	
	/** Seems like irrelevant variables, but they're saved just in case they'll ever come handy in the future. */
	private String prodId, version, method;
	
	private List<CalEntry> calEntries;
	
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
	 * Inner class that contains the data for a single entry in a calender.
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

	public String getCalScale() {
		return calScale;
	}

	public void setCalScale(String calScale) {
		this.calScale = calScale;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getCalName() {
		return calName;
	}

	public void setCalName(String calName) {
		this.calName = calName;
	}

	public String getCalDesc() {
		return calDesc;
	}

	public void setCalDesc(String calDesc) {
		this.calDesc = calDesc;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<CalEntry> getCalEntries() {
		return calEntries;
	}

	public void setCalEntries(List<CalEntry> calEntries) {
		this.calEntries = calEntries;
	}
}
