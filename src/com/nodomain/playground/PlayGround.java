package com.nodomain.playground;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PlayGround {

	public static void main(String[] args) throws IOException { //20160201T073000Z
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssX");
		
		ZonedDateTime dateTime = ZonedDateTime.parse("20160201T073000Z", formatter);
		
		dateTime = dateTime.withZoneSameInstant(ZoneId.of("Europe/Copenhagen"));
		
		System.out.println(dateTime);
	}

}
