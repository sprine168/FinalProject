package Program.ControllerPackage;

import Program.Data.EnumeratedTypes;
import Program.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/*
    TransactionController is used for obtaining values from the CreateTransactions.fxml page. The controller should
    pass content to savings account class.
*/
public class TransactionController {

    // TODO create the rest of the controller to be in lieu of the pending transaction class.

    // Customer selection variables
    public ComboBox selectAccountType;
    public ComboBox selectCustomer;

    // Credit card input variables
    public TextField creditName;
    public TextField creditAmount;
    public Button submitCredit;

    // Checking input variables
    public TextField checkName;
    public TextField checkAmount;
    public Button submitCheck;

    // function is used for changing the gui pages and setting the new scene.
    private void function(Parent parent, ActionEvent event) {
        //function to traverse pages
        Scene homePageScene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(homePageScene);
    }

    // returnCreditTransaction is used to submit the credit card transaction.
    public void returnCreditTransaction(ActionEvent actionEvent) {

    }

    // returnCheckTransaction is used to submit the checking transactions.
    public void returnCheckTransaction(ActionEvent actionEvent) {

    }

    // returnToCus checks the login type and makes sure to transfer the user to the correct area when navigating.
    @FXML
    void returnToCus(ActionEvent event) throws IOException {

        if (Main.currentAuthorization != null) {

            if (Main.currentAuthorization == EnumeratedTypes.TELLER)
                function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/Submenu.fxml")), event);

            else if (Main.currentAuthorization == EnumeratedTypes.MANAGER)
                function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/SubMenu.fxml")), event);

            else if (Main.currentAuthorization == EnumeratedTypes.CUSTOMER)
                function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/Customer.fxml")), event);
        }
    }
}
