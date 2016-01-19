package com.nodomain.demo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.nodomain.api.scraper.Scraper;
import com.nodomain.api.scraper.TimetableVarUtil;
import com.nodomain.api.scraper.Scraper.TimetableVar;
import com.nodomain.util.HttpUtil;

public class ScraperDemo {

	public static void main(String[] args) throws MalformedURLException, IOException {
		Scraper s = new Scraper();
		
		System.out.println("All available educations:");

		List<TimetableVar> educations;
		
		for (TimetableVar var : (educations = s.getEdus())) {
			System.out.println(var);
		}		
		
		System.out.println("\nAll semesters for educationId 1 (Datamatikeruddannelsen):");
		
		TimetableVar education = TimetableVarUtil.getIdFromList(educations, 1);
		List<TimetableVar> semesters = s.getSemesters(education);
		
		for (TimetableVar var : semesters)
			System.out.println(var);
		
		System.out.println("\nAll courses and subjects for semesterId 373 (4. semester):");

		TimetableVar semester = TimetableVarUtil.getIdFromList(semesters, 373);
		List<TimetableVar> coursesAndSubjects = s.getCoursesAndSubjects(education, semester);
		
		for (TimetableVar var : coursesAndSubjects)
			System.out.println(var);
		
		System.out.println("\nAll courses and subjects for semesterId 372 (2. semester):");
		
		for (TimetableVar var : s.getCoursesAndSubjects(education, TimetableVarUtil.getIdFromList(semesters, 371)))
			System.out.println(var);
		
		System.out.println("\n.ics calendar data for subjectId 9628 (Databaser for udviklere):");
		
		TimetableVar subject = TimetableVarUtil.getIdFromList(coursesAndSubjects, 9628);
		
		System.out.println(HttpUtil.downloadPage(s.getCalenderData(subject)));
	}

}
