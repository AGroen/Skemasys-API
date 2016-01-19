package com.nodomain.api.scraper;

import java.util.ArrayList;
import java.util.List;

import com.nodomain.api.scraper.Scraper.TimetableVar;

public class ScraperUtil {
	/**
	 * Extracts all courses from a list of timetable variables (normally this
	 * list will only contain courses and subjects, but anything is fine)
	 * 
	 * @param list
	 *            The list of timetable variables
	 * @return Returns a list of all courses from the input list, if none are
	 *         found then an empty list is returned
	 */
	public static List<TimetableVar> getCoursesFromList(final List<TimetableVar> list) {
		List<TimetableVar> courses = new ArrayList<TimetableVar>();
		
		for (TimetableVar var : list)
			if (isCourse(var))
				courses.add(var);
		
		return courses;
	}
	
	/**
	 * Extracts all subjects from a list of timetable variables (normally this
	 * list will only contain courses and subjects, but anything is fine)
	 * 
	 * @param list
	 *            The list of timetable variables
	 * @return Returns a list of all subjects from the input list, if none are
	 *         found then an empty list is returned
	 */
	public static List<TimetableVar> getSubjectsFromList(final List<TimetableVar> list) {
		List<TimetableVar> subjects = new ArrayList<TimetableVar>();
		
		for (TimetableVar var : list)
			if (isSubject(var))
				subjects.add(var);
		
		return subjects;
	}

	/**
	 * Tests whether a timetable variable is a course
	 * 
	 * @param var
	 *            The timetable variable
	 * @return True if timetable variable is a course, else false
	 */
	public static boolean isCourse(final TimetableVar var) {
		return var.getType() == TimetableVarType.COURSE;
	}
	
	/**
	 * Tests whether a timetable variable is a subject
	 * 
	 * @param var
	 *            The timetable variable
	 * @return True if timetable variable is a subject, else false
	 */
	public static boolean isSubject(final TimetableVar var) {
		return var.getType() == TimetableVarType.SUBJECT;
	}
}
