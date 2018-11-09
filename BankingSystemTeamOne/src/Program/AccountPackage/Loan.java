package Program.AccountPackage;

import sun.util.BuddhistCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// The loan class is an extension of accounts, that shows the loan information for customers.
public class Loan extends Account {

	// Variables for the loan class
	protected String description;
	protected double currentInterestRate;
	protected Date datePaymentDue;
	protected Date dateNotifiedOfPayment;
	protected double currentPaymentDue;
	protected Date dateSinceLastPayment;
	protected boolean missedPaymentFlag;
	protected String accountType;

	// Constructor for the loan class.
	public Loan(String customerId, String description, double balance, double currentInterestRate, Date datePaymentDue,
			Date dateNotifiedOfPayment, double currentPaymentDue, Date dateSinceLastPayment, boolean missedPaymentFlag,
			String accountType) {

		super(customerId, balance, null);

		this.description = description;
		this.currentInterestRate = currentInterestRate;
		this.datePaymentDue = datePaymentDue;
		this.dateNotifiedOfPayment = dateNotifiedOfPayment;
		this.currentPaymentDue = currentPaymentDue;
		this.dateSinceLastPayment = dateSinceLastPayment;
		this.missedPaymentFlag = missedPaymentFlag;
		this.accountType = accountType;
	}

	public String getAccountType(){
		return accountType;
	}

	public Date getDatePaymentDue(){
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

	public void advanceAMonth(){
		Calendar c = new GregorianCalendar();
		c.setTime(datePaymentDue);
		c.add(Calendar.DATE, 30);
		datePaymentDue = c.getTime();
		currentPaymentDue = balance * Math.pow(1+(currentInterestRate/12.0), 12) / 12;
	}

	public void updateInterestRate(double currentInterestRate){
		this.currentInterestRate = currentInterestRate;
		currentPaymentDue = balance * Math.pow(1+(currentInterestRate/12.0), 12) / 12;
	}
	
	@Override
    public String toString() {
		DateFormat df = new SimpleDateFormat("mm-dd-yyyy");
		return String.format("%s,%s,%2.2f,%2.3f,%s,%s,%2.2f,%s,%s,%s",customerId, description, balance, currentInterestRate,
				df.format(datePaymentDue), df.format(dateNotifiedOfPayment), currentPaymentDue, df.format(dateSinceLastPayment),
				missedPaymentFlag ? "1" : "0", accountType);
    }

}
