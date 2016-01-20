package com.nodomain.api.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import com.nodomain.util.ExtractUtil;
import com.nodomain.util.HttpUtil;

public class Parser {

	/**
	 * Contains all logic for parsing a stream of an .ics calendar file.
	 * 
	 * @param is
	 *            The inputstream to a .ics calendar
	 * @return An icsContainer with all the calendar headers and event entries
	 * @throws IOException
	 */
	public icsContainer parseFromStream(final InputStream is) throws IOException {
		final icsContainer container = new icsContainer();
		String calStr = HttpUtil.downloadPage(is); //This seems like a strange method call, but it works fine with any input stream
		
		//Clean up special characters in calendar text
		calStr = calStr.replace("&aring;", "å")
				.replace("&Aring;", "Å")
				.replace("&aelig;", "æ")
				.replace("&Aelig;", "Æ")
				.replace("&oslash;", "ø")
				.replace("&Oslash;", "Ø");
		
		//First we'll extract all the headers from the .ics file, located between the calendar begin tag and the first event's begin tag
		final String keyValuePairs = ExtractUtil.extract(calStr, "BEGIN:VCALENDAR", "BEGIN:VEVENT", 1);
		
		final Map<String, String> headerMap = keyValuePairsToMap(keyValuePairs);
		
		container.setHeaders(headerMap);
		
		//Now we'll extract all calendar entries (I know ExtractUtil#extract is inefficient, should be replaced with ExtractAdvancer#next in the future)
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssX");
		
		int i = 1;
		String calEntryStr;
		while ((calEntryStr = ExtractUtil.extract(calStr, "BEGIN:VEVENT", "END:VEVENT", i++)) != null) {
			final Map<String, String> calEntryMap = keyValuePairsToMap(calEntryStr); //Extract all event data into a HashMap
			
			final String stampStr = calEntryMap.get("DTSTAMP");
			final String startStr = calEntryMap.get("DTSTART");
			final String endStr = calEntryMap.get("DTEND");

			final ZonedDateTime stamp, start, end;
			
			//If any date isn't valid, we should just terminate right away.
			try {
				stamp = ZonedDateTime.parse(stampStr, formatter).withZoneSameInstant(container.getTimeZoneId());
				start = ZonedDateTime.parse(startStr, formatter).withZoneSameInstant(container.getTimeZoneId());
				end = ZonedDateTime.parse(endStr, formatter).withZoneSameInstant(container.getTimeZoneId());
			} catch (DateTimeParseException e) {
				e.printStackTrace();
				System.out.println("Encountered a date time that we couldn't parse, skipping event. Event index: " + i);
				continue;
			}
			
			final String uid = calEntryMap.get("UID");
			final String summary = calEntryMap.get("SUMMARY");
			final String location = calEntryMap.get("LOCATION");
			
			CalEntry entry = new CalEntry(uid, stamp, start, end, summary, location);
			container.addCalEntry(entry);
		}
		
		return container;
	}
	
	/**
	 * Parses an .ics calendar from a file. See
	 * {@link #parseFromStream(InputStream)} for details.
	 * 
	 * @param file
	 *            The .ics file to parse from
	 * @return An icsContainer with all the calendar headers and event entries
	 * @throws IOException 
	 */
	public icsContainer parseFromFile(final File file) throws IOException {
		FileInputStream fIn = new FileInputStream(file);
		
		return parseFromStream(fIn);
	}
	
	/**
	 * Parses an .ics calendar from a file. See
	 * {@link #parseFromStream(InputStream)} for details.
	 * 
	 * @param file
	 *            The path (as a String) to the .ics file to parse from
	 * @return An icsContainer with all the calendar headers and event entries
	 * @throws IOException 
	 */
	public icsContainer parseFromFile(final String file) throws IOException {
		return parseFromFile(new File(file));
	}

	/**
	 * Parses an .ics calendar from an URL. See
	 * {@link #parseFromStream(InputStream)} for details.
	 * 
	 * @param url
	 *            The URL to download the calendar from
	 * @return An icsContainer with all the calendar headers and event entries
	 */
	public icsContainer parseFromURL(final String url) {
		return null;
	}
	
	/**
	 * A utility method for splitting a string of key-value pairs and inserting
	 * them into a HashMap. Each key-value are separated by a newline, and a key
	 * is separated from its value by a colon (':').
	 * 
	 * @param keyValuePairs
	 *            A String of key-value pairs
	 * @return A HashMap with all key-value pairs
	 */
	private Map<String, String> keyValuePairsToMap(String keyValuePairs) {
		Map<String, String> map = new HashMap<String, String>();

		String[] keyValuePairsArr = keyValuePairs.split("\r\n");
		
		for (String keyValuePair : keyValuePairsArr) {
			String[] keyValueArr = keyValuePair.split(":");
			map.put(keyValueArr[0], keyValueArr[1]);
		}
		return map;
	}
}
