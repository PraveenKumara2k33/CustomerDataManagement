package CustomerDataManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class InterestDAO {
	CustomerDetialsActionMain cdm = new CustomerDetialsActionMain();
	int addrID;
	int custID;
	int transID;
	CustomerDetials cd1;
	public void saveAddress(AddressDetials add) {
		try {
			Connection conn = cdm.getDBConnection();
//			String query ="insert into address_tb1(addr_id, addr_line1, addr_line2, addr_line3, city, state) values (?,?,?,?,?,?)";//Oracle 11g
			String query = "INSERT INTO address_tb1 (addr_line1, addr_line2, addr_line3, city, state) " +
		               "VALUES ('" + add.getAddr_line1() + "', '" + add.getAddr_line2() + "', '" + add.getAddr_line3() + "', '" + add.getCity() + "', '" + add.getState() + "')";

			PreparedStatement pt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			/* //Oracle 11g
			pt.setInt(1, addressId);
			pt.setString(2, address1);
			pt.setString(3, address2);
			pt.setString(4, address3);
			pt.setString(5, city);
			pt.setString(6, state);
			*/
			int n = pt.executeUpdate();
			if(n>0) {
				ResultSet rs = pt.getGeneratedKeys();
				if(rs.next()) {
					addrID = rs.getInt(1);
					System.out.println("Generated Address ID: " + addrID);
				}		
			}
			System.out.println(query);
			System.out.println(n+" rows inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveCustomer(CustomerDetials cd) {
		try {
			Connection conn = cdm.getDBConnection();
//			String query ="insert into customer_tb1(CUST_ID, CUST_NAME, PHONE, EMAIL,  ADDR_ID) values (?,?,?,?,?)";//Oracle 11g
			String query = "INSERT INTO customer_tb1 (CUST_NAME, PHONE, EMAIL, ADDR_ID) " +
		               "VALUES ('" + cd.getCustName() + "', " + cd.getPhone() + ", '" + cd.getEmail() + "', " + cd.getAddr().getAddressId() + ")";

			PreparedStatement pt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			/*//Oracle 11g
			pt.setInt(1, custId);
			pt.setString(2, custName);
			pt.setLong(3, phone);
			pt.setString(4, email);
			pt.setInt(5, addressId);
			*/
			int n = pt.executeUpdate();
			System.out.println(query);
			if(n>0) {
				ResultSet rs = pt.getGeneratedKeys();
				if(rs.next()) {
					custID = rs.getInt(1);
					System.out.println("Generated Customer ID: " + custID);
				}		
			}
			
			System.out.println(n+" rows inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveTransaction(Transaction trans) {
		try {
			Connection conn = cdm.getDBConnection();
//			String query ="insert into transaction_tb1(TRANS_ID, TRANS_DATE, PRINCIPLE_AMOUNT, RATE_OF_INTEREST, NO_OF_TENURE,  INTEREST_AMT, TOTAL_AMT, CUST_ID) values (?,?,?,?,?,?,?,?)";//Oracle 11g
			String query = "INSERT INTO transaction_tb1 (TRANS_DATE, PRINCIPLE_AMOUNT, RATE_OF_INTEREST, NO_OF_TENURE, INTEREST_AMT, TOTAL_AMT, CUST_ID) " +
		               "VALUES ('" + trans.getTransDate() + "', " + trans.getPrinciple() + ", " + trans.getRate() + ", " + trans.getTensure() + ", " + trans.getInterestAmount() + ", " + trans.getTotalAmount() + ", " + trans.getCustId() + ")";

			PreparedStatement pt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			/*//Oracle 11g
			pt.setInt(1, transID);
			pt.setDate(2, tranDate);
			pt.setInt(3, principal);
			pt.setDouble(4, rate);
			pt.setInt(5, year);
			pt.setDouble(6, interset_amt);
			pt.setDouble(7, total_amt);
			pt.setInt(8, cust_ID);
			*/
			int n = pt.executeUpdate();
			System.out.println(query);
			if(n>0) {
				ResultSet rs = pt.getGeneratedKeys();
				if(rs.next()) {
					transID = rs.getInt(1);
					System.out.println("Generated Transaction ID: " + transID);
				}		
			}		
			System.out.println(n+" rows inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
