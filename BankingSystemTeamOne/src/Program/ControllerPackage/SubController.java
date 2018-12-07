package Program.ControllerPackage;

import Program.AccountPackage.*;
import Program.Data.EnumeratedTypes;
import Program.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.nio.cs.HistoricallyNamedCharset;

import javax.swing.*;
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
    public TextField selectedAccountBalance;
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
    public TextField amountToTransfer;

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

    @FXML
    protected ListView checkingAccountTransactions;

    @FXML
    protected ListView creditCardTransactions;

    @FXML
    void advanceAMonth(ActionEvent event) throws IOException {
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
        function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/SubMenu.fxml"))), event);

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
            manCusSelect.getItems().remove(selectedAccount3);
            function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/SubMenu.fxml")), event);
        }
    }

    @FXML
    void Deposit(ActionEvent event) throws IOException {
        if (selectedAccount != null){
            double amountToDeposit = Double.parseDouble(amountToTransfer.getText());
            if (amountToDeposit > 0){
                selectedAccount.Deposit(amountToDeposit);
            }
            selectedAccountBalance.setText(String.format("$%2.2f", selectedAccount.getBalance()));
            System.out.println("Deposit made");
            //Resets the page so new info can be displayed
            function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/SubMenu.fxml"))), event);

        }
    }

    @FXML
    void Withdraw(ActionEvent event) throws IOException {
        //checks to see if there is an account to withdraw from
        if (selectedAccount != null && !amountToTransfer.getText().equals(null)){
            //Grabs the amount from the textfield
            double amountToWithdraw = Double.parseDouble(amountToTransfer.getText());
            if (amountToWithdraw > 0){
                //perfroms the appropriate withdraw
                selectedAccount.Withdraw(amountToWithdraw);
            }
            selectedAccountBalance.setText(String.format("$%2.2f", selectedAccount.getBalance()));
            //Resets the page so new info can be displayed
            function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/SubMenu.fxml"))), event);
        }
    }

    @FXML
    void transferFunds(ActionEvent event) throws IOException
    {
        //Checks to make sure both accounts are selected
        if (selectedAccount != null && selectedAccount2 != null){
            //Grabs the amount to transfer from the textfield
            double amountToTransfer1 = !(amountToTransfer.getText().equals(null) || amountToTransfer.getText().equals("")) ?  Double.parseDouble(amountToTransfer.getText()) : 0.00;
            if (amountToTransfer1 > 0 ) {
                    double amt = selectedAccount.Withdraw(amountToTransfer1);
                    selectedAccount2.Deposit(amt);
            }
            function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/SubMenu.fxml"))), event);
        }
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
                    if (newLoan.getMissedPaymentFlag()){
                        missedPayment.setText("Yes");
                        cusAccountStatus.setText("Late");
                    }else{
                        missedPayment.setText("No");
                        cusAccountStatus.setText("Current");
                    }
                    if (newLoan.getAccountType().equals("CC")){
                        ArrayList<PendingTransaction> pendingTransactions = Main.fetchTransactions(newLoan.getIdentifier());
                        CollectionController newController = new CollectionController(pendingTransactions);

                        creditCardTransactions.setItems(newController.getCollections());

                        creditCardTransactions.setCellFactory(lv -> new ListCell<PendingTransaction>()
                        {
                            @Override
                            public void updateItem(PendingTransaction item, boolean empty)
                            {
                                super.updateItem(item, empty);
                                if (!empty) {
                                    setText(item.toString());
                                    setOnMouseClicked(mouseClickedEvent -> {
                                        if (mouseClickedEvent.getButton().equals(MouseButton.PRIMARY) && mouseClickedEvent.getClickCount() == 2) {
                                            if (item.getIsStopped() != 1 && (item.getDateOfTransaction().equals(null) || item.getDateOfTransaction().isAfter(LocalDate.now()))) {
                                                item.setIsStopped(1);
                                                setText(item.toString());
                                                super.updateItem(item, empty);
                                            }
                                        }
                                    });
                                } else {
                                    setText(null);
                                    setGraphic(null);
                                }
                            }
                        });
                    } else {
                        checkingAccountTransactions.setItems(null);
                        checkingAccountTransactions.setCellFactory(null);

                    }
                    if (LocalDate.now().isBefore(newLoan.getDatePaymentDue())){
                        notifiedOfPayment.setText("No");
                    }else{
                        notifiedOfPayment.setText("Yes");
                    }
                    currentLoanAccount = newLoan;
                    currentLoanBalance.setText(String.format("%2.2f", newLoan.getBalance()));
                    String s = "%2.2f\037";
                    currentLoanRate.setText(String.format(s,(newLoan.getCurrentInterestRate() * 100.0)));
                    datePaymentDue.setText(df.format(newLoan.getDatePaymentDue()));
                    currentLoanPaymentDue.setText(String.format("%2.2f", newLoan.getCurrentPaymentDue()));
                    lastPaymentMade.setText(String.format("%2.2f", newLoan.getLastPayment()));
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
                    selectedAccountBalance.setText(String.format("$%2.2f", selectedAccount.getBalance()));
                    if (selectedAccount.getClass() == CheckingAccount.class){
                        ArrayList<PendingTransaction> pendingTransactions = Main.fetchTransactions(((CheckingAccount) selectedAccount).getAccountId());
                        CollectionController newController = new CollectionController(pendingTransactions);

                        checkingAccountTransactions.setItems(newController.getCollections());

                        checkingAccountTransactions.setCellFactory(lv -> new ListCell<PendingTransaction>()
                        {
                            @Override
                            public void updateItem(PendingTransaction item, boolean empty)
                            {
                                super.updateItem(item, empty);
                                if (!empty) {
                                    setText(item.toString());
                                    setOnMouseClicked(mouseClickedEvent -> {
                                        if (mouseClickedEvent.getButton().equals(MouseButton.PRIMARY) && mouseClickedEvent.getClickCount() == 2) {
                                            if (item.getIsStopped() != 1 && (item.getDateOfTransaction().equals(null) || item.getDateOfTransaction().isAfter(LocalDate.now()))) {
                                                item.setIsStopped(1);
                                                setText(item.toString());
                                                super.updateItem(item, empty);
                                            }
                                        }
                                    });
                                } else {
                                    setText(null);
                                    setGraphic(null);
                                }
                            }
                        });
                    } else {
                        checkingAccountTransactions.setItems(null);
                        checkingAccountTransactions.setCellFactory(null);
                    }
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

    // navigation button to go to the create transaction page.
    public void createTransaction(ActionEvent event) throws Exception {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/CreateTransactions.fxml")), event);
    }

}