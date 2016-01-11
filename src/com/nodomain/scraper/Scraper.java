package com.nodomain.scraper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.nodomain.util.ExtractUtil;
import com.nodomain.util.HttpUtil;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Class for scraping educations, semesters, and courses from SkemaSys as well as
 * calendar data, parseable by
 * {@link com.nodomain.parser.Parser#parseFromStream(java.io.InputStream)}
 * 
 * @author Anders Gr�n
 *
 */
public class Scraper {
	/* Ex. {eduId} in an url is a placeholder for the education id */
	private static final String indexUrl = "https://skemasys.akademiaarhus.dk/index.php";
	private static final String termUrl = "https://skemasys.akademiaarhus.dk/index.php?educationId={eduId}";
	
	/**
	 * Scrapes the names of all educations
	 * 
	 * @return A list of educations
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public List<TimetableVar> getEdus() throws MalformedURLException, IOException {
		List<TimetableVar> edus = new ArrayList<TimetableVar>();
		
		//Download index HTML and extract HTML for the top menu containing links to educations
		String indexPage = HttpUtil.downloadPage((new URL(indexUrl)).openConnection().getInputStream());
		String menuHTML = ExtractUtil.extract(indexPage, "<div id=\"topMenu\">", "<div class=\"clear\"></div>", 1);
		
		String eduEntry;
		int i = 1;
		
		while ((eduEntry = ExtractUtil.extract(menuHTML, "<a href=", "</li>", i++)) != null) {
			String eduName = ExtractUtil.extract(eduEntry, "title=\"", "\">", 1);
			int eduId = Integer.parseInt(ExtractUtil.extract(eduEntry, "\">", "</a>", 1));
			
			edus.add(new TimetableVar(eduName, eduId, TimetableVarType.EDUCATION));
		}
		
		return edus;
	}

	/**
	 * Scrapes all academic terms by name for a named education
	 * 
	 * @param education
	 *            The education to scrape terms from
	 * @return A list of semesters
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public List<TimetableVar> getSemesters(final TimetableVar education) throws MalformedURLException, IOException {
		List<TimetableVar> semesters = new ArrayList<TimetableVar>();
		
		//Download index HTML and extract the navigation HTML that contains semesters
		String indexPage = HttpUtil.downloadPage((new URL(termUrl.replace("{eduId}", "" + education.getId())))
				.openConnection().getInputStream());
		String menuHTML = ExtractUtil.extract(indexPage, "<div id=\"semesters\">", "<div class=\"box_bottom\"></div></div>", 1);
				
		String semEntry;
		int i = 1;
				
		while ((semEntry = ExtractUtil.extract(menuHTML, "<a href=", "</tr>", i++)) != null) {
			String semName = ExtractUtil.extract(semEntry, "</a>&nbsp;", "</td>", 1);
			int semId = Integer.parseInt(ExtractUtil.extract(semEntry, "&amp;semesterId=", "\"><img", 1));
					
			semesters.add(new TimetableVar(semName, semId, TimetableVarType.SEMESTER));
		}
		
		return semesters;
	}

	/**
	 * Scrapes all courses for a named education at a named term
	 * 
	 * @param education
	 *            The education to scrape from
	 * @param semester
	 *            The semester to scrape courses from
	 * @return A list of courses
	 */
	public List<TimetableVar> getCourses(final TimetableVar education, final TimetableVar semester) {
		return null;
	}
	
	/**
	 * Scrape the calendar data from a specific class, parseable by the Parser class.
	 * 
	 * @param education The education wherein the class is located
	 * @param semester The semester for the class
	 * @param course The class name
	 * @return An {@link InputStream} to the calendar data
	 */
	public InputStream getCalenderData(final TimetableVar education, final TimetableVar semester, final TimetableVar course) {
		return null;
	}
	
	/**
	 * A class that holds a "timetable" variable. It's just a very simple class
	 * with two properties, a String name and an int ID. The ID is used in URLs
	 * on SkemaSys, and the string name is used as an identifier. This class is
	 * used as a container for data scraped by the outer class.
	 * 
	 * @author Anders Gr�n
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
	 * @author Anders Gr�n
	 *
	 */
	private enum TimetableVarType {
		EDUCATION,
		SEMESTER,
		COURSE;
	}
}