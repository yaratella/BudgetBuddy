/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package csc2040.edu.budgetbuddy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author yarazrigan
 */
public class AddExpenseController implements Initializable {
    
    @FXML
    private TextField expenseAmountField;
    
    @FXML
    private DatePicker expenseAddDate;
    
    @FXML
    private Text amountErrorText, dateErrorText;
    
    @FXML
    private Button addButton, cancelButton;
    
    private MainDashboardViewController mainDashboardViewController;
    
    /**
     * Sets the reference to the main dashboard controller
     * Allows for communication between add expense view and main dashboard
     * 
     * @param controller
     */
    public void setMainDashboardViewController(MainDashboardViewController controller){
        this.mainDashboardViewController = controller;
    }
    
    /**
     * Handles the add button
     * Reads the input data, updates the main dashboard, and closes the window.
     * 
     * @param event
     */
    @FXML
    private void submitExpense(ActionEvent event){
        //Reset error messages to hide them at the beginning
        amountErrorText.setText("");
        dateErrorText.setText("");
        amountErrorText.setVisible(false);
        dateErrorText.setVisible(false);
        
        try{
            //validate amount input
            double amount = Double.parseDouble(expenseAmountField.getText());
            if(amount <=0){
                amountErrorText.setText("Amount must be greater than 0.");
                amountErrorText.setStyle("-fx-fill: red; -fx-font-weight: bold;");
                amountErrorText.setVisible(true);
                return; //exit if invalid amount
            }
            //validate date input
            if(expenseAddDate.getValue() == null){
                dateErrorText.setText("Please select a date.");
                dateErrorText.setStyle("-fx-fill: red; -fx-font-weight: bold;");
                dateErrorText.setVisible(true);
                return; //exit if invalid input
            }
            
            String date = expenseAddDate.getValue().toString(); //Format YYYY-MM-DD
            
            //update main dashboard
            mainDashboardViewController.addExpense(date, amount);
            
            //Close the Add expense window
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        }catch(NumberFormatException e){
            amountErrorText.setText("Invalid amount entered.");
            amountErrorText.setStyle("-fx-fill: red; -fx-font-weight: bold;");
            amountErrorText.setVisible(true);
        }
    }
    
    /**
     * Handles the cancel button action
     * closes the add expense window without making any changes
     * 
     * @param event 
     */
    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close(); //close window without making changes
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
