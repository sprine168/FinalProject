package Program.ControllerPackage;

import Program.AccountPackage.*;
import Program.Data.EnumeratedTypes;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class SubController implements Initializable {

    private Loan currentLoanAccount;
    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    private Account selectedAccount; // Account to Transfer/Withdraw/Deposit From/To
    private Account selectedAccount2;// Account to Transfer To
    private Account selectedAccount3;// Account to Close.

    public Button logoutButton;
    public Button returnMenu;
    public Button closeAccountButton;
    public Button withdrawButton;
    public Button depositButton;
    public Button transferButton;
    public Button closeCustomerButton;

    public TextArea cusCheckingStatement;
    public TextArea cusLoanStatement;

    public TextField customerIDText;
    public TextField cusAccountStatus;
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
    public ComboBox accountToTransferTo;
    public ComboBox accountToTransferFrom;
    public ComboBox accountToClose;

    public Label accountToCloseLabel;
    public Pane LoanPane;

    protected CollectionController checkingsCollection;
    protected CollectionController allAccountsCollection;
    protected CollectionController loanCollection;
    protected CollectionController nonLoanCollection;
    protected CollectionController savingsCollection;

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
    void returnTo(ActionEvent event) throws IOException {
        if (Main.currentAuthorization != null) {
            if (Main.currentAuthorization == EnumeratedTypes.TELLER)
                function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/TellerMainMenu.fxml"))), event);
            else if (Main.currentAuthorization == EnumeratedTypes.MANAGER)
                function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/ManagerMainMenu.fxml"))), event);
        }
    }

    @FXML
    void closeAccount(ActionEvent event) throws IOException
    {
        if (selectedAccount3 != null){
            double amountToGive = selectedAccount3.CloseAccount(LocalDate.now());
            System.out.println(String.format("%2.2f", amountToGive));
            function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/SubMenu.fxml")), event);
        }
    }

    @FXML
    void Deposit(ActionEvent event) throws IOException {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //grabs the customer from the previous drop down menu
        Customer customer = Main.currentCustomer;
        switch(Main.currentAuthorization){
            case MANAGER:
                LoanPane.visibleProperty().setValue(true);
                break;
            case TELLER:
                LoanPane.visibleProperty().setValue(false);
                break;
            default:
                break;
        }
        if (customer != null) {
            customerIDText.setText(customer.getCustomerId());
            manCusName.setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
            manCusAddress.setText(customer.getAddress());
            manCusState.setText(customer.getState());
            manCusZip.setText(customer.getZipCode());
            manCusAtm.setText(customer.getAtmCard() == 1 ? "Yes" : "No");
            ArrayList<Account> nonLoanAccounts = new ArrayList();
            ArrayList<Account> loanAccounts = new ArrayList();
            ArrayList<Account> allAccounts = new ArrayList();

            for (Account account : customer.getAccounts()) {
                if (!account.getClass().equals(Loan.class)) {
                    nonLoanAccounts.add(account);
                    allAccounts.add(account);
                } else {
                    loanAccounts.add(account);
                    allAccounts.add(account);
                }
            }

            loanCollection = new CollectionController(loanAccounts);
            manCusSelect.setItems(loanCollection.getCollections());

            nonLoanCollection = new CollectionController(nonLoanAccounts);
            accountToTransferFrom.setItems(nonLoanCollection.getCollections());

            allAccountsCollection = new CollectionController(allAccounts);
            accountToTransferTo.setItems(allAccountsCollection.getCollections());

            accountToClose.setItems(nonLoanCollection.getCollections());

            manCusSelect.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    Loan newLoan = (Loan) newValue;
                    currentLoanAccount = newLoan;
                    currentLoanBalance.setText(String.format("%2.2f", newLoan.getBalance()));
                    String s = "%2.2f\037";
                    currentLoanRate.setText(String.format(s,(newLoan.getCurrentInterestRate() * 100.0)));
                    datePaymentDue.setText(df.format(newLoan.getDatePaymentDue()));
                    currentLoanPaymentDue.setText(String.format("%2.2f", newLoan.getCurrentPaymentDue()));
                }
            });

            accountToTransferFrom.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    selectedAccount = (Account) newValue;
                    ArrayList<Account> newArray = (ArrayList<Account>) allAccounts.clone();
                    newArray.remove(selectedAccount);

                    allAccountsCollection = new CollectionController(newArray);
                    accountToTransferTo.setItems(allAccountsCollection.getCollections());
                }
            });

            accountToTransferTo.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    selectedAccount2 = (Account) newValue;
                }
            });

            accountToClose.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    selectedAccount3 = (Account) newValue;
                }
            });
        }
    }
}