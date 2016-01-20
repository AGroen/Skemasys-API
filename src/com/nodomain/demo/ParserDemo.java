package com.nodomain.demo;

import java.io.IOException;

import com.nodomain.api.parser.Parser;
import com.nodomain.api.parser.icsContainer;

public class ParserDemo {

	public static void main(String[] args) throws IOException {
		Parser p = new Parser();
		
		//Algorithms and Datastructures, 4. semester, Datamatikeruddannelsen
		icsContainer container = p.parseFromURL("https://skemasys.akademiaarhus.dk/calendar/timetable.php?subjectId=9629");
		
		System.out.println(container);
	}

}
