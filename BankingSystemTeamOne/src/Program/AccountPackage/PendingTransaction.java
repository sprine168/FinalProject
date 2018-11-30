package Program.AccountPackage;

import java.lang.management.MemoryUsage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PendingTransaction {
    //Todo For Checking Transactions
    protected int transactionID;
    protected int accountID;
    protected double amount;
    protected String payToOrderOf;
    protected ChronoLocalDate dateOfTransaction;
    protected DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-uuuu");

    public PendingTransaction(int transactionID, int accountID, double amount, String payToOrderOf, ChronoLocalDate dateOfTransaction) throws ParseException {
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.amount = amount;
        this.payToOrderOf = payToOrderOf;
        this.dateOfTransaction = dateOfTransaction;

        if (this.transactionID == 0){
            this.transactionID = this.toString().hashCode();
        }
    }

    public int getAccountID() {
        return accountID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public String getPayToOrderOf() {
        return payToOrderOf;
    }

    public ChronoLocalDate getDateOfTransaction() {
        return dateOfTransaction;
    }

    @Override
    public String toString() {
        return String.format("%d,%d,%2.2f,%s,%s",transactionID, accountID, amount, payToOrderOf, df.format(dateOfTransaction));
    }
}
