package Program.ControllerPackage;

/*
    Banking Project Team One
    CSC-406
    Prof. Pickett

    Team Members:

*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TellerController implements Initializable {

    @FXML
    public Button logoutButton;

    @FXML
    public Button searchButton;

    @FXML
    public Button createSavingsButton;

    @FXML
    public Button createCheckingButton;

    @FXML
    public Button createLoanButton;

    @FXML
    public Button returnMenu;



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
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/TellerSubMenu.fxml")), event);
    }

    @FXML
    void createAccount(ActionEvent event) throws IOException
    {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/CreateAccount.fxml")), event);
    }

    @FXML
    void toTellerPage(ActionEvent event) throws IOException
    {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/TellerMainMenu.fxml")), event);
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }

}
