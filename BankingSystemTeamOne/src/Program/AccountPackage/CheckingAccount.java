package Program.AccountPackage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// CheckingAccount is an extension of Account, and allows for customer to have a Checking Account.
public class CheckingAccount extends Account {

	// Variables for the CheckingAccount
	protected String accountType;
	protected String backupId;
	protected int numberOfOverdrafts;

	// Constructor for CheckingAccount.
	public CheckingAccount(String customerId, double balance, String accountType, String backupId,
			int numberOfOverdrafts, Date dateOpened) {

		super(customerId, balance, dateOpened);

		this.accountType = accountType;
		this.backupId = backupId;
		this.numberOfOverdrafts = numberOfOverdrafts;
	}

	public String getAccountType(){
		return accountType;
	}

	@Override
	public String toString(){
		DateFormat df = new SimpleDateFormat("mm-dd-yyyy");
		return String.format("%s,%2.2f,%s,%s,%d,%s",customerId,balance,accountType,backupId,numberOfOverdrafts,df.format(dateOpened));
	}
}
