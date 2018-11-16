package Program.ControllerPackage;

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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class LoanAccountController implements Initializable{

    //Variables for the FXML values
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
        //function to traverse pages
        Scene homePageScene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(homePageScene);
    }

    @FXML
    void createLoan(){
        //Makes sure that there is not a null so we can create a loan
        try {
            if (!currentSelectedCustomer.equals(null)) {
                String desc = !cusLoanDesc.getText().equals(null) ? cusLoanDesc.getText() : "";
                double balance = !cusLoanAmount.getText().equals(null) ? Double.parseDouble(cusLoanAmount.getText()) : 0.00;
                double interestRate = !cusInterestRate.getText().equals(null) ? Double.parseDouble(cusInterestRate.getText()) : 0.00;

                //Grabs the dates.. not working right now
                LocalDate dueOn1 = dueDate.getValue() != null ? dueDate.getValue() : LocalDate.now();
                LocalDate notifyOn1 = notificationDate !=null ? notificationDate.getValue(): LocalDate.now();
                Calendar c1 = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();
                c1.set(dueOn1.getYear(),dueOn1.getMonthValue()-1,dueOn1.getDayOfMonth());
                c2.set(notifyOn1.getYear(),notifyOn1.getMonthValue()-1,notifyOn1.getDayOfMonth());

                Date dueOn = c1.getTime();
                Date notifyOn = c2.getTime();
                Date today = Calendar.getInstance().getTime();


                //Sets the loantype to a shorter string for the constructor
                String loanTypeShort = (String) loanType.getValue();
                if (loanTypeShort.equals("Credit Card")){loanTypeShort="CC";}
                if (loanTypeShort.equals("Long Term Loan")){loanTypeShort="LT";}
                if (loanTypeShort.equals("Short Term Loan")){loanTypeShort="ST";}
                //Creates a new loan using the values grabbed on button click
                Loan newLoan = new Loan(currentSelectedCustomer.getCustomerId(),desc,balance,interestRate,dueOn,notifyOn,0,dueOn,false,loanTypeShort);
                //adds the new loan to the database
                Main.addAccount(newLoan);

            }
        }catch(NullPointerException e){
            System.out.println("None Selected");
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
