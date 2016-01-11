package com.nodomain.scraper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.nodomain.util.HttpUtil;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Class for scraping educations, terms, and courses from SkemaSys as well as
 * calendar data, parseable by
 * {@link com.nodomain.parser.Parser#parseFromStream(java.io.InputStream)}
 * 
 * @author Anders Grøn
 *
 */
public class Scraper {
	private static final String indexUrl = "https://skemasys.akademiaarhus.dk/index.php";
	
	/**
	 * Scrapes the names of all educations
	 * 
	 * @return A list of educations
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public List<TimetableVar> getEdus() throws MalformedURLException, IOException {
		
		return null;
	}

	/**
	 * Scrapes all academic terms by name for a named education
	 * 
	 * @param education
	 *            The education to scrape terms from
	 * @return A list of terms
	 */
	public List<TimetableVar> getTerms(final String education) {
		return null;
	}

	/**
	 * Scrapes all courses for a named education at a named term
	 * 
	 * @param education
	 *            The education to scrape find the term in
	 * @param term
	 *            The term to scrape courses from
	 * @return A list of courses
	 */
	public List<TimetableVar> getcourse(final String education, final String term) {
		return null;
	}
	
	/**
	 * Scrape the calendar data from a specific class, parseable by the Parser class.
	 * 
	 * @param education The education wherein the class is located
	 * @param term The term for the class
	 * @param course The class name
	 * @return An {@link InputStream} to the calendar data
	 */
	public InputStream getCalenderData(final TimetableVar education, final TimetableVar term, final TimetableVar course) {
		return null;
	}
	
	/**
	 * A class that holds a "timetable" variable. It's just a very simple class
	 * with two properties, a String name and an int ID. The ID is used in URLs
	 * on SkemaSys, and the string name is used as an identifier. This class is
	 * used as a container for data scraped by the outer class.
	 * 
	 * @author Anders Grøn
	 *
	 */
	@Immutable
	public class TimetableVar {
		private final String name;
		private final int id;
		private final TimetableVarType type;
		
		private TimetableVar(final String name, final int id, final TimetableVarType type) {
			this.name = name;
			this.id = id;
			this.type = type;
		}
		
		/**
		 * Disallow instantiation outside this class file.
		 */
		private TimetableVar() { name = null; id = -1; type = null; }

		public String getName() { return name; }
		public int getId() { return id; }
		public TimetableVarType getType() { return type; }
	}
	
	/**
	 * Enum that describes the type of a TimetableVar object. Shouldn't be used
	 * outside this class.
	 * 
	 * @author Anders Grøn
	 *
	 */
	private enum TimetableVarType {
		education,
		term,
		course;
	}
}
