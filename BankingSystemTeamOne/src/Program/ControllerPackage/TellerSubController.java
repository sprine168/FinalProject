package Program.ControllerPackage;

import Program.AccountPackage.Account;
import Program.AccountPackage.CheckingAccount;
import Program.AccountPackage.Customer;
import Program.AccountPackage.SavingsAccount;
import Program.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TellerSubController implements Initializable {

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
    public TextArea customerIDText;

    @FXML
    public TextArea checkingBalanceText;

    @FXML
    public TextArea cusAccountStatus;

    @FXML
    public TextArea cusLoanBalance;

    @FXML
    public TextArea cusAccountStatement;

    @FXML
    public TextArea savingsBalanceText;


    private void function(Parent parent, ActionEvent event){
        Scene homePageScene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(homePageScene);
    }

    @FXML
    void logout(ActionEvent event) throws IOException
    {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/LoginPage.fxml")), event);
    }

    @FXML
    void toTellerPage(ActionEvent event) throws IOException
    {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/TellerMainMenu.fxml")), event);
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