/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package csc2040.edu.budgetbuddy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Platform;

/**
 * FXML Controller class
 * Controller handles user interactions, navigation to different views (ex: AddIncomeController.java)
 * It updates the dashboard with current financial data.
 * 
 * @author yarazrigan
 */
public class MainDashboardViewController implements Initializable {
    
    @FXML
    private Button btnAddIncome, btnAddExpense, btnSetSavingsGoal, btnExitDashBoard;
    
    @FXML
    private LineChart<String, Number> MonthlySummaryGraph, SavingsGraph;
    
    @FXML
    private Text incomeValue, expenseValue;
    
    private double totalIncome = 0;
    private double totalExpense = 0;
    private double savingsGoal = 0;
    
    private XYChart.Series<String, Number> incomeDataSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> expenseDataSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> savingsDataSeries = new XYChart.Series<>();
    
    /**
     * Initializes the controller by setting up the charts with data series
     * Populates the dashboard with current financial data (income, expense, and savings goal).
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        // Initialize the charts with data series
        incomeDataSeries.setName("Income");
        expenseDataSeries.setName("Expense");
        savingsDataSeries.setName("Savings Goal");
        
        // Add data series to the respective graphs
        MonthlySummaryGraph.getData().addAll(incomeDataSeries, expenseDataSeries);
        SavingsGraph.getData().add(savingsDataSeries);
        
        // Update the dashboard with current data
        updateDashboard();
    }
    
    /**
     * Navigates to the Add Income view
     * Loads the AddIncome FXML file and opens modal window to add income
     * 
     * @param event
     * @throws IOException 
     */
    
    @FXML
    private void navigateToAddIncome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddIncome.fxml"));
        Parent root = loader.load();
        
        // Get the controller and pass the main dashboard reference
        AddIncomeController controller = loader.getController();
        controller.setMainDashboardViewController(this);
        
        // Create a new stage for the Add income window
        Stage stage = new Stage();
        stage.setTitle("Add Income");
        stage.initModality(Modality.APPLICATION_MODAL); // This blocks the user from interacting with other windows
        stage.setScene(new Scene(root));
        stage.showAndWait(); // Show this and wait until the Add Income window is closed
    }
    
    /**
     * Navigates to the Add Expense view
     * Loads the AddExpense FXML file and opens modal window to add expense
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void navigateToAddExpense(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddExpense.fxml"));
        Parent root = loader.load();
        
        // Get the controller and pass the main dashboard reference
        AddExpenseController controller = loader.getController();
        controller.setMainDashboardViewController(this);
        
        // Create a new stage for the Add expense window
        Stage stage = new Stage();
        stage.setTitle("Add Expense");
        stage.initModality(Modality.APPLICATION_MODAL); // This blocks the user from interacting with other windows
        stage.setScene(new Scene(root));
        stage.showAndWait(); // Show this and wait until the Add Expense window is closed
    }

    /**
     * Navigates to the Set Savings Goal View
     * Loads the SetSavingsGoal FXML file and opens modal window to add savings goal.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void navigateToSetSavingsGoal(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SetSavingsGoal.fxml"));
        Parent root = loader.load();
        
        // Get the controller and pass the main dashboard reference
        SetSavingsGoalController controller = loader.getController();
        controller.setMainDashboardViewController(this);
        
        // Create a new stage for the Set Savings Goal window
        Stage stage = new Stage();
        stage.setTitle("Set Savings Goal");
        stage.initModality(Modality.APPLICATION_MODAL); // This blocks the user from interacting with other windows
        stage.setScene(new Scene(root));
        stage.showAndWait(); // Show this and wait until the Set Savings Goal window is closed
    }
    
    /**
     * Closes the application when the exit button is clicked.
     * 
     * @param event 
     */
    @FXML
    private void exitApplication(ActionEvent event) {
        Stage stage = (Stage) btnExitDashBoard.getScene().getWindow();
        stage.close(); // Close the application
        System.out.println("Exit button clicked");
        Platform.exit(); //Terminates the whole application
    }
    
    /**
     * Saves the current income, expense, and savings goal data to a file named:
     * "budget_data.txt"
     * Then it prints a message confirming the save.
     */
    public void saveDataToFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("budget_data.txt"))) {
            //Write income, expense, and savings goal to the file
            writer.write("Total Income: " + totalIncome);
            writer.newLine();
            writer.write("Total Expense: " + totalExpense);
            writer.newLine();
            writer.write("Savings Goal: " + savingsGoal);
            writer.newLine();
            System.out.println("Data has been saved to budget_data.txt");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Adds an income value to the current total income
     * Updates the income chart and dashboard.
     * 
     * @param date
     * @param amount 
     */
    public void addIncome(String date, double amount) {
        totalIncome += amount;
        incomeValue.setText(String.format("%.2f", totalIncome));
        incomeDataSeries.getData().add(new XYChart.Data<>(date, totalIncome));
        updateDashboard();
    }

    /**
     * Adds an expense value to the current total income
     * Updates the expense chart and dashboard
     * 
     * @param date
     * @param amount 
     */
    public void addExpense(String date, double amount) {
        totalExpense += amount;
        expenseValue.setText(String.format("%.2f", totalExpense));
        expenseDataSeries.getData().add(new XYChart.Data<>(date, totalExpense));
        updateDashboard();
    }

    /**
     * Sets savings goal and updates the savings chart.
     * 
     * @param goal 
     */
    public void setSavingsGoal(double goal) {
        this.savingsGoal = goal;
        savingsDataSeries.getData().add(new XYChart.Data<>("Goal", savingsGoal));
        updateDashboard();
    }

    /**
     * Updates the dashboard by refreshing the displayed income, expense, and savings data.
     */
    private void updateDashboard() {
        // Refreshes income and expense fields and graphs
        incomeValue.setText(String.format("%.2f", totalIncome));
        expenseValue.setText(String.format("%.2f", totalExpense));
    }
}
