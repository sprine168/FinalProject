package Program.ControllerPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TellerSubController {

    @FXML
    public Button logoutButton;

    @FXML
    public Button returnMenu;

    @FXML
    public Button closeAccountButton;

    @FXML
    public Button withdrawButton;

    @FXML
    public Button depositButton;

    @FXML
    public Button paymentButton;

    @FXML
    public Button transferButton;

    @FXML
    public TextField deposit;

    @FXML
    public TextField transfer;

    @FXML
    public TextField withdraw;

    @FXML
    public TextField payment;

    @FXML
    public TextArea customerIDText;

    @FXML
    public TextArea checkingBalanceText;

    @FXML
    public TextArea cusAccountStatus;

    @FXML
    public TextArea cusLoanBalance;

    @FXML
    public TextArea cusAccountStatement;


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
    void toTellerPage(ActionEvent event) throws IOException
    {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/TellerMainMenu.fxml")), event);
    }

}
