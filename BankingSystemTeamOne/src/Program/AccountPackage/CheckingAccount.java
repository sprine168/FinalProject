package Program.AccountPackage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// CheckingAccount is an extension of Account, and allows for customer to have a Checking Account.
public class CheckingAccount extends Account {

	// Variables for the CheckingAccount
	protected String accountType;
	protected int backupId;
	protected int numberOfOverdrafts;
	protected int identifier;

	// Constructor for CheckingAccount.
	public CheckingAccount(String customerId,int identifier, double balance, String accountType, int backupId,
			int numberOfOverdrafts, Date dateOpened) {

		super(customerId, balance, dateOpened);

		this.accountType = accountType;
		this.backupId = backupId;
		this.numberOfOverdrafts = numberOfOverdrafts;

		if (identifier == 0)
			this.identifier = toString().hashCode();
		else if (identifier != 0)
			this.identifier = identifier;
	}

	//Withdraw from Checking account, check to see if it has a backupAccount;
	public double Withdraw(double amountToWithdraw){
		double amountToReturn = 0.0;
		if (backupId != 0){
			if (balance >= amountToWithdraw){
				balance -= amountToWithdraw;
				amountToReturn = amountToWithdraw;
			}else if (balance < amountToWithdraw){
				double amt = (amountToReturn - balance);
				balance = 0;

			}
		}
		return amountToReturn;
	}

	public String getAccountType(){
		return accountType;
	}

	@Override
	public String toString(){
		DateFormat df = new SimpleDateFormat("mm-dd-yyyy");
		return String.format("%s,%d,%2.2f,%s,%d,%d,%s",customerId,identifier,balance,accountType,backupId,numberOfOverdrafts,df.format(dateOpened));
	}
}
