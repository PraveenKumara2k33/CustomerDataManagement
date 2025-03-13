package CustomerDataManagement;

public class CustomerDetials {
	private int custId;
	private String custName;
	private long phone;
	private String email;
	private AddressDetials addr;
	private Transaction trans;
	
	
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public AddressDetials getAddr() {
		return addr;
	}
	public void setAddr(AddressDetials addr) {
		this.addr = addr;
	}
	public Transaction getTrans() {
		return trans;
	}
	public void setTrans(Transaction trans) {
		this.trans = trans;
	}
}
