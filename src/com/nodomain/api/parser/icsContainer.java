package com.nodomain.api.parser;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that contains all data read from an .ics file
 * 
 * @author Anders Grøn
 *
 */
// TODO Make getters for common header properties that'll search the headers
// map, objective is to make not make users write
// getHeaderByKey("X-WR-TIMEZONE"), but instead getTimezone()
public class icsContainer {
	//private String calScale, timezone, calName, calDesc;
	/** Seems like irrelevant variables, but they're saved just in case they'll ever come handy in the future. */
	//private String prodId, version, method;
	
	private Map<String, String> headers;
	
	private List<CalEntry> calEntries;
	
	public icsContainer() {
		this.calEntries = new ArrayList<CalEntry>();
	}
	
	public String getTimezone() {
		String timezone = headers.get("X-WR-TIMEZONE");
		
		if (timezone == null ||timezone.isEmpty())
			throw new IllegalStateException("Timezone hasn't been set in this container yet.");
		
		return timezone;
	}
	
	public ZoneId getTimeZoneId() {
		return ZoneId.of(getTimezone());
	}
	
	public String getHeaderByKey(final String key) {
		return this.headers.get(key);
	}
	
	public Map<String, String> getHeaders() {
		return this.headers;
	}
	
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public List<CalEntry> getCalEntries() {
		return new ArrayList<CalEntry>(calEntries);
	}

	public void setCalEntries(List<CalEntry> calEntries) {
		this.calEntries = calEntries;
	}
	
	public void addCalEntry(final CalEntry entry) {
		this.calEntries.add(entry);
	}
	
	public void removeCalEntr(final CalEntry entry) {
		this.calEntries.remove(entry);
	}
}
