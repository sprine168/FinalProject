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
	public abstract void Deposit(double amountToDeposit);

	public String getCustomerId(){
		return customerId;
	}

	public double getBalance(){
		return balance;
	}
	// Method that will allow the closing of an Account

	public abstract double CloseAccount(ChronoLocalDate accountClosed);

	public abstract double Withdraw(double amountToWithdraw);
}