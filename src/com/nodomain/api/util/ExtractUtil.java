package com.nodomain.api.util;

public class ExtractUtil {
	public static String extractById(String str, String id, String attributeToReturn, int count) {
		int location1, location2, valueLoc1, valueLoc2;
		String token1 = " id=\"" + id;
		String token2 = "\" ";
		String valueToken2 = "\"";
		
		location1 = location2 = 0;
		do {
			location1 = str.indexOf(token1, location1);

			if (location1 == -1)
				return null;

			count--;
		} while (count > 0);

		location2 = str.indexOf(token2, location1 + 1);
		if (location2 == -1)
			return null;

		//return str.substring(location1 + token1.length(), location2);
		
		//Now extract the value part
		valueLoc1 = str.indexOf(attributeToReturn, location2) + attributeToReturn.length();
		valueLoc2 = str.indexOf(valueToken2, valueLoc1);
		
		try {
			return str.substring(valueLoc1, valueLoc2);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public static String extract(String str, String token1, String token2, int count) {
		int location1, location2;

		location1 = location2 = 0;
		do {
			location1 = str.indexOf(token1, location1);

			if (location1 == -1)
				return null;

			location1 += + token1.length();
			count--;
		} while (count > 0);

		location2 = str.indexOf(token2, location1 + 1);
		if (location2 == -1)
			return null;

		return str.substring(location1, location2);
	}
}
