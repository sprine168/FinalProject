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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Program.AccountPackage.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/*
    TODO Come up with a better description for what the program is designed to do...
    This program is for a banking system to maintain information about customers. The customers can interact with their
    accounts. The bank tellers can interact with customer accounts, and bank managers can do everything a teller can do
    and more.
*/

public class Main extends Application {
    public static Stage primaryStage;
    public static ArrayList<Customer> customers = new ArrayList();
    public static ArrayList<Account> accounts = new ArrayList();
    public static Customer currentCustomer = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/LoginPage.fxml"));
        primaryStage.setTitle("Not Wells Fargo Banking System");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }


    public static java.util.Date getDate(String arg) {
        DateFormat df = new SimpleDateFormat("mm-dd-yyyy");
        java.util.Date date = null;
        try {
            date = df.parse(arg);
        } catch (ParseException e) {

        }
        return date;
    }

    public static void main(String[] args) {
        readDatabase();
        launch(args);
    }

    public static void readDatabase(){
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("BankingSystemTeamOne/src/Program/Data/BankingDatabase.txt")));
            String accountType = null;
            String str;
            while ((str = input.readLine()) != null){
                if (str.startsWith("//") || str.length() <= 0) continue;
                if (str.length() > 0 && str.toLowerCase().contains("account") && str.contains(" ")) {
                    accountType = str.substring(0, str.indexOf(" "));
                    System.out.println(accountType);
                }
                if (str.contains(",")) {
                    String[] args = str.split(",", - 1);
                    try {
                        switch (accountType.trim()) {
                            case "Customer":
                                customers.add(new Customer(args[0], args[1], args[2], args[3], args[4], args[5], args[6], Integer.parseInt(args[7])));
                                break;
                            case "Checking":
                                accounts.add(new CheckingAccount(args[0], Double.parseDouble(args[1]),
                                        args[2], args[3], Integer.parseInt(args[4]), getDate(args[5])));
                                break;
                            case "Savings":
                                accounts.add(new SavingsAccount(args[0], Double.parseDouble(args[1]),
                                        Double.parseDouble(args[2]), getDate(args[3]), getDate(args[4])));
                                break;
                            case "Loan":
                                accounts.add(new Loan(args[0], args[1], Double.parseDouble(args[2]),
                                        Double.parseDouble(args[3]), getDate(args[4]), getDate(args[5]),
                                        Double.parseDouble(args[6]), getDate(args[7]), Boolean.parseBoolean(args[8]), args[9]));
                                break;
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

    public static Stage getPrimaryStage() {
        return Main.primaryStage;
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
}