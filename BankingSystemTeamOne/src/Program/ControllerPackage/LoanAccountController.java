package Program.ControllerPackage;

import Program.AccountPackage.Account;
import Program.AccountPackage.CheckingAccount;
import Program.AccountPackage.Customer;
import Program.AccountPackage.Loan;
import Program.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.Date;
import java.util.ResourceBundle;

public class LoanAccountController implements Initializable{

    private Customer currentSelectedCustomer;

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
    public ComboBox cusID;

    @FXML
    public TextField cusLoanDesc;

    private void function(Parent parent, ActionEvent event){
        Scene homePageScene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(homePageScene);
    }

    @FXML
    void createLoan(){
        if (!currentSelectedCustomer.equals(null)){
            String desc = cusLoanDesc.getText().equals(null) ? cusLoanDesc.getText() : "";
            double balance = cusLoanAmount.getText().equals(null) ? Double.parseDouble(cusLoanAmount.getText()) : 0.00;
            double interestRate = cusInterestRate.getText().equals(null) ? Double.parseDouble(cusInterestRate.getText()) : 0.00;
            //Loan newLoan = new Loan();
            //Main.addAccount();
        }
    }

    @FXML
    void returnMan(ActionEvent event) throws IOException
    {
        function(FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/ManagerMainMenu.fxml")), event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cusID.setItems((new CollectionController(Main.customers).getCollections()));

        cusID.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                currentSelectedCustomer = (Customer) newValue;
            }
        });
    }
}
