package Program.ControllerPackage;
/*
    Banking Project Team One
    CSC-406
    Prof. Pickett

    Team Members:

*/

import Program.AccountPackage.Account;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
    The Controller class will access the input boxes and other various items from the fxml pages.
    The pages should contain fx:id to be able to be accessed on this page using event listeners.

    // TODO Make sure to give the items in the fxml pages id's for the input fields. Otherwise can't be accessed.
*/
public class MainController implements Initializable {

    @FXML
    public Button cusPageButton;

    @FXML
    public Button logoutButton;

    @FXML
    public Button loginButton;

    @FXML
    public Button manPageButton;

    @FXML
    public Button telPageButton;

    @FXML
    public Button createCheckingButton;

    @FXML
    public Button searchButton;

    @FXML
    public TextField userLoginText;

    @FXML
    public TextField userPassText;

    public TextField cusIDSearch;

    @FXML
    public Button telPageBtton;

    private void function(Parent parent, ActionEvent event) {
        Scene homePageScene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //app_stage.hide();
        app_stage.setScene(homePageScene);
        //app_stage.show();
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        if (userLoginText.getText().equals(null) || userPassText.getText().equals(null) ||
                userLoginText.getText().trim().equals("") || userPassText.getText().trim().equals("")) return;
        String user = userLoginText.getText().toLowerCase();
        String pass = userPassText.getText().toLowerCase();
        if (user.equals("teller") && pass.equals("1234"))
            toTellerPage(event);
        else if (user.equals("manager") && pass.equals("4321"))
            toManagerPage(event);
        else if (pass.equals("1") && !user.equals(null) && !user.equals("") && Main.findCustomer(user)) {
            Customer customer = Main.getCustomer(user);
            Main.currentCustomer = customer;
            function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/Customer.fxml")), event);
        } else {
            userLoginText.setText("");
            userPassText.setText("");
        }
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/LoginPage.fxml")), event);
    }

    @FXML
    void toTellerPage(ActionEvent event) throws IOException {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/TellerMainMenu.fxml")), event);
    }

    @FXML
    void toManagerPage(ActionEvent event) throws IOException {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/ManagerMainMenu.fxml")), event);
    }

    @FXML
    void toCusPage(ActionEvent event) throws IOException {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/Customer.fxml")), event);
    }

    @FXML
    void createAccount(ActionEvent event) throws IOException {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/CreateAccount.fxml")), event);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }

}
