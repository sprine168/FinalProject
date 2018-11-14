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
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import Program.Data.*;
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
	public TextField createCusSSN;

	@FXML
	public TextField date;
	
	@FXML
	public TextField createCusFName;
	
	@FXML
	public TextField createCusLName;
	
	@FXML
	public TextField createCusAddress;
	
	@FXML
	public TextField createCusCity;
	
	@FXML
	public TextField createCusState;
	
	@FXML
	public TextField createCusZip;
	
	@FXML
	public TextField createCusInitCBalance;
	
	@FXML
	public TextField createCusInitSBalance;

	@FXML
	public TextField cusInitBalance;

	@FXML
	public TextField atmCard;

	@FXML
	public ComboBox accountTypeBox;

	@FXML
    public Label atm;

	@FXML
	public ComboBox accountTypeBox1;

	ArrayList<Object> accounts = new ArrayList<Object>();
	
	Stage dialogStage;
	
	MainController c = new MainController();

	private void function(Parent parent, ActionEvent event){
		Scene homePageScene = new Scene(parent);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(homePageScene);
	}

	@FXML
	void returnTo(ActionEvent event) throws IOException {
		if (Main.currentAuthorization != null){
			if (Main.currentAuthorization == EnumeratedTypes.TELLER)
				function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/TellerMainMenu.fxml"))), event);
			else if(Main.currentAuthorization == EnumeratedTypes.MANAGER)
				function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/ManagerMainMenu.fxml"))), event);
		}
	}


	public void createCustomer(ActionEvent event) throws ParseException {
		DateFormat df = new SimpleDateFormat("mm-dd-yyyy");
		String ssn = !(createCusSSN.getText().isEmpty() && createCusSSN.getText().equals("")) ? createCusSSN.getText() : "";
		String fName = !(createCusFName.getText().isEmpty() && createCusFName.getText().equals("")) ? createCusFName.getText() : "";
		String lName = !(createCusLName.getText().isEmpty() && createCusLName.getText().equals("")) ? createCusLName.getText() : "";
		String address = !(createCusAddress.getText().isEmpty() && createCusAddress.getText().equals("")) ? createCusAddress.getText() : "";
		String city = !(createCusCity.getText().isEmpty() && createCusCity.getText().equals("")) ? createCusCity.getText() : "";
		String state = !(createCusState.getText().isEmpty() && createCusState.getText().equals("")) ? createCusState.getText() : "";
		String zip = !(createCusZip.getText().isEmpty() && createCusZip.getText().equals("")) ? createCusZip.getText() : "";
		String accountType = (accountTypeBox.getSelectionModel().getSelectedItem() != null) ?
                accountTypeBox.getSelectionModel().getSelectedItem().toString() : "Checking Account";
		double initialBalance = !(cusInitBalance.getText().isEmpty() && cusInitBalance.getText().equals(""))
				? Double.parseDouble(cusInitBalance.getText()) : 0.0;
		int atm = !(atmCard.getText().isEmpty() && atmCard.getText().equals(""))
				? Integer.parseInt(atmCard.getText()) : 0;
		Date currentDate = !(date.getText().isEmpty() && date.getText().trim().equals("")) ? df.parse(date.getText().trim()) : new Date();

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
