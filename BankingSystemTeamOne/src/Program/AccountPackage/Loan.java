package Program.AccountPackage;

import java.util.Date;

// The loan class is an extension of accounts, that shows the loan information for customers.
public class Loan extends Account {

	// Variables for the loan class
	protected String description;
	protected static double currentInterestRate;
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

	public double getCurrentInterestRate() {
		return currentInterestRate;
	}

	public static void setCurrentInterestRate(double currentInterest) {
		currentInterestRate = currentInterest;
	}

}