package com.nodomain.api.parser;

import java.util.ArrayList;
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
	
	public icsContainer() {
		this.calEntries = new ArrayList<CalEntry>();
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
