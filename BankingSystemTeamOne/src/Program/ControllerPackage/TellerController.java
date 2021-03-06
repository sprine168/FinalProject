package Program.ControllerPackage;

/*
    Banking Project Team One
    CSC-406
    Prof. Pickett

    Team Members:

*/

import Program.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TellerController implements Initializable {

    @FXML
    public Button logoutButton;
    public Button searchButton;
    public Button createCheckingButton;
    public TextField cusIDSearch;

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
    void telSearch(ActionEvent event) throws IOException
    {
        String ssn = !cusIDSearch.getText().isEmpty() ? cusIDSearch.getText() : "";
        if (Main.findCustomer(ssn)) {
            Main.currentCustomer = Main.getCustomer(ssn);
            function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/SubMenu.fxml")), event);
        }
    }

    @FXML
    void createAccount(ActionEvent event) throws IOException
    {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/AccountCreation.fxml")), event);
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }

}
