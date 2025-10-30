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

public class SetSavingsGoalController implements Initializable {

    @FXML
    private TextField savingsAmountField;
    @FXML
    private DatePicker deadlineField;
    @FXML
    private Button savingsAddButton, cancelButton;
    @FXML
    private Text amountErrorText, dateErrorText;

    private MainDashboardViewController mainDashboardViewController;

    // Sets the reference to the main dashboard controller
    public void setMainDashboardViewController(MainDashboardViewController controller) {
        this.mainDashboardViewController = controller;
    }

    // Handles the Add Savings Goal button click
    @FXML
    private void submitSavingsGoal(ActionEvent event) {
        // Reset error messages at the start
        amountErrorText.setText("");
        dateErrorText.setText("");
        amountErrorText.setVisible(false);
        dateErrorText.setVisible(false);

        try {
            double goalAmount = Double.parseDouble(savingsAmountField.getText());

            // Validate the amount input
            if (goalAmount <= 0) {
                amountErrorText.setText("Amount must be greater than 0.");
                amountErrorText.setStyle("-fx-fill: red; -fx-font-weight: bold;");
                amountErrorText.setVisible(true);
                return; // Exit if invalid amount
            }

            // Validate the date input
            if (deadlineField.getValue() == null) {
                dateErrorText.setText("Please select a date.");
                dateErrorText.setStyle("-fx-fill: red; -fx-font-weight: bold;");
                dateErrorText.setVisible(true);
                return; // Exit if invalid date input
            }

            String date = deadlineField.getValue().toString(); // Get the date in YYYY-MM-DD format

            // Update the main dashboard with the savings goal
            mainDashboardViewController.setSavingsGoal(goalAmount);

            // Close the Add Savings Goal window
            closeWindow();

        } catch (NumberFormatException e) {
            // Handle invalid number format for the savings amount
            amountErrorText.setText("Invalid amount entered.");
            amountErrorText.setStyle("-fx-fill: red; -fx-font-weight: bold;");
            amountErrorText.setVisible(true);
        }
    }

    // Handles the Cancel button click
    @FXML
    private void cancel(ActionEvent event) {
        closeWindow();
    }

    // Helper function to close the window
    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the visibility of error messages
        amountErrorText.setVisible(false);
        dateErrorText.setVisible(false);
    }
}