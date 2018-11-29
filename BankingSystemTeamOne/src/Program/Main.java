package Program;
/*
    Banking Project Team One
    CSC-406
    Prof. Pickett

    Team Members:
    Byran Emery
    Trent Gaskill
    Steven Prine
    Kielan Sullivan
    Huaji Zhu
*/

import Program.Data.EnumeratedTypes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Program.AccountPackage.*;
import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/*
    This program is for a banking system to maintain information about customers. The customers can interact with their
    accounts. The bank tellers can interact with customer accounts, and bank managers can do everything a teller can do
    and also manipulate credit/loan accounts.
*/

public class Main extends Application {

    public static ArrayList<Customer> customers = new ArrayList();
    public static ArrayList<Account> accounts = new ArrayList();
    public static ArrayList<PendingTransaction> pendingTransactions = new ArrayList();
    public static Customer currentCustomer = null;
    public static EnumeratedTypes currentAuthorization = null;

    static class ClosingThread extends Thread {
        public void run(){
            try {
                PrintWriter writer = new PrintWriter(new File("BankingSystemTeamOne/src/Program/Data/BankingDatabase.txt"));
                writer.println("Customer Account");
                for (Customer customer: customers){
                    writer.println(customer);
                }
                writer.println();
                String AccountType = "";

                for (int i = 0; i < 3; i++){
                    if (i == 0)
                        AccountType = "Savings Account";
                    if (i == 1)
                        AccountType = "Checking Account";
                    if (i == 2)
                        AccountType = "Loan Account";

                    writer.println(AccountType);

                    for(Account account : accounts){
                        if (account.getClass().getName().contains(AccountType.substring(0, AccountType.indexOf(" ")))){
                            writer.println(account);
                        }
                    }
                    writer.println();
                }

                writer.println("Transactions Account");
                for (PendingTransaction p : pendingTransactions){
                    writer.println(p);
                }

                writer.println();
                writer.flush();
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/LoginPage.fxml"));
        primaryStage.setTitle("Not Wells Fargo Banking System");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }

    public static void main(String[] args) throws ParseException {
        Runtime.getRuntime().addShutdownHook(new ClosingThread());
        readDatabase();
        linkAccounts();
        launch(args);
    }

    public static void readDatabase() throws ParseException {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("BankingSystemTeamOne/src/Program/Data/BankingDatabase.txt")));
            String accountType = null;
            String str;
            while ((str = input.readLine()) != null){
                if (str.startsWith("//") || str.length() <= 0) continue;
                if (str.length() > 0 && str.toLowerCase().contains("account") && str.contains(" ")) {
                    accountType = str.substring(0, str.indexOf(" "));
                }
                if (str.contains(",")) {
                    String[] args = str.split(",", - 1);
                    try {
                        switch (accountType.trim()) {
                            case "Customer":
                                customers.add(new Customer(args[0], args[1], args[2], args[3], args[4], args[5],
                                        args[6], Integer.parseInt(args[7])));
                                break;
                            case "Checking":
                                accounts.add(new CheckingAccount(args[0], Integer.parseInt(args[1]), Double.parseDouble(args[2]), args[3],
                                        Integer.parseInt(args[4]), Integer.parseInt(args[5]), getDate(args[6])));
                                break;
                            case "Savings":
                                accounts.add(new SavingsAccount(args[0], Integer.parseInt(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                                        getDate(args[4]), getDate(args[5])));
                                break;
                            case "Loan":
                                accounts.add(new Loan(args[0], args[1], Double.parseDouble(args[2]),
                                        Double.parseDouble(args[3]), getDate(args[4]), getDate(args[5]),
                                        Double.parseDouble(args[6]), getDate(args[7]), Boolean.parseBoolean(args[8]), args[9]));
                                break;
                            case "Transactions":
                                pendingTransactions.add(new PendingTransaction(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Double.parseDouble(args[2]), args[3], getDate(args[4])));
                            default:
                                break;
                        }
                    } catch(NullPointerException e){

                    }
                }
            }
            input.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeAccount(Account accountToRemove){
        Customer customer = getCustomer(accountToRemove.getCustomerId());
        if (!customer.equals(null))
            customer.removeAccount(accountToRemove);
        accounts.remove(accountToRemove);
    }

    public static void linkAccounts(){
        for (Account account : accounts){
            for (Customer customer : customers){
                customer.addAccount(account);
            }
        }
    }

    public static ArrayList<PendingTransaction> fetchTransactions(int accountId){
        ArrayList<PendingTransaction> accountTransactions = new ArrayList();
        for (PendingTransaction pendingTransaction : pendingTransactions){
            if (accountId == pendingTransaction.getAccountID()){
                accountTransactions.add(pendingTransaction);
            }
        }
        return accountTransactions;
    }

    public static Customer getCustomer(String SSN){
        Customer customer = null;
        for (Customer cus : customers){
            if (cus.getCustomerId().equals(SSN)){
                customer = cus;
            }
        }
        return customer;
    }

    public static boolean addCustomer(Customer customer){
        boolean available = true;
        for (Customer customer1: customers){
            if (customer1.getCustomerId().equals(customer.getCustomerId()))
                available = false;
        }
        if (available && !customers.contains(customer))
            customers.add(customer);
        return available;
    }

    public static void addAccount(Account account){
        if (account != null && !accounts.contains(account)) {
            accounts.add(account);
            linkAccounts();
        }
    }

    public static Boolean findCustomer(String string){
        return getCustomer(string) != null;
    }

    public static ChronoLocalDate getDate(String arg) {
        if (arg.length() > 0) {
            DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("MM-dd-uuuu");
            return LocalDate.parse(arg, newFormatter);
        }
        return null;
    }
}