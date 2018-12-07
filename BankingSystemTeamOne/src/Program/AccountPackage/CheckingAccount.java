package Program.AccountPackage;

import Program.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
			int numberOfOverdrafts, ChronoLocalDate dateOpened) {

		super(customerId, balance, dateOpened);

		//If the balance is above $1000, automatically set the type to gold
		this.accountType = balance >= 1000 ? "gold" : "regular";
//		this.accountType = accountType;
		this.backupId = backupId;
		this.numberOfOverdrafts = numberOfOverdrafts;

		if (identifier == 0)
			this.identifier = toString().hashCode();
		else if (identifier != 0)
			this.identifier = identifier;


	}

	//Withdraw from Checking account, check to see if it has a backupAccount;
	@Override
	public double Withdraw(double amountToWithdraw){
		double amountToReturn = 0.0;
		if (backupId != 0){
			System.out.println(amountToWithdraw);
			if (balance >= amountToWithdraw){
				balance -= amountToWithdraw;
				amountToReturn = amountToWithdraw;
			}else if (balance < amountToWithdraw){
				double prevBalance = balance;
				double amt = Math.abs(amountToWithdraw - balance);
				balance = 0;
				Account backupAccount = null;
				for (Account account : Main.getCustomer(customerId).getAccounts()){
					if (account.getClass().equals(SavingsAccount.class)){
						if (((SavingsAccount) account).identifier == backupId){
							backupAccount = account;
						}
					}
				}
				if (backupAccount != null){
					backupAccount.Withdraw(amt);
				}else{
					System.out.println("Couldn't find account, cancelling transaction");
					balance = prevBalance;
				}
			}
		} else {
			if (balance >= amountToWithdraw){
				balance -= amountToWithdraw;
				amountToReturn = amountToWithdraw;
			}
		}
		return amountToReturn;
	}

	@Override
	public void Deposit(double amountToDeposit) {
		balance += amountToDeposit;
		setAccountType();
	}

	@Override
	public double CloseAccount(ChronoLocalDate date){
		double amt = balance;
		balance = 0;
        ArrayList<PendingTransaction> pendingTransactions = Main.fetchTransactions(getAccountId());
        for (PendingTransaction pendingTransaction : pendingTransactions){
            Main.pendingTransactions.remove(pendingTransaction);
        }
		Main.removeAccount(this);
		return amt;
	}


	//Checks the balance and sets the account type accordingly
	public void setAccountType(){
		accountType = balance >= 1000 ? "gold" : "regular";
	}

	public String getAccountType(){
		return accountType;
	}

	@Override
	public String toString(){
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		return String.format("%s,%d,%2.2f,%s,%d,%d,%s",customerId,identifier,balance,accountType,backupId,numberOfOverdrafts,df.format(dateOpened));
	}

    public int getAccountId() {
		return identifier;
    }
}
