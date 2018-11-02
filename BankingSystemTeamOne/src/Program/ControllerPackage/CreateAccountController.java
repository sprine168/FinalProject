package Program.ControllerPackage;/*
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
import java.util.ArrayList;
import java.util.Date;
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
public class CreateAccountController implements Initializable {
	
	@FXML
	public Button returnMenu;
	
	@FXML
	public Button createCus;
	
	@FXML
	public TextArea createCusSSN;

	@FXML
	public TextArea date;
	
	@FXML
	public TextArea createCusFName;
	
	@FXML
	public TextArea createCusLName;
	
	@FXML
	public TextArea createCusAddress;
	
	@FXML
	public TextArea createCusCity;
	
	@FXML
	public TextArea createCusState;
	
	@FXML
	public TextArea createCusZip;
	
	@FXML
	public TextArea createCusInitCBalance;
	
	@FXML
	public TextArea createCusInitSBalance;

	@FXML
	public TextArea cusInitBalance;

	@FXML
	public TextArea atmCard;

	@FXML
	public ComboBox accountTypeBox;

	ArrayList<Object> accounts = new ArrayList<Object>();
	
	Stage dialogStage;
	
	MainController c = new MainController();

	private void function(Parent parent, ActionEvent event){
		Scene homePageScene = new Scene(parent);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		//app_stage.hide();
		app_stage.setScene(homePageScene);
		//app_stage.show();
	}

	@FXML
	void returnTeller(ActionEvent event) throws IOException {
		function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/TellerMainMenu.fxml"))), event);
	}


	public void createCustomer(ActionEvent event) throws ParseException {
		DateFormat df = new SimpleDateFormat("mm-dd-yyyy");
		String ssn = (createCusSSN.getText() != null && createCusSSN.getText() != "") ? createCusSSN.getText() : "";
		String fName = (createCusFName.getText() != null && createCusFName.getText() != "") ? createCusFName.getText() : "";
		String lName = (createCusLName.getText() != null && createCusLName.getText() != "") ? createCusLName.getText() : "";
		String address = (createCusAddress.getText() != null && createCusAddress.getText() != "") ? createCusAddress.getText() : "";
		String city = (createCusCity.getText() != null && createCusCity.getText() != "") ? createCusCity.getText() : "";
		String state = (createCusState.getText() != null && createCusState.getText() != "") ? createCusState.getText() : "";
		String zip = (createCusZip.getText() != null && createCusZip.getText() != "") ? createCusZip.getText() : "";
		String accountType = (accountTypeBox.getSelectionModel().getSelectedItem() != null) ?
                accountTypeBox.getSelectionModel().getSelectedItem().toString() : "Checking Account";
		double initialBalance = (cusInitBalance.getText() != null && cusInitBalance.getText() != "")
				? Double.parseDouble(cusInitBalance.getText()) : 0.0;
		int atm = (atmCard.getText() != null && atmCard.getText() != "")
				? Integer.parseInt(atmCard.getText()) : 0;
		Date currentDate = (date.getText().trim() != null && date.getText().trim() != "") ? df.parse(date.getText().trim()) : new Date();

		Customer nCustomer = new Customer(ssn, address, city, state, zip, fName, lName, atm);
		Main.addCustomer(nCustomer);
		Account account = null;
		if (accountTypeBox.getSelectionModel().getSelectedItem() == null)
		    accountTypeBox.setValue(accountType);

		if ((accountTypeBox.getSelectionModel().getSelectedItem()).toString().equals("Checking Account")){
			account = new CheckingAccount(ssn, initialBalance, "", "", 0, currentDate);
		}else if(accountTypeBox.getSelectionModel().getSelectedItem().equals("Savings Account")){
			account = new SavingsAccount(ssn, initialBalance, .125, currentDate, null);
		}

		Main.addAccount(account);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
