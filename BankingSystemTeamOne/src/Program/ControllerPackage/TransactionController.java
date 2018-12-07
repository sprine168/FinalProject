package Program.ControllerPackage;

import Program.AccountPackage.Account;
import Program.AccountPackage.CheckingAccount;
import Program.AccountPackage.Loan;
import Program.AccountPackage.PendingTransaction;
import Program.Data.EnumeratedTypes;
import Program.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
    TransactionController is used for obtaining values from the CreateTransactions.fxml page. The controller should
    pass content to savings account class.
*/
public class TransactionController implements Initializable {

    // TODO create the rest of the controller to be in lieu of the pending transaction class.
    public Account selectedAccount;

    // Checking input variables
    public ComboBox selectCustomerAccount;
    public TextField transactionDesc;
    public TextField transactionAmount;
    public Button submitTransaction;

    // function is used for changing the gui pages and setting the new scene.
    private void function(Parent parent, ActionEvent event) {
        //function to traverse pages
        Scene homePageScene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(homePageScene);
    }

    @FXML
    void onSubmit(ActionEvent event) throws IOException, ParseException {
        if (selectedAccount != null && !transactionDesc.getText().equals(null) && !transactionAmount.getText().equals(null)){
            int accountId = 0;
            if (selectedAccount.getClass() == CheckingAccount.class){
                accountId = ((CheckingAccount) selectedAccount).getAccountId();
            }else {
                accountId = ((Loan) selectedAccount).getAccountId();
            }
            PendingTransaction newTransaction = new PendingTransaction(0, accountId, Double.parseDouble(transactionAmount.getText()), transactionDesc.getText(), LocalDate.now().plus(Period.ofDays(3)), 0);
            Main.pendingTransactions.add(newTransaction);
            returnTo(event);
        }
    }

    // returnTo checks the login type and makes sure to transfer the user to the correct area when navigating.
    @FXML
    void returnTo(ActionEvent event) throws IOException {

        if (Main.currentAuthorization != null) {

            if (Main.currentAuthorization == EnumeratedTypes.TELLER)
                function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/Submenu.fxml")), event);

            else if (Main.currentAuthorization == EnumeratedTypes.MANAGER)
                function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/SubMenu.fxml")), event);

            else if (Main.currentAuthorization == EnumeratedTypes.CUSTOMER)
                function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/Customer.fxml")), event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Main.currentCustomer != null){
            ArrayList<Account> transactableAccounts = new ArrayList();
            for (Account account : Main.currentCustomer.getAccounts()){
                if (account.getClass() == CheckingAccount.class || (account.getClass() == Loan.class && ((Loan) account).getAccountType() == "cc")){
                    transactableAccounts.add(account);
                }
            }
            CollectionController transactableAccountsController = new CollectionController(transactableAccounts);
            selectCustomerAccount.setItems(transactableAccountsController.getCollections());

            selectCustomerAccount.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    selectedAccount = (Account) newValue;
                    transactionAmount.editableProperty().setValue(true);
                    transactionDesc.editableProperty().setValue(true);
                }
            });
        }
    }
}
