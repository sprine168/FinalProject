package Program.AccountPackage;

import java.time.chrono.ChronoLocalDate;
import java.util.Date;

// Account holds the account information and extends to Loan, CheckingsAccounts, SavingAccounts
public abstract class Account {

	// Variables for Account
	protected String customerId;
	protected double balance;
	protected ChronoLocalDate dateOpened;

	// Constructor for Account
	public Account(String customerId, double balance, ChronoLocalDate dateOpened) {
		this.customerId = customerId;
		this.balance = balance;
		this.dateOpened = dateOpened;
	}

	// Method that allows deposits for customers
	public void Deposit(double amountToDeposit) {
		if (this.getClass().equals(Loan.class))
			balance -= amountToDeposit;
		else
			balance += amountToDeposit;
	}

	public String indicator() {
		return String.format("%2.2f", balance);
	}

	public String getCustomerId(){
		return customerId;
	}

	public double getBalance(){
		return balance;
	}
	// Method that will allow the closing of an Account

	public double CloseAccount(ChronoLocalDate accountClosed) {
		double amountToReturn = 0;
		if (this.getClass() != Loan.class) {
			if (this.getClass() == SavingsAccount.class){
				this.CloseAccount(accountClosed);
			}
			amountToReturn = balance;
			balance = 0;
		}else{

		}
		return amountToReturn;
	}

}