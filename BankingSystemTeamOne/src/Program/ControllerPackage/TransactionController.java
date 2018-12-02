package Program.ControllerPackage;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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



    // returnCreditTransaction is used to submit the credit card transaction.
    public void returnCreditTransaction(ActionEvent actionEvent) {
    }

    // returnCheckTransaction is used to submit the checking transactions.
    public void returnCheckTransaction(ActionEvent actionEvent) {
    }
}
