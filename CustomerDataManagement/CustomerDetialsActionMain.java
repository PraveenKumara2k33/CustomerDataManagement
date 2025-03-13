package CustomerDataManagement;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CustomerDetialsActionMain {

	String sURL ;
	String sUserName;
	String sPwd;
	String driver;
	
	CustomerDetials cd;
	CustomerValidation cv = new CustomerValidation();
	InterestDAO dao;
	
	static Scanner sc = new Scanner(System.in);
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static void main(String[] args) {
		CustomerDetialsActionMain cdm = new CustomerDetialsActionMain();
		cdm.dao = new InterestDAO();
		cdm.cd = new CustomerDetials();  // Initialize CustomerDetials object
	    cdm.cd.setAddr(new AddressDetials());  // Initialize AddressDetials object
	    cdm.cd.setTrans(new Transaction()); // Initialize TransactionDetials object
        int choice = 0;
		
		while(choice != 6) {
			System.out.println("1. Add Customer Address");
			System.out.println("2. Add Customer Records");
			System.out.println("3. Add Customer Transaction");
			System.out.println("4. Add Full Customer Details");
			System.out.println("5. View Full Customer Details");
			System.out.println("6. Exit");
			choice = sc.nextInt();
			if(choice == 1) {
//				cdm.getAddressID();
				sc.nextLine();
				cdm.getAddressLine1();
				cdm.getAddressLine2();
				cdm.getAddressLine3();
				cdm.getCity();
				cdm.getState();
				cdm.addAddressDetials();
				System.out.println("Customer Address added successfully!");
			}else if (choice == 2) {
//				cdm.getCustomerID();
				sc.nextLine();
				cdm.getCustomerName();
				cdm.getPhoneNumber();
				sc.nextLine();
				cdm.getEmail();
//				cdm.getAddressID();
				cdm.addCustomerDetials();
				System.out.println("Customer Details added successfully!");
			}else if (choice == 3) {
//				cdm.getTransID();
				sc.nextLine();
				cdm.getTransDate();
				cdm.getPrinciple();
				cdm.getRate();
				cdm.getTenure();
				cdm.getInterset();
				cdm.getTotalAmount();
				cdm.addCustomerTransaction();
				System.out.println("Customer Transaction added successfully!");
			}else if (choice == 4) {
				cdm.getDataFromUser();
				cdm.getFullCustomerDetails();
				System.out.println("Full Customer Details added successfully!");
			}else if (choice == 5) {
				cdm.getViewCustomerDetials();
			} else if(choice == 6) {
				System.out.println("Exiting CustomerDetials Management System...");
			}else {
				System.out.println("Invalid choice. Try again.");
			}
		}
	}
	public void getViewCustomerDetials() {
		
		try {
			Connection conn = getDBConnection();
			String query = "SELECT * FROM CUSTOMER_TB1 C JOIN ADDRESS_TB1 A ON (A.ADDR_ID = C.ADDR_ID) JOIN TRANSACTION_TB1 T ON (C.CUST_ID = T.CUST_ID)";
			PreparedStatement pt = conn.prepareStatement(query);
			ResultSet rs = pt.executeQuery();
			while(rs.next()) {
				int addrId = rs.getInt("ADDR_ID");
				String addrLine1 = rs.getString("ADDR_LINE1");
				String addrLine2 = rs.getString("ADDR_LINE2");
				String addrLine3 = rs.getString("ADDR_LINE3");
	            String city = rs.getString("CITY");
	            String state = rs.getString("STATE");
	            int custId = rs.getInt("CUST_ID");
	            String custName = rs.getString("CUST_NAME");
	            long phoneNumber = rs.getLong("PHONE");
	            String email = rs.getString("EMAIL");
	            int transactionId = rs.getInt("TRANS_ID");
	            String transDate = rs.getString("TRANS_DATE");
	            int principle = rs.getInt("PRINCIPLE_AMOUNT");
	            double rate = rs.getDouble("RATE_OF_INTEREST");
	            int year = rs.getInt("NO_OF_TENURE");
	            double si = rs.getDouble("INTEREST_AMT");
	            double total = rs.getDouble("TOTAL_AMT");
				System.out.println("ADDRESS_ID :"+addrId+" \tADDR_LINE1 :"+addrLine1+" \tADDR_LINE2 :"+addrLine2+" \tADDR_LINE3 :"+addrLine3+" \t CITY :"+city+" \tSTATE :"+state+" \tCUST_ID :"+custId+" \tCUST_NAME :"+custName+" \t PHONE :"+phoneNumber+" \tEMAIL :"+email+" \tTRANSID :"+transactionId+" \tTRANSDATE :"+transDate+" \tPRINCIPLE :"+principle+" \t RATE :"+rate+" \t YEAR :"+year+" \t SI :"+si+" \t Total Amount :"+total);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public void getFullCustomerDetails() {
		
		//address Table
		addAddressDetials();
		
		//customer Table
		addCustomerDetials();
		
		//Transaction Table
		addCustomerTransaction();
	}
	public void getDataFromUser() {
//		getAddressID();
		sc.nextLine();
		getAddressLine1();
		getAddressLine2();
		getAddressLine3();
		getCity();
		getState();
		
//		getCustomerID();
		sc.nextLine();
		getCustomerName();
		getPhoneNumber();
		sc.nextLine();
		getEmail();
		
//		getTransID();
		sc.nextLine();
		getTransDate();
		getPrinciple();
		getRate();
		getTenure();
		getInterset();
		getTotalAmount();
		
	}
	
	public void addAddressDetials() {
		dao.saveAddress(cd.getAddr());
		cd.getAddr().setAddressId(dao.addrID);
	}
	
	public void addCustomerDetials() {
		
		int addrId = cd.getAddr().getAddressId();
		if(addrId == 0) {
			getReAddressID();
			addrId = cd.getAddr().getAddressId();
		}
		dao.saveCustomer(cd);
		cd.getTrans().setCustId(dao.custID); 
	}
	
	public void addCustomerTransaction() {
		
		int cust_ID = cd.getTrans().getCustId();
		if(cust_ID == 0) {
			getreCustomerID();
			cust_ID = cd.getTrans().getCustId();
		}
		dao.saveTransaction(cd.getTrans());
//		cd.getTrans().setTransId(dao.transID);
	}
	public void getReAddressID() {
		System.out.println("Enter Address ID : ");
		String input = sc.next();
		if(cv.validatePrinciple(input)) {
			int id = Integer.parseInt(input);
			try {
				Connection conn = getDBConnection();
				PreparedStatement pt = null;
				ResultSet rs = null;
				String checkQuery = "select ADDR_ID from address_tb1 where ADDR_ID = ?";
				pt = conn.prepareStatement(checkQuery);
				pt.setInt(1, id);
				rs = pt.executeQuery();
				if(rs.next()) {
					cd.getAddr().setAddressId(id);
				}else {
					System.out.println("Not exist this Address ID Re-enter:");
					getReAddressID();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}else {
			System.out.println("Invalid Address ID");
			getReAddressID();
		}
	}
	public void getreCustomerID() {
		
		System.out.println("Enter Customer ID : ");
		String input = sc.next();
		if(cv.validatePrinciple(input)) {
			int id = Integer.parseInt(input);
			try {
				Connection conn = getDBConnection();
				PreparedStatement pt = null;
				ResultSet rs = null;
				String checkQuery = "select CUST_ID from CUSTOMER_TB1 where CUST_ID = ?";
				pt = conn.prepareStatement(checkQuery);
				pt.setInt(1, id);
				rs = pt.executeQuery();
				if(rs.next()) {
					cd.getTrans().setCustId(id);
				}else {
					System.out.println("Not exist this Customer ID Re-enter:");
					getreCustomerID();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}else {
			System.out.println("Invalid Address ID");
			getreCustomerID();
		}
	}
	

	
	public void getCustomerID() {
		
		int cust_ID = 0;
		try {
			Connection conn = getDBConnection();
//			String seqQuery = "SELECT CUSTOMER_ID_SEQ.NEXTVAL FROM dual";//Oracle 11g
			String seqQuery = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'CUSTOMER_TB1' AND TABLE_SCHEMA = 'CustomerDetails'";
			PreparedStatement pt = conn.prepareStatement(seqQuery);
			ResultSet rs = pt.executeQuery();
			if(rs.next()) {
				cust_ID = rs.getInt(1);
				cd.setCustId(cust_ID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void getTransID() {
		
		int trans_ID = 0;
		try {
			Connection conn = getDBConnection();
//			String seqQuery = "SELECT TRANSACTION_ID_SEQ.NEXTVAL FROM dual";//Oracle 11g
			String seqQuery = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TRANSACTION_TB1' AND TABLE_SCHEMA = 'CustomerDetails'";
			PreparedStatement pt = conn.prepareStatement(seqQuery);
			ResultSet rs = pt.executeQuery();
			if(rs.next()) {
				trans_ID = rs.getInt(1);
				cd.getTrans().setTransId(trans_ID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void getAddressID() {
		try {
			Connection conn = getDBConnection();
//			String seqQuery = "SELECT ADDRESS_ID_SEQ.NEXTVAL FROM dual";//Oracle 11g
			String seqQuery = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'ADDRESS_TB1' AND TABLE_SCHEMA = 'CustomerDetails'";
	        PreparedStatement pt = conn.prepareStatement(seqQuery);
	        ResultSet rs = pt.executeQuery();
			if(rs.next()) {
				int addr_id = rs.getInt(1);
				cd.getAddr().setAddressId(addr_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	public void getCustomerName() {
		System.out.println("Enter the  customer name");
		String cust_name=sc.nextLine();
		if(cv.validateCustomerName(cust_name)) {
			cd.setCustName(cust_name);
		}else {
			System.out.println("Invalid customer name");
			getCustomerName();			
		}
	}
	
	public void getPhoneNumber() {
		System.out.println("Enter the  customer Phone Number : ");
		String phone = sc.next();
		if (cv.validatePhoneNumber(phone)) {
			try {
                long phoneNumber = Long.parseLong(phone);
                cd.setPhone(phoneNumber);
			}catch (NumberFormatException e ) {
				System.out.println("Invalid input: Phone number should be numeric.");
				getPhoneNumber();
			}
		}else {
			System.out.println("Invalid customer Phone Number. Please enter a valid 10-digit number.");
            getPhoneNumber(); 
		}
	}
	public void getEmail() {
		System.out.println("Enter the  customer Email : ");
		String email = sc.nextLine();
		boolean flag = cv.validateEmail(email);
		if(flag == true) {
			cd.setEmail(email);
		}else {
			System.out.println("Invalid customer Phone Number");
			getEmail();
		}
		
	}	
	
	
	public void getAddressLine1() {
		System.out.println("Enter Customer Address 1 : ");
		String address1 = sc.nextLine();
		if(cv.validateAddress(address1)) {
			cd.getAddr().setAddr_line1(address1);
		}else {
			System.out.println("Invalid customer Address ");
			getAddressLine1();
		}
	}
	public void getAddressLine2() {
		System.out.println("Enter Customer Address 2 : ");
		String address2 = sc.nextLine();
		if(cv.validateAddress(address2)) {
			cd.getAddr().setAddr_line2(address2);
		}else {
			System.out.println("Invalid customer Address ");
			getAddressLine1();
		}
	}
	public void getAddressLine3() {
		System.out.println("Enter Customer Address 3 : ");
		String address3 = sc.nextLine();
		if(cv.validateAddress(address3)) {
			cd.getAddr().setAddr_line3(address3);
		}else {
			System.out.println("Invalid customer Address ");
			getAddressLine1();
		}
	}
	
	public void getCity() {
		System.out.println("Enter Customer City : ");
		String city = sc.nextLine();
		if(cv.validateCityState(city)) {
			cd.getAddr().setCity(city.toUpperCase());
		}else {
			System.out.println("Invalid customer City ");
			getCity();
		}
	}
	
	public void getState() {
		System.out.println("Enter Customer State : ");
		String state = sc.nextLine();
		if(cv.validateCityState(state)) {
			cd.getAddr().setState(state.toUpperCase());
		}else {
			System.out.println("Invalid customer State ");
			getState();
		}
	}
	
	public void getTransDate() {
		System.out.println("Enter Transaction Date : ");
		String date = sc.nextLine();
		if(cv.validateDate(date)) {
			LocalDate date1 = LocalDate.parse(date, DATE_FORMATTER);
			Date d = Date.valueOf(date1);
			cd.getTrans().setTransDate(d);
		}else {
			System.out.println("Invalid Transaction Date ");
			getTransDate();
		}
	}
	public void getPrinciple() {
		System.out.println("Enter Transaction Prinicple : ");
		String p = sc.next();
		if(cv.validatePrinciple(p)) {
			int principle = Integer.parseInt(p);
			cd.getTrans().setPrinciple(principle);
		}else {
			System.out.println("Invalid Transaction Principle ");
			getPrinciple();
		}
	}
	
	public void getRate() {
		System.out.println("Enter Transaction Rate : ");
		String r = sc.next();
		if(cv.validateRate(r)) {
			double rate = Double.parseDouble(r);
			cd.getTrans().setRate(rate);
		}else {
			System.out.println("Invalid Transaction Rate ");
			getRate();
		}
	}
	
	public void getTenure() {
		System.out.println("Enter Transaction Year : ");
		String y = sc.next();
		if(cv.validatePrinciple(y)) {
			int year = Integer.parseInt(y);
			cd.getTrans().setTensure(year);
		}else {
			System.out.println("Invalid Transaction Year ");
			getTenure();
		}
	}
	
	
	public void getInterset() {
		int prinicple = cd.getTrans().getPrinciple();
		double rate = cd.getTrans().getRate();
		int year = cd.getTrans().getTensure();
		double SI = (prinicple*rate*year)/100;
		
		cd.getTrans().setInterestAmount(SI);		
	}
	
	public void getTotalAmount() {
		int prinicple = cd.getTrans().getPrinciple();
		double SI = cd.getTrans().getInterestAmount();
		double total = prinicple + SI;
		cd.getTrans().setTotalAmount(total);
	}

	
	public Connection getDBConnection() throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("application");
		sURL = bundle.getString("db.url");
		sUserName = bundle.getString("db.username");
		sPwd = bundle.getString("db.password");
		driver = bundle.getString("db.driver");
		Class.forName(driver).newInstance();
		Connection conn = DriverManager.getConnection(sURL, sUserName, sPwd);
		return conn;
	}
}
