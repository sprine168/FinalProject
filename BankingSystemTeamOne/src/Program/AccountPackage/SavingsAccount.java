package Program.AccountPackage;

import Program.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/* SavingsAccount is an extension of accounts and holds the saving account information for customers */
public class SavingsAccount extends Account {

	// Variables for SavingsAccount
	protected static double currentInterestRate;
	protected ChronoLocalDate CDDue;
	protected int identifier;

	// Constructor for the SavingsAccount
	public SavingsAccount(String customerId, int identifier, double balance, double currentInterestRate, ChronoLocalDate dateOpened, ChronoLocalDate CDDue) {
		super(customerId, balance, dateOpened);
		this.currentInterestRate = currentInterestRate;
		this.CDDue = CDDue;

		if (identifier == 0)
			this.identifier = toString().hashCode();
		else if (identifier != 0)
			this.identifier = identifier;
	}

	public double Withdraw(double amountToWithdraw){
		if (isCD()) return 0.0;
		if (balance >= amountToWithdraw){
			balance -= amountToWithdraw;
		}else {
			amountToWithdraw = 0.0;
		}
		return amountToWithdraw;
	}

	@Override
	public double CloseAccount(ChronoLocalDate dateOfClose){
		double amountToWithdraw = 0.0;
		if (isCD()){
			if (dateOfClose.isBefore(CDDue)){
				amountToWithdraw = balance;
				balance = 0;
			} else {
				amountToWithdraw =  balance + balance * (currentInterestRate / 100); // insert equation for interest over the set time;
				balance = 0;
			}
		}else{
			amountToWithdraw = balance + balance * (currentInterestRate / 100);
			balance = 0;
		}
		Main.removeAccount(this);
		return amountToWithdraw;
	}


	public boolean isCD(){
		return this.CDDue != null;
	}

	@Override
	public String toString(){
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		return String.format("%s,%d,%2.2f,%2.2f,%s,%s", customerId, identifier, balance, currentInterestRate, dateOpened == null ? "" : df.format(dateOpened), isCD() ? df.format(CDDue) : "");
	}
}