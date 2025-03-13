package CustomerDataManagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerValidation {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public boolean validateAddress(String address1) {
		if(address1.length()<=30 && !address1.isEmpty()) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public boolean validateCityState(String city) {
		String pattern = "^[\\p{L} ]{1,15}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(city);
		if (!m.matches() || city == null || city.trim().isEmpty()) {
			return false;
	    }
		return true;
	}
	
	public boolean validateCustomerName(String name) {
	    return name.matches("[a-zA-Z ]+");  // Only letters and spaces
	}

	public boolean validatePhoneNumber(String phone) {
	    String pattern = "[1-9]\\d{9}";
	    return phone.matches(pattern); 
	}
	
	public boolean validateEmail(String email) {
		String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		if(!m.matches() || email == null || email.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean validateDate(String date) {
		try {
			LocalDate.parse(date, DATE_FORMATTER);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public boolean validatePrinciple(String p) {
		
		if(p.matches("\\d+")) {
			int principle = Integer.parseInt(p);
			return principle>0;
		}
		return false;
	}

	public boolean validateRate(String r) {
		if(r.matches("\\d*\\.\\d+|\\d+") && Double.parseDouble(r)>0.0) {
			return true;
		}
		return false;
	}
	
}
