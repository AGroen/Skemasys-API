package com.nodomain.demo;

import java.io.IOException;
import java.net.MalformedURLException;

import com.nodomain.api.scraper.Scraper;
import com.nodomain.api.scraper.Scraper.TimetableVar;

public class ScraperDemo {

	public static void main(String[] args) throws MalformedURLException, IOException {
		Scraper s = new Scraper();
		
		System.out.println("All available educations:");
		for (TimetableVar var : s.getEdus()) {
			System.out.println(var);
		}
		
		System.out.println("All semesters for Datamatikeruddannelsen:");
//		for (TimetableVar var : s.getSemesters())
	}

}
