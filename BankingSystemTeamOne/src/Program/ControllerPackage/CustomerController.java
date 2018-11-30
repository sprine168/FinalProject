package Program.ControllerPackage;/*
    Banking Project Team One
    CSC-406
    Prof. Pickett

    Team Members:

*/

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import Program.AccountPackage.*;
import Program.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventType;
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
public class CustomerController implements Initializable {

	private Customer customer;
	private Account selectedAccount1 = null;
	private Account selectedAccount2 = null;
	private Loan currentSelectedLoan;
	private Account currentSelectedAccount;

	public TextArea monthlyChecking;
	public TextArea monthlyCC;

	public TextField depositAmount;
	public TextField transferAmount;
	public TextField paymentAmount;
	public TextField withdrawAmount1;
	public TextField customerIDText;
	public TextField customerNameText;
	public TextField customerStateText;
	public TextField customerAddressText;
	public TextField customerZipText;
	public TextField customerATMText;
	public TextField cusCheckingBalance;
	public TextField cusSavingBalance;
	public TextField cusLoanBalance;
	public TextField cusAccountStatus;

	public Button transferButton1;
	public Button withdrawButton1;
	public Button transferButton;
	public Button paymentButton;
	public Button depositButton;
	public Button logoutButton;

	public ComboBox cusSelectCheckings;
	public ComboBox cusSelectSavings;
	public ComboBox accountToTransferTo;
	public ComboBox accountToTransferFrom;
    public ComboBox cusLoanSelect;

    public ScrollPane CheckingAccountScrollPane;
    public ScrollPane CreditCardScollPane;

    protected CollectionController checkingsCollection;
    protected CollectionController allAccountsCollection;
    protected CollectionController loanCollection;
    protected CollectionController nonLoanCollection;
    protected CollectionController savingsCollection;

    private void function(Parent parent, ActionEvent event){
		Scene homePageScene = new Scene(parent);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(homePageScene);
	}

	@FXML 
	void logout(ActionEvent event) throws IOException
	{
		function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/LoginPage.fxml"))), event);
	}

	@FXML
	void transferFunds(ActionEvent event) throws IOException
	{
		if (selectedAccount1 != null && selectedAccount2 != null){
			double amountToTransfer = !(transferAmount.getText().equals(null) || transferAmount.getText().equals("")) ?  Double.parseDouble(transferAmount.getText()) : 0.00;
			if (amountToTransfer > 0 && selectedAccount1.getBalance() > amountToTransfer){
				if (selectedAccount2.getClass().equals(Loan.class)) {
					Loan acc = (Loan) selectedAccount2;
					double amt = selectedAccount1.Withdraw(amountToTransfer);
					acc.Deposit(amt);
				} else {
					double amt = selectedAccount1.Withdraw(amountToTransfer);
					selectedAccount2.Deposit(amt);
				}
			}
			function((FXMLLoader.load(getClass().getResource("/Program/FXMLPackage/Customer.fxml"))), event);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		customer = Main.currentCustomer;
		if (customer != null){
            customerIDText.setText(customer.getCustomerId());
            customerNameText.setText(customer.getFirstName()+ " " + customer.getLastName());
			customerAddressText.setText(customer.getAddress()+ ", " + customer.getCity());
			customerStateText.setText(customer.getState());
			customerZipText.setText(customer.getZipCode());
			customerATMText.setText(customer.getAtmCard() == 1 ? "Yes" : "No");
            boolean foundChecking = false;
			boolean foundSavings = false;
			ArrayList<Account> nonLoanAccounts = new ArrayList();
			ArrayList<Account> checkingsAccounts = new ArrayList();
			ArrayList<Account> savingsAccounts = new ArrayList();
			ArrayList<Account> loanAccounts = new ArrayList();
            final ArrayList<Account> allAccounts = new ArrayList();

			for(Account account : customer.getAccounts()) {
				if (account.getClass().equals(CheckingAccount.class)) {
					checkingsAccounts.add(account);
					nonLoanAccounts.add(account);
					allAccounts.add(account);
					foundChecking = true;
				} else if (account.getClass().equals(SavingsAccount.class)) {
					savingsAccounts.add(account);
					nonLoanAccounts.add(account);
					allAccounts.add(account);
					foundSavings = true;
				} else {
				    loanAccounts.add(account);
					allAccounts.add(account);
                }
			}

			// Setting up the Collections for the combo boxes
			checkingsCollection = new CollectionController(checkingsAccounts);
			cusSelectCheckings.setItems(checkingsCollection.getCollections());

			loanCollection = new CollectionController(loanAccounts);
			cusLoanSelect.setItems(loanCollection.getCollections());

			nonLoanCollection = new CollectionController(nonLoanAccounts);
			accountToTransferFrom.setItems(nonLoanCollection.getCollections());

			savingsCollection = new CollectionController(savingsAccounts);
			cusSelectSavings.setItems(savingsCollection.getCollections());

			// Adding Change Listners so when a value is changed it will update information.
			cusSelectCheckings.valueProperty().addListener(new ChangeListener() {
				@Override
				public void changed(ObservableValue observable, Object oldValue, Object newValue) {
					cusCheckingBalance.setText(String.format("$%2.2f", ((Account) newValue).getBalance()));
					ArrayList<PendingTransaction> checkingTransactions = Main.fetchTransactions(((CheckingAccount) newValue).getAccountId());
					//CheckingAccountScrollPane.setContent();
				}
			});

			cusSelectSavings.valueProperty().addListener(new ChangeListener() {
				@Override
				public void changed(ObservableValue observable, Object oldValue, Object newValue) {
					cusSavingBalance.setText(String.format("$%2.2f", ((Account) newValue).getBalance()));
				}
			});

			accountToTransferFrom.valueProperty().addListener(new ChangeListener() {
				@Override
				public void changed(ObservableValue observable, Object oldValue, Object newValue) {
					selectedAccount1 = (Account) newValue;
                    ArrayList<Account> newArray = (ArrayList<Account>) allAccounts.clone();
					newArray.remove(selectedAccount1);
                    allAccountsCollection = new CollectionController(newArray);
                    accountToTransferTo.setItems(allAccountsCollection.getCollections());
				}
			});

			accountToTransferTo.valueProperty().addListener(new ChangeListener() {
				@Override
				public void changed(ObservableValue observable, Object oldValue, Object newValue) {
					selectedAccount2 = (Account) newValue;
				}
			});

            cusLoanSelect.valueProperty().addListener(new ChangeListener() {
				@Override
				public void changed(ObservableValue observable, Object oldValue, Object newValue) {
					currentSelectedLoan = (Loan) newValue;
					if(currentSelectedLoan.getMissedPaymentFlag()){
						cusAccountStatus.setText("Late");
					}else{
						cusAccountStatus.setText("Current");
					}

					cusLoanBalance.setText(String.format("%2.2f", currentSelectedLoan.getBalance()));
				}
			});

			if (!foundChecking){
				cusCheckingBalance.setText("N/A");
			}
			if (!foundSavings){
				cusSavingBalance.setText("N/A");
			}
		}
	}

}
