package Program.AccountPackage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/* SavingsAccount is an extension of accounts and holds the saving account information for customers */
public class SavingsAccount extends Account {

	// Variables for SavingsAccount
	private static double currentInterestRate;
	private Date CDDue;

	// Constructor for the SavingsAccount
	public SavingsAccount(String customerId, double balance, double currentInterestRate, Date dateOpened, Date CDDue) {
		super(customerId, balance, dateOpened);
		this.currentInterestRate = currentInterestRate;
		this.CDDue = CDDue;
	}

	public double Withdraw(double amountToWithdraw){
		if (balance >= amountToWithdraw){
			balance = balance - amountToWithdraw;
		}else {
			amountToWithdraw = 0.0;
		}
		return amountToWithdraw;
	}

	public double Withdraw(Date dateOfWithdraw, double penaltyPercent){
		double amountToWithdraw = 0.0;
		if (isCD()){
			if (dateOfWithdraw.before(CDDue)){
				amountToWithdraw = balance - (balance * penaltyPercent);
				balance = 0;
			} else {
				amountToWithdraw = balance;
				balance = 0;
			}
		}
		return amountToWithdraw;
	}

	public boolean isCD(){
		return this.CDDue != null;
	}

	@Override
	public String toString(){
		DateFormat df = new SimpleDateFormat("mm-dd-yyyy");
		return String.format("%s,%2.2f,%2.2f,%s,%s", customerId, balance, currentInterestRate, dateOpened != null ? df.format(dateOpened) : "",CDDue != null ? df.format(CDDue) : "");
	}
}