package Program.ControllerPackage;/*
    Banking Project Team One
    CSC-406
    Prof. Pickett

    Team Members:

*/

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Program.AccountPackage.*;
import Program.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventType;
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
public class CustomerController implements Initializable {

	private Customer customer;
	private Account accountToTransferFrom;
	private Loan currentSelectedLoan;
	private Account currentSelectedAccount;

	@FXML
	public Button logoutButton;

	@FXML
	public TextField customerIDText;

	@FXML
	public TextField customerNameText;

	@FXML
	public TextField customerStateText;

	@FXML
	public TextField customerAddressText;

	@FXML
	public TextField customerZipText;

	@FXML
	public TextField customerATMText;

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
		customer = Main.currentCustomer;
		if (customer != null){
            customerIDText.setText(customer.getCustomerId());
            customerNameText.setText(customer.getFirstName()+ " " + customer.getLastName());
			customerAddressText.setText(customer.getAddress()+ ", " + customer.getCity());
			customerStateText.setText(customer.getState());
			customerZipText.setText(customer.getZipCode());
			customerATMText.setText(customer.getAtmCard() == 1 ? "Yes" : "No");
            boolean foundChecking = false;
			boolean foundSavings = false;
			int numberOfCheckings = 0;
			int numberOfSavings = 0;
			ArrayList<Account> nonLoanAccounts = new ArrayList();
			ArrayList<Account> checkingsAccounts = new ArrayList();
			ArrayList<Account> savingsAccounts = new ArrayList();
			ArrayList<Account> loanAccounts = new ArrayList();
			for(Account account : customer.getAccounts()) {
				if (account.getClass().equals(CheckingAccount.class)) {
					checkingsAccounts.add(account);
					nonLoanAccounts.add(account);
					foundChecking = true;
				} else if (account.getClass().equals(SavingsAccount.class)) {
					savingsAccounts.add(account);
					nonLoanAccounts.add(account);
					foundSavings = true;
				} else {
				    loanAccounts.add(account);
                }
			}
			CollectionController checkingsCollection = new CollectionController(checkingsAccounts);
			cusSelectCheckings.setItems(checkingsCollection.getCollections());

			CollectionController savingsCollection = new CollectionController(savingsAccounts);
			cusSelectSavings.setItems(savingsCollection.getCollections());

			CollectionController nonLoanCollection = new CollectionController(nonLoanAccounts);
			accountType1.setItems(nonLoanCollection.getCollections());

            CollectionController loanCollection = new CollectionController(nonLoanAccounts);
            cusLoanSelect.setItems(loanCollection.getCollections());

            cusSelectSavings.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    cusSavingBalance.setText(String.format("$%2.2f", ((Account) newValue).getBalance()));
                }
            });

            cusSelectCheckings.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    cusCheckingBalance.setText(String.format("$%2.2f", ((Account) newValue).getBalance()));
                }
            });

			if (!foundChecking){
				cusCheckingBalance.setText("N/A");
			}
			if (!foundSavings){
				cusSavingBalance.setText("N/A");
			}
		}
	}

}
