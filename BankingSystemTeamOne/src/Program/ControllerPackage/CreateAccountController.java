package Program.ControllerPackage;

/*
    Banking Project Team One
    CSC-406
    Prof. Pickett

    Team Members:
*/

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Program.Data.*;
import Program.AccountPackage.Account;
import Program.AccountPackage.CheckingAccount;
import Program.AccountPackage.Customer;
import Program.AccountPackage.SavingsAccount;
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


/*
    The Controller class will access the input boxes and other various items from the  CreateAccount fxml pages.
    The pages should contain fx:id to be able to be accessed on this page using event listeners.

    // TODO Make sure to give the items in the fxml pages id's for the input fields. Otherwise can't be accessed.
*/
public class CreateAccountController implements Initializable {

	Date currentDate;
	Customer currentCustomer;
	DateFormat df = new SimpleDateFormat("mm-dd-yyyy");

	@FXML
	public Button addCheckingAccount;
	public Button returnMenu;
	public Button createCus;

	public ComboBox accountTypeBox1;
	public ComboBox accountList;
	public ComboBox backupAccount;
	public ComboBox accountTypeBox;

	public TextField createCusSSN;
	public TextField date;
	public TextField createCusFName;
	public TextField createCusLName;
	public TextField createCusAddress;
	public TextField createCusCity;
	public TextField createCusState;
	public TextField createCusZip;
	public TextField cusInitBalance;
	public TextField atmCard;
	public TextField checkCusInitBalance;
	public TextField savingAccountBalance;
	public TextField savingAccountOpen;
	public TextField cdInterest;
	public TextField cdAddDate;
	public TextField cdDueDate;
	public TextField checkAtmCard;
	public TextField checkDate;

	public Label atm;
	public Label CDAddDateLabel;
	public Label AccountBalanceLabel;
	public Label AccountBackupLabel;
	public Label DateAccountOpenedLabel;
	public Label ATMAccessLabel;
	public Label CDDueLabel;
	public Label SubAccountLabel;
	public Label DateAccountOpened2Label;
	public Label CurrentInterestLabel;
	public Label AccountBalance2Label;

	//This function will be used to set and go to the next scene.
	private void function(Parent parent, ActionEvent event){
		Scene homePageScene = new Scene(parent);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(homePageScene);
	}

	//This function, based on login permissions will take you back to the manager or teller main page.
	@FXML
	void returnTo(ActionEvent event) throws IOException {
		if (Main.currentAuthorization != null){
			if (Main.currentAuthorization == EnumeratedTypes.TELLER)
				function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/TellerMainMenu.fxml"))), event);
			else if(Main.currentAuthorization == EnumeratedTypes.MANAGER)
				function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/ManagerMainMenu.fxml"))), event);
		}
	}

	public void createAccount() throws ParseException {
		String accountType = (accountTypeBox.getSelectionModel().getSelectedItem() != null) ?
				accountTypeBox.getSelectionModel().getSelectedItem().toString() : "Checking Account";

		String subAccountType = (accountTypeBox1.getSelectionModel().getSelectedItem() != null) ?
				accountTypeBox1.getSelectionModel().getSelectedItem().toString() : "";

		double initialBalance = !(cusInitBalance.getText().isEmpty() && cusInitBalance.getText().equals("")) ?
				Double.parseDouble(cusInitBalance.getText()) : 0.0;

		Calendar c = new GregorianCalendar();

		Date cdDue = !(cdDueDate.getText().isEmpty() && cdDueDate.getText().equals("")) ? df.parse(cdDueDate.getText()) : null;

		if (cdDue.equals(null)){
			c.setTime(new Date());
			c.add(Calendar.DATE, 325);
			cdDue = c.getTime();
		}

		Account account = null;
		if (accountTypeBox.getSelectionModel().getSelectedItem() == null)
			accountTypeBox.setValue(accountType);

		if (accountType.equals("Checking Account")){
			account = new CheckingAccount(currentCustomer.getCustomerId(), initialBalance, subAccountType, "", 0, currentDate);
		}else if(accountType.equals("Savings Account")){
			if (subAccountType.equals("Non CD"))
				account = new SavingsAccount(currentCustomer.getCustomerId(), initialBalance, .125, currentDate, null);
			else
				account = new SavingsAccount(currentCustomer.getCustomerId(), initialBalance, .125, currentDate, cdDue);
		}
	}

