
public class Customer {
    private int customerID;
    private String customerName;
    private int phoneNo;
    private String address;

    public void setCustomerID(int id) { this.customerID = id; }
    public void setCustomerName(String name) { this.customerName = name; }
    public void setPhoneNo(int phone) { this.phoneNo = phone; }
    public void setAddress(String address) { this.address = address; }
    public int getCustomerID() { return customerID; }
    public String getCustomerName() { return customerName; }
    public int getPhoneNo() { return phoneNo; }
    public String getAddress() { return address; }
}
