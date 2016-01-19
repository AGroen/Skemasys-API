package com.nodomain.api.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that contains all data read from an .ics file
 * 
 * @author Anders Grøn
 *
 */
public class icsContainer {
	//private String calScale, timezone, calName, calDesc;
	/** Seems like irrelevant variables, but they're saved just in case they'll ever come handy in the future. */
	//private String prodId, version, method;
	
	//Make getters for common properties that'll search the headers map
	private Map<String, String> headers;
	
	private List<CalEntry> calEntries;
	
	public icsContainer() {
		this.calEntries = new ArrayList<CalEntry>();
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
