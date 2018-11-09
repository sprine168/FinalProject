package Program.ControllerPackage;/*
    Banking Project Team One
    CSC-406
    Prof. Pickett

    Team Members:

*/

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.*;
import javafx.stage.Stage;


/*
    The Controller class will access the input boxes and other various items from the  CreateAccount fxml pages.
    The pages should contain fx:id to be able to be accessed on this page using event listeners.

    // TODO Make sure to give the items in the fxml pages id's for the input fields. Otherwise can't be accessed.
*/
public class CustomerController implements Initializable{

	@FXML
	public Button logoutButton;

	@FXML
	public TextField customerIDText;

	@FXML
	public TextField cusCheckingBalance;

	@FXML
	public TextField cusSavingBalance;

	@FXML
	public TextField cusLoanBalance;

	@FXML
	public TextField cusAccountStatus;

	@FXML
	public TextArea monthlyChecking;

	@FXML
	public TextArea monthlyCC;

	@FXML
	public TextField depositAmount;

	@FXML
	public TextField transferAmount;

	@FXML
	public TextField paymentAmount;

	@FXML
	public Button transferButton1;

	@FXML
	public Button withdrawButton1;

	@FXML
	public TextField withdrawAmount1;

	@FXML
	public Button transferButton;

	@FXML
	public Button paymentButton;

	@FXML
	public Button depositButton;

	@FXML
	public ComboBox cusSelectCheckings;

	@FXML
	public ComboBox cusSelectSavings;

	@FXML
	public ComboBox accountType1;

	@FXML
    public ComboBox cusLoanSelect;

    private void function(Parent parent, ActionEvent event){
		Scene homePageScene = new Scene(parent);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		//app_stage.hide();
		app_stage.setScene(homePageScene);
		//app_stage.show();
	}

	@FXML 
	void logout(ActionEvent event) throws IOException
	{
		function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/LoginPage.fxml"))), event);
	} 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Customer customer = Main.currentCustomer;
		if (customer != null){
			boolean foundChecking = false;
			boolean foundSavings = false;
			int numberOfCheckings = 0;
			int numberOfSavings = 0;
			customerIDText.setText(customer.getCustomerId());

			for(Account account : customer.getAccounts()){
				if (account.getClass() == CheckingAccount.class){
					foundChecking = true;
					numberOfCheckings += 1;
					cusCheckingBalance.setText(String.format("%2.2f", account.getBalance()));
				} else if(account.getClass() == SavingsAccount.class){
					foundSavings = true;
					numberOfSavings += 1;
					cusSavingBalance.setText(String.format("%2.2f", account.getBalance()));
				}
			}
			System.out.println(String.format("Found %d Checking Account(s), Found %d Savings Account(s) for Customer: %s", numberOfCheckings, numberOfSavings, customer.getFirstName()));
			if (!foundChecking){
				cusCheckingBalance.setText("N/A");
			}
			if (!foundSavings){
				cusSavingBalance.setText("N/A");
			}
		}
	}

}
