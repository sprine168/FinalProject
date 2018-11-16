package Program.ControllerPackage;

import Program.AccountPackage.Account;
import Program.AccountPackage.CheckingAccount;
import Program.AccountPackage.Customer;
import Program.AccountPackage.Loan;
import Program.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerSubController implements Initializable {

    public String tellerChecking;       // This is for the comboBox. Note: For selecting account type.
    public String tellerSaving;         // This is for the comboBox. Note: For selecting account type.
    private Loan currentLoanAccount;
    DateFormat df = new SimpleDateFormat("mm-dd-yyyy");

    public Button logoutButton;
    public Button returnMenu;
    public Button closeAccountButton;
    public Button withdrawButton;
    public Button depositButton;
    public Button paymentButton;
    public Button transferButton;

    public TextArea cusAccountStatement;
    public TextArea cusCheckingStatement;
    public TextArea cusLoanStatement;

    public TextField deposit;
    public TextField transfer;
    public TextField withdraw;
    public TextField payment;
    public TextField customerIDText;
    public TextField checkingBalanceText;
    public TextField cusAccountStatus;
    public TextField cusLoanBalance;
    public TextField setInterestRate;
    public TextField currentLoanRate;
    public TextField datePaymentDue;
    public TextField lastPaymentMade;
    public TextField currentLoanBalance;
    public TextField notifiedOfPayment;
    public TextField missedPayment;
    public TextField currentLoanPaymentDue;
    public TextField manCusName;
    public TextField manCusAddress;
    public TextField manCusState;
    public TextField manCusZip;
    public TextField manCusAtm;

    public ComboBox manCusSelect;

    @FXML
    void advanceAMonth(){
        if (!currentLoanAccount.equals(null)){

            //On button click, advance the loan a month.  This allows us to watch interst rise automatically
            currentLoanAccount.advanceAMonth();
            datePaymentDue.setText(df.format(currentLoanAccount.getDatePaymentDue()));
            //Set the current amount due
            currentLoanPaymentDue.setText(String.format("%2.2f", currentLoanAccount.getCurrentPaymentDue()));
            //Set the loan balance to what the customer owes on that loan
            currentLoanBalance.setText(String.format("%2.2f", currentLoanAccount.getBalance()));
            cusLoanBalance.setText(String.format("%2.2f", currentLoanAccount.getBalance()));
            //Signals that a month has passed and the customer has been notified
            notifiedOfPayment.setText("Yes");
        }
    }

    @FXML
    void updateInterestRate(){
        //Checks to see if there is a valid account
        if(!currentLoanAccount.equals(null))
        {
            //on Valid account, grabs the new interest rates and updates it
            String text = setInterestRate.getText(); // example String
            double value = Double.parseDouble(text);
            currentLoanAccount.updateInterestRate(value);
            double value1 = currentLoanAccount.getCurrentInterestRate()*100;
            String str = value1 + "";
            //Returns the updated interest rate
            currentLoanRate.setText("%"+str);
        }
    }

    private void function(Parent parent, ActionEvent event) {
        Scene homePageScene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(homePageScene);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/LoginPage.fxml")), event);
    }

    @FXML
    void returnMan(ActionEvent event) throws IOException {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/ManagerMainMenu.fxml")), event);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //grabs the customer from the previous drop down menu
        Customer customer = Main.currentCustomer;
        if (customer != null) {
            customerIDText.setText(customer.getCustomerId());
            manCusName.setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
            manCusAddress.setText(customer.getAddress());
            manCusState.setText(customer.getState());
            manCusZip.setText(customer.getZipCode());
            manCusAtm.setText(customer.getAtmCard() == 1 ? "Yes" : "No");
            boolean foundNonLoanAccount = false;
            ArrayList<Account> nonLoanAccounts = new ArrayList();
            ArrayList<Account> loanAccounts = new ArrayList();
            for (Account account : customer.getAccounts()) {
                if (!account.getClass().equals(Loan.class)) {
                    foundNonLoanAccount = true;
                    nonLoanAccounts.add(account);
                } else {
                    loanAccounts.add(account);
                }
            }
            CollectionController loanCollection = new CollectionController(loanAccounts);
            manCusSelect.setItems(loanCollection.getCollections());

            manCusSelect.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    Loan newLoan = (Loan) newValue;
                    currentLoanAccount = newLoan;
                    currentLoanBalance.setText(String.format("%2.2f", newLoan.getBalance()));
                    cusLoanBalance.setText(String.format("%2.2f", newLoan.getBalance()));
                    String s = "%2.2f\037";
                    currentLoanRate.setText(String.format(s,(newLoan.getCurrentInterestRate() * 100.0)));
                    datePaymentDue.setText(df.format(newLoan.getDatePaymentDue()));
                    currentLoanPaymentDue.setText(String.format("%2.2f", newLoan.getCurrentPaymentDue()));
                }
            });
            if (!foundNonLoanAccount) {
                checkingBalanceText.setText("N/A");
            }
        }
    }
}