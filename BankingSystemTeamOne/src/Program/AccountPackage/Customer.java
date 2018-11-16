package Program.AccountPackage;

import java.util.ArrayList;

// The customer class shows information about the customer and the accounts they hold.
public class Customer {

	// Variables used for the customer.
	protected String customerId;
	protected String address;
	protected String city;
	protected String state;
	protected String zipCode;
	protected String firstName;
	protected String lastName;
	protected int atmCard = 0;
	protected ArrayList<Account> accounts;

	// Constructor for the customer.
	public Customer(String customerId, String address, String city, String state, String zipCode, String firstName,
			String lastName, int atmCard) {
		this.customerId = customerId;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.atmCard = atmCard;
		accounts = new ArrayList();
	}
	
	public int getAtmCard() { return atmCard; }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	// Method for adding an account
	public void addAccount(Account account) {
		if (account.customerId.equals(this.customerId) && !accounts.contains(account)) { // Just double check to make sure the account is owned by this person
			//System.out.println("Adding: " + account.getClass().getName() + " To: " + customerId);
			accounts.add(account);
		}
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s,%s,%d", customerId, address, city, state, zipCode, firstName, lastName, atmCard);
		//return "Customer [customerId=" + customerId + ", address=" + address + ", city=" + city + ", state=" + state
				//+ ", zipCode=" + zipCode + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	// getter for customerID
	public String getCustomerId() {
		return customerId;
	}

	// getter for account
	public ArrayList<Account> getAccounts() {
		return accounts;
	}
}