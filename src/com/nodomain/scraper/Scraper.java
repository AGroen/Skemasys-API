package com.nodomain.scraper;

import java.io.InputStream;
import java.util.List;

/**
 * Class for scraping educations, terms, and classes from Skemasys as well as
 * calendar data, parseable by
 * {@link com.nodomain.parser.Parser#parseFromStream(java.io.InputStream)}
 * 
 * @author Anders Grøn
 *
 */
public class Scraper {
	/**
	 * Scrapes the names of all educations
	 * 
	 * @return A list of educations
	 */
	public List<String> getEdus() {
		return null;
	}

	/**
	 * Scrapes all academic terms by name for a named education
	 * 
	 * @param education
	 *            The education to scrape terms from
	 * @return A list of terms
	 */
	public List<String> getTerms(final String education) {
		return null;
	}

	/**
	 * Scrapes all classes for a named education at a named term
	 * 
	 * @param education
	 *            The education to scrape find the term in
	 * @param term
	 *            The term to scrape classes from
	 * @return A list of classes
	 */
	public List<String> getClasses(final String education, final String term) {
		return null;
	}
	
	/**
	 * Scrape the calendar data from a specific class, parseable by the Parser class.
	 * 
	 * @param education The education wherein the class is located
	 * @param term The term for the class
	 * @param className The class name
	 * @return An {@link InputStream} to the calendar data
	 */
	public InputStream getCalenderData(final String education, final String term, final String className) {
		return null;
	}
}
