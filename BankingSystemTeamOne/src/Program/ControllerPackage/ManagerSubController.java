package Program.ControllerPackage;

import Program.AccountPackage.Account;
import Program.AccountPackage.CheckingAccount;
import Program.AccountPackage.Customer;
import Program.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerSubController implements Initializable {

    @FXML
    public Button logoutButton;

    @FXML
    public Button returnMenu;

    @FXML
    public Button closeAccountButton;

    @FXML
    public Button withdrawButton;

    @FXML
    public Button depositButton;

    @FXML
    public Button paymentButton;

    @FXML
    public Button transferButton;

    @FXML
    public TextField deposit;

    @FXML
    public TextField transfer;

    @FXML
    public TextField withdraw;

    @FXML
    public TextField payment;

    @FXML
    public TextField customerIDText;

    @FXML
    public TextField checkingBalanceText;

    @FXML
    public TextField cusAccountStatus;

    @FXML
    public TextField cusLoanBalance;

    @FXML
    public Button depositButton1;

    @FXML
    public String tellerChecking;       // This is for the comboBox. Note: For selecting account type.

    @FXML
    public String tellerSaving;         // This is for the comboBox. Note: For selecting account type.

    @FXML
    public TextField setInterestRate;

    @FXML
    public Button advanceInterestRate;

    @FXML
    public TextArea cusAccountStatement;

    @FXML
    public Button advanceMonth;

    @FXML
    public TextField currentLoanRate;

    @FXML
    public DatePicker datePaymentDue;

    @FXML
    public DatePicker lastPaymentMade;

    @FXML
    public TextField currentLoanBalance;

    @FXML
    public DatePicker notifiedOfPayment;

    @FXML
    public TextField missedPayment;

    @FXML
    public TextArea cusCheckingStatement;

    @FXML
    public TextField currentLoanPaymentDue;

    @FXML
    public TextArea cusLoanStatement;


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
        // TODO Auto-generated method stub
        Customer customer = Main.currentCustomer;
        if (customer != null) {
            boolean foundChecking = false;
            int numberOfCheckings = 0;
            customerIDText.setText(customer.getCustomerId());
            for (Account account : customer.getAccounts()) {
                if (account.getClass() == CheckingAccount.class) {
                    foundChecking = true;
                    numberOfCheckings += 1;
                    checkingBalanceText.setText(String.format("%2.2f", account.getBalance()));
                }
            }
            System.out.println(String.format("Found %d Checking Account(s), for Customer: %s", numberOfCheckings, customer.getFirstName()));
            if (!foundChecking) {
                checkingBalanceText.setText("N/A");
            }
        }
    }

}
