package edu.eci.pgr.utilities;

public class ValidationUtilities {
	public static boolean isNumeric(String number){
		boolean response = true;
		if (number == null || number.equals(""))
		return false;
		for(int i=0;i<number.length() && response;i++)
			try {
				Double.parseDouble(String.valueOf(number.charAt(i)));
			} catch (NumberFormatException e) {
				response = false;
			}
		return response;
	}
	
}
