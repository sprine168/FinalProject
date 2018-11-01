package Program.ControllerPackage;/*
    Banking Project Team One
    CSC-406
    Prof. Pickett

    Team Members:

*/

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
		function((FXMLLoader.load(getClass().getResource("/FXMLPackage/LoginPage.fxml"))), event);
	} 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
