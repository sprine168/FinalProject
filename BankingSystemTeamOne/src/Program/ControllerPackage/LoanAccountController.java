package Program.ControllerPackage;

import Program.AccountPackage.Account;
import Program.AccountPackage.CheckingAccount;
import Program.AccountPackage.Customer;
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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoanAccountController implements Initializable{

    @FXML
    public Button returnMenu;

    @FXML
    public Button createLoan;

    @FXML
    public DatePicker notificationDate;

    @FXML
    public DatePicker dueDate;

    @FXML
    public TextField cusInterestRate;

    @FXML
    public TextField cusLoanAmount;

    @FXML
    public ComboBox loanType;

    @FXML
    public TextField cusID;

    private void function(Parent parent, ActionEvent event){
        Scene homePageScene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(homePageScene);
    }

    @FXML
    void returnMan(ActionEvent event) throws IOException
    {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/ManagerMainMenu.fxml")), event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
