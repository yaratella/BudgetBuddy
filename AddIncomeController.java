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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *Responsible for handling income addition (validate the income, and pass the data to the main dashboard.)
 * @author yarazrigan
 */
public class AddIncomeController implements Initializable {

    @FXML
    private TextField incomeAmountField;
    @FXML
    private DatePicker incomeAddDate;
    @FXML
    private Text amountErrorText, dateErrorText;
    @FXML
    private Button addButton, cancelButton;
    
    private MainDashboardViewController mainDashboardViewController;
    
    /**
     * Sets the reference to the main dashboard controller.
     * Allows communication between add income view and main dashboard.
     * 
     * @param controller
     */
    
    public void setMainDashboardViewController(MainDashboardViewController controller){
        this.mainDashboardViewController = controller;
    }
    
    
    /**
     * Handles the add button action
     * Reads the input data, updates the main dashboard, and closes the window.
     * 
     * @param event 
     */
    
    @FXML
    private void submitIncome(ActionEvent event) {
        //Reset error messages to hide them at the beginning
        amountErrorText.setText("");
        dateErrorText.setText("");
        amountErrorText.setVisible(false);
        dateErrorText.setVisible(false);
        
        try{
            double amount = Double.parseDouble(incomeAmountField.getText());
            //validate amount input
            if(amount <=0){
                amountErrorText.setText("Amount must be greater than 0.");
                amountErrorText.setStyle("-fx-fill: red; -fx-font-weight: bold;");
                amountErrorText.setVisible(true);
                return; //exit if invalid amount
            }
            //validate date input
            if(incomeAddDate.getValue() == null){
                dateErrorText.setText("Please select a date.");
                dateErrorText.setStyle("-fx-fill: red; -fx-font-weight: bold;");
                dateErrorText.setVisible(true);
                return; //exit if invalid input
            }
            String date = incomeAddDate.getValue().toString(); // format YYYY-MM-DD
            
            //Update the main dashbaord
            mainDashboardViewController.addIncome(date, amount);
            
            //close the Add Income Window
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        }catch(NumberFormatException e){
            amountErrorText.setText("Invalid amount entered.");
            amountErrorText.setStyle("-fx-fill: red; -fx-font-weight: bold;");
            amountErrorText.setVisible(true);
        }
    }
    /**
     * Handles the cancel button action.
     * closes the add income window without applying changes
     * 
     * @param event 
     */
    @FXML
    private void cancel(ActionEvent event){
        //Close the Add Income window without saving changes
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
