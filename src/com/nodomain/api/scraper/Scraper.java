package com.nodomain.api.scraper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.nodomain.util.ExtractUtil;
import com.nodomain.util.HttpUtil;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Class for scraping educations, semesters, courses, and subjects from SkemaSys
 * as well as calendar data, parseable by
 * {@link com.nodomain.api.parser.Parser#parseFromStream(java.io.InputStream)}<br>
 * <br>
 * There's a difference between a <b>course</b> and <b>subject</b>. A course is a class with
 * multiple subjects. There will only be subjects available when all members of
 * a class doesn't share all subjects, if they do then subjects won't be shown
 * individually.
 * 
 * @author Anders Grøn
 *
 */
/*
 * Developer note: This is a stateless class, therefore it makes sense to
 * prevent instantiation and make every method static, but caching webpages
 * might be implemented in the future. It depends how users will use this
 * Scraper in practice. So no final decision has been made yet. Methods will
 * remain not static for now.
 */
public class Scraper {
	/* Ex. {eduId} in an url is a placeholder for the education id */
	private static final String indexUrl = "https://skemasys.akademiaarhus.dk/index.php";
	private static final String semesterUrl = "https://skemasys.akademiaarhus.dk/index.php?educationId={eduId}";
	private static final String educationUrl = "https://skemasys.akademiaarhus.dk/index.php?educationId={eduId}&menuId=1&account=timetable_semester&semesterId={semId}";
	
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
		String indexPage = HttpUtil.downloadPage(HttpUtil.initHttpSession(indexUrl).getInputStream());
		String menuHTML = ExtractUtil.extract(indexPage, "<div id=\"topMenu\">", "<div class=\"clear\"></div>", 1);
		
		String eduEntry;
		int i = 1;
		
		while ((eduEntry = ExtractUtil.extract(menuHTML, "<a href=", "</li>", i++)) != null) {
			String eduName = ExtractUtil.extract(eduEntry, "title=\"", "\">", 1);
			int eduId = Integer.parseInt(ExtractUtil.extract(eduEntry, "Id=", "\" title", 1));
			
			edus.add(new TimetableVar(eduName, eduId, TimetableVarType.EDUCATION));
		}
		
		return edus;
	}

	/**
	 * Scrapes all academic terms (semseters) by name for a named education
	 * 
	 * @param education
	 *            The education to scrape terms from
	 * @return A list of semesters
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public List<TimetableVar> getSemesters(final TimetableVar education) throws MalformedURLException, IOException {
		List<TimetableVar> semesters = new ArrayList<TimetableVar>();
		
		//Download education HTML and extract the navigation HTML that contains semesters
		String eduPage = HttpUtil.downloadPage((new URL(semesterUrl.replace("{eduId}", "" + education.getId()))).openConnection().getInputStream());
		String menuHTML = ExtractUtil.extract(eduPage, "<div id=\"semesters\">", "<div class=\"box_bottom\"></div></div>", 1);
				
		String semEntry;
		int i = 1;
				
		while ((semEntry = ExtractUtil.extract(menuHTML, "<a href=", "</tr>", i++)) != null) {
			String semName = ExtractUtil.extract(semEntry, "</a>&nbsp;", "</td>", 1).trim();
			int semId = Integer.parseInt(ExtractUtil.extract(semEntry, "&amp;semesterId=", "\"><img", 1));
					
			semesters.add(new TimetableVar(semName, semId, TimetableVarType.SEMESTER));
		}
		
		return semesters;
	}

	/**
	 * Scrapes all courses and subjects (see {@link Scraper}) for a named
	 * education at a named semester
	 * 
	 * @param education
	 *            The education to scrape from
	 * @param semester
	 *            The semester to scrape courses from
	 * @return A list of courses and subjects, see {@link TimetableVarUtil} for
	 *         utility methods for filtering courses and subjects
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public List<TimetableVar> getCoursesAndSubjects(final TimetableVar education, final TimetableVar semester) throws MalformedURLException, IOException {
		//account=timetable_subject&amp;subject    "><img class="messageImg"
		//account=timetable_class&amp;class     "><img class="messageImg"
		List<TimetableVar> coursesAndSubjects = new ArrayList<TimetableVar>();
		
		//Prepare URL and download semester HTML
		String url = semesterUrl.replace("{eduId}", education.getId() + "").replace("{semId}", semester.getId() + "");
		String semPage = HttpUtil.downloadPage((new URL(url).openConnection().getInputStream()));
		
		String entry;
		int i = 1;
		
		//Extract courses
		while ((entry = ExtractUtil.extract(semPage, "account=timetable_class&amp;class", "</tr>", i++)) != null) {
			String entryName = ExtractUtil.extract(entry, "</a>&nbsp;", "</td>", 1).trim();
			int entryId = Integer.parseInt(ExtractUtil.extract(entryName, "Id=", "\"><img", 1));
			
			coursesAndSubjects.add(new TimetableVar(entryName, entryId, TimetableVarType.COURSE));
		}
		
		i = 1;
		//Extract subjects
		while ((entry = ExtractUtil.extract(semPage, "account=timetable_subject&amp;subject", "</tr>", i++)) != null) {
			String entryName = ExtractUtil.extract(entry, "</a>&nbsp;", "</td>", 1).trim();
			int entryId = Integer.parseInt(ExtractUtil.extract(entryName, "Id=", "\"><img", 1));
			
			coursesAndSubjects.add(new TimetableVar(entryName, entryId, TimetableVarType.SUBJECT));
		}

		return coursesAndSubjects;
	}
	
	/**
	 * Scrape the calendar data from a specific course, parseable by the Parser
	 * class.
	 * 
	 * @param education
	 *            The education wherein the class is located
	 * @param semester
	 *            The semester for the course
	 * @param course
	 *            The course name
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
		 * Disallow instantiation outside the outer class.
		 */
		private TimetableVar() { name = null; id = -1; type = null; }
		
		@Override
		public String toString() {
			return "Name=" + this.name + ", id=" + this.id + ", type=" + this.type;
		}

		public String getName() { return name; }
		public int getId() { return id; }
		public TimetableVarType getType() { return type; }
	}
}
