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

import Program.AccountPackage.Customer;
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
	public TextArea createInitBalance;
	
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
	public TextArea atmCard;
	
	Customer nCustomer;
	
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


	public void createCustomer(ActionEvent event){
		System.out.println("Creating customer");
		String ssn = createCusSSN.getText();
		String fName = createCusFName.getText();
		String lName = createCusLName.getText();
		String address = createCusAddress.getText();
		String city = createCusCity.getText();
		String state = createCusState.getText();
		String zip = createCusZip.getText();
		int atm = Integer.parseInt(atmCard.getText());
		
		nCustomer = new Customer(ssn, address, city, state, zip, fName, lName, atm);
		System.out.println(nCustomer.toString());
		
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}




}