	public void createCustomer(ActionEvent event) throws ParseException {
		//Read input to variables and use variables as needed.
		if (currentCustomer == null) {
			String ssn = !(createCusSSN.getText().isEmpty() && createCusSSN.getText().equals("")) ? createCusSSN.getText() : "";

			String fName = !(createCusFName.getText().isEmpty() && createCusFName.getText().equals("")) ? createCusFName.getText() : "";

			String lName = !(createCusLName.getText().isEmpty() && createCusLName.getText().equals("")) ? createCusLName.getText() : "";

			String address = !(createCusAddress.getText().isEmpty() && createCusAddress.getText().equals("")) ? createCusAddress.getText() : "";

			String city = !(createCusCity.getText().isEmpty() && createCusCity.getText().equals("")) ? createCusCity.getText() : "";

			String state = !(createCusState.getText().isEmpty() && createCusState.getText().equals("")) ? createCusState.getText() : "";

			String zip = !(createCusZip.getText().isEmpty() && createCusZip.getText().equals("")) ? createCusZip.getText() : "";

			int atm = !(atmCard.getText().isEmpty() && atmCard.getText().equals("")) ?
					Integer.parseInt(atmCard.getText()) : 0;

			currentCustomer = new Customer(ssn, address, city, state, zip, fName, lName, atm);

			Main.addCustomer(currentCustomer);
		}

		createAccount();
	}

	public void setAccountTypeVisibilty(boolean value){
		accountTypeBox1.visibleProperty().setValue(true);
		SubAccountLabel.visibleProperty().setValue(true);

		//Checkings TextFields
		checkAtmCard.visibleProperty().setValue(value);
		checkCusInitBalance.visibleProperty().setValue(value);
		checkDate.visibleProperty().setValue(value);
		backupAccount.visibleProperty().setValue(value);

		//Checkings Labels
		AccountBackupLabel.visibleProperty().setValue(value);
		AccountBalanceLabel.visibleProperty().setValue(value);
		ATMAccessLabel.visibleProperty().setValue(value);
		DateAccountOpenedLabel.visibleProperty().setValue(value);

		//Savings TextFields
		savingAccountBalance.visibleProperty().setValue(!value);
		cdInterest.visibleProperty().setValue(!value);
		savingAccountOpen.visibleProperty().setValue(!value);
		cdAddDate.visibleProperty().setValue(!value);
		cdDueDate.visibleProperty().setValue(!value);

		//Savings Labels
		AccountBalance2Label.visibleProperty().setValue(!value);
		CurrentInterestLabel.visibleProperty().setValue(!value);
		DateAccountOpened2Label.visibleProperty().setValue(!value);
		CDDueLabel.visibleProperty().setValue(!value);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ArrayList<String> savingsSubAccounts = new ArrayList();
		ArrayList<String> checkingsSubAccounts = new ArrayList();

		savingsSubAccounts.add("Non CD");
		savingsSubAccounts.add("CD");

		checkingsSubAccounts.add("TMB");
		checkingsSubAccounts.add("Gold/Diamond");

		CollectionController savings = new CollectionController(savingsSubAccounts);
		CollectionController checkings = new CollectionController(checkingsSubAccounts);

		currentDate = new Date();

		accountTypeBox.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if (newValue.toString().equals("Checkings Account")){
					accountTypeBox1.setItems(checkings.getCollections());
					setAccountTypeVisibilty(true);
				}else if (newValue.toString().equals("Savings Account")){
					accountTypeBox1.setItems(savings.getCollections());
					setAccountTypeVisibilty(false);
				}
			}
		});
	}

}
