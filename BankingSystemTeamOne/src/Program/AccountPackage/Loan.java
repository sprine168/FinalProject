package Program.AccountPackage;

import Program.Main;
import sun.util.BuddhistCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// The loan class is an extension of accounts, that shows the loan information for customers.
public class Loan extends Account {

	// Variables for the loan class
	protected double lastPayment;
	protected String description;
	protected double currentInterestRate;
	protected ChronoLocalDate datePaymentDue;
	protected ChronoLocalDate dateNotifiedOfPayment;
	protected double currentPaymentDue;
	protected ChronoLocalDate dateSinceLastPayment;
	protected boolean missedPaymentFlag;
	protected String accountType;
	protected int identifier;

	// Constructor for the loan class.
	public Loan(String customerId, int identifier, String description, double balance, double currentInterestRate, ChronoLocalDate datePaymentDue,
				ChronoLocalDate dateNotifiedOfPayment, double currentPaymentDue, ChronoLocalDate dateSinceLastPayment, boolean missedPaymentFlag,
				String accountType) {

		super(customerId, balance, null);
		this.description = description;
		this.currentInterestRate = currentInterestRate;
		this.datePaymentDue = datePaymentDue;
		this.dateNotifiedOfPayment = dateNotifiedOfPayment;
		this.dateSinceLastPayment = dateSinceLastPayment;
		this.missedPaymentFlag = missedPaymentFlag;
		this.accountType = accountType;
		if (this.accountType.equals("ST")){this.currentPaymentDue=((balance/(5*12.0))+(balance/2)*5*this.currentInterestRate);}
		if (this.accountType.equals("LT")){this.currentPaymentDue=((balance/(30*12.0))*30*this.currentInterestRate);}
		if (this.accountType.equals("CC")){this.currentPaymentDue=((balance/(1*12.0))+(balance/2)*1*this.currentInterestRate);}
		if (identifier == 0)
			this.identifier = toString().hashCode();
		else if (identifier != 0)
			this.identifier = identifier;
	}

	@Override
	public void Deposit(double amountToDeposit){
		//Checks the date and if it is currently before the payment date, dont flag account
		if (LocalDate.now().isBefore(datePaymentDue)){
			//If correct payment is made, they are current on the account and there is no flag
			if(amountToDeposit>=currentPaymentDue){
				missedPaymentFlag = false;
			}
			lastPayment = amountToDeposit;
			balance -= amountToDeposit;
			//These are what is used to calculate the interest rate.  Each loan type has a different amount of time its due.
			//Therefore there will be different totals for each.
			if (this.accountType.equals("ST")){this.currentPaymentDue=((balance/(5*12.0))+(balance/2)*5*this.currentInterestRate);}
			if (this.accountType.equals("LT")){this.currentPaymentDue=((balance/(30*12.0))*30*this.currentInterestRate);}
			if (this.accountType.equals("CC")){this.currentPaymentDue=((balance/(1*12.0))+(balance/2)*1*this.currentInterestRate);}
			dateSinceLastPayment = LocalDate.now();
			//advanceAMonth();
		}else
		{
			//This is what happens if the payment is late
			missedPaymentFlag = true;
			lastPayment = amountToDeposit;
			balance -= amountToDeposit;
			if (this.accountType.equals("ST")){this.currentPaymentDue=((balance/(5*12.0))+(balance/2)*5*this.currentInterestRate);}
			if (this.accountType.equals("LT")){this.currentPaymentDue=((balance/(30*12.0))*30*this.currentInterestRate);}
			if (this.accountType.equals("CC")){this.currentPaymentDue=((balance/(1*12.0))+(balance/2)*1*this.currentInterestRate);}
			dateSinceLastPayment = LocalDate.now();
		}
		if (balance == 0.0){
			Main.removeAccount(this);
		}
	}

	@Override
	public double CloseAccount(ChronoLocalDate accountClosed) {
		return 0.0;
	}

    @Override
    public double Withdraw(double amountToWithdraw) {
        return 0;
    }

    public String getAccountType(){
		return accountType;
	}

	public ChronoLocalDate getDatePaymentDue(){
		return datePaymentDue;
	}

	public double getCurrentInterestRate() {
		return currentInterestRate;
	}

	public void setCurrentInterestRate(double currentInterest) {
		currentInterestRate = currentInterest;
	}

	public double getCurrentPaymentDue(){
		return currentPaymentDue;
	}

	public double getLastPayment() {return lastPayment;}

	public boolean getMissedPaymentFlag() {return missedPaymentFlag;}

	public void setMissedPaymentFlag(boolean flag) {missedPaymentFlag = flag;}

	public int getAccountId(){
		return identifier;
	}

	/** Method to simulate a month passing.  It will look at the current account balance, and interest rate and calculate a new balance.
	 *  It will also give it a new due date
	 **/
	public void advanceAMonth(){
		missedPaymentFlag=true;
		datePaymentDue = datePaymentDue.plus(Period.ofDays(30));
		//Checks the loan type and updates the balance and calculates the new current Payment Due
		if (accountType.equals("ST")){balance += currentPaymentDue; currentPaymentDue=((balance/(5*12.0))+(balance/2)*5*this.currentInterestRate); }
		if (accountType.equals("LT")){balance += currentPaymentDue; currentPaymentDue=((balance/(30*12.0))*30*this.currentInterestRate); }
		if (accountType.equals("CC")){balance += currentPaymentDue; currentPaymentDue=((balance/(1*12.0))+(balance/2)*1*this.currentInterestRate);}
	}

	/**
	 * Function to update the interest rate.  Takes in a new value for the new interest rate.  0.01 = 1%
	 */
	public void updateInterestRate(double currentInterestRate){
		//Sets the interest rate to a new interest rate.
		this.currentInterestRate = currentInterestRate;
	}

	@Override
	public String toString() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");

		return String.format("%s,%d,%s,%2.2f,%2.3f,%s,%s,%2.2f,%s,%s,%s",customerId, identifier, description, balance, currentInterestRate,
				df.format(datePaymentDue), df.format(dateNotifiedOfPayment), currentPaymentDue, df.format(dateSinceLastPayment),
				missedPaymentFlag ? "1" : "0", accountType);
	}

}
