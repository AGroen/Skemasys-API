package com.nodomain.demo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.nodomain.api.scraper.Scraper;
import com.nodomain.api.scraper.TimetableVarUtil;
import com.nodomain.api.scraper.Scraper.TimetableVar;

public class ScraperDemo {

	public static void main(String[] args) throws MalformedURLException, IOException {
		Scraper s = new Scraper();
		
		List<TimetableVar> educations;
		
		System.out.println("All available educations:");
		for (TimetableVar var : (educations = s.getEdus())) {
			System.out.println(var);
		}
		
		TimetableVar education = TimetableVarUtil.getIdFromList(educations, 1);
		List<TimetableVar> semesters = s.getSemesters(education);
		
		System.out.println("All semesters for educationId 1 (Datamatikeruddannelsen):");
		for (TimetableVar var : semesters)
			System.out.println(var);
		
		System.out.println("All courses and subjects for semesterId 373 (4. semester):");
		for (TimetableVar var : s.getCoursesAndSubjects(education, TimetableVarUtil.getIdFromList(semesters, 373)))
			System.out.println(var);
		
		System.out.println("All courses and subjects for semesterId 372 (2. semester):");
		for (TimetableVar var : s.getCoursesAndSubjects(education, TimetableVarUtil.getIdFromList(semesters, 371)))
			System.out.println(var);
	}

}
