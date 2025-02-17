package com.example.database_project;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DashboardController implements Initializable{

    @FXML
    private TableView<Cars> AvailableCarsTable;

    @FXML
    private BorderPane Bpane;

    @FXML
    private Button CarButton;

    @FXML
    private Button CustomerButton;

    @FXML
    private Label Nocustomerslbl;

    @FXML
    private Label Noempslbl;

    @FXML
    private Label Noincomelbl;

    @FXML
    private Label Nosupplierslbl;

    @FXML
    private TableColumn<Transaction, Double> Remaining_col;

    @FXML
    private ComboBox<String> SalesMonthcbx;

    @FXML
    private CategoryAxis SalesXAxis;

    @FXML
    private NumberAxis SalesYAxis;

    @FXML
    private ComboBox<String> SalesYearcbx;

    @FXML
    private LineChart<String, Number> Saleslinechart;

    @FXML
    private StackPane Stack_pane;

    @FXML
    private ComboBox<Integer> TopCarsYearcbx;

    @FXML
    private ComboBox<Integer> TopcarsMonthcbx;

    @FXML
    private TableView<Warehouse> Warehouses_table;

    @FXML
    private TableColumn<Cars, String> carColor_col;

    @FXML
    private TableColumn<Cars, Integer> carID_col;

    @FXML
    private TableColumn<Cars, String> carMake_col;

    @FXML
    private TableColumn<Cars, String> carModel_col;

    @FXML
    private TableColumn<Cars, Double> carPrice_col;

    @FXML
    private TableColumn<Cars, Integer> carYear_col;

    @FXML
    private Button dashBoardButton;

    @FXML
    private Button employeesButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button homeButton;

    @FXML
    private AnchorPane inside_Anch_left;

    @FXML
    private Button suppliersButton;

    @FXML
    private BarChart<String, Integer> topSellCars_Barchart;

    @FXML
    private AnchorPane top_anch;

    @FXML
    private Button tranButton;

    @FXML
    private TableView<Transaction> unpaidOrder_table;

    @FXML
    private TableColumn<Transaction, Integer> unpaidid_col;

    @FXML
    private TableColumn<Transaction, Double> unpaidprice_col;

    @FXML
    private TableColumn<Warehouse, Integer> warehouseCap_col;

    @FXML
    private TableColumn<Warehouse, String> warehouseName_col;

    @FXML
    private TableColumn<Warehouse, Integer> warehouseid_col;

    @FXML
    private Button warehousesButton;

    @FXML
    private Button btn_updateBarChart;

    @FXML
    private CategoryAxis carBrands_XAxis;

    @FXML
    private NumberAxis carBrands_YAxis;



    ObservableList<Transaction> listM;
    ObservableList<Warehouse> warehousesList;
    ObservableList<Cars> availableCars;


    int index = -1;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    void UpdatePchart(ActionEvent event) {

    }

    @FXML
    void UpdateSchart(ActionEvent event) {

    }

    @FXML
    void switchForm(ActionEvent event) throws IOException {
        Map<Button, String> buttonToFXMLMap = new HashMap<>();
        buttonToFXMLMap.put(dashBoardButton, "Dashboard.fxml");
        buttonToFXMLMap.put(warehousesButton, "Warehouses.fxml");
        buttonToFXMLMap.put(CustomerButton, "Customers.fxml");
        buttonToFXMLMap.put(CarButton, "Cars.fxml");
        buttonToFXMLMap.put(employeesButton, "Employees.fxml");
        buttonToFXMLMap.put(tranButton, "Transaction.fxml");
        buttonToFXMLMap.put(suppliersButton, "Suppliers.fxml");
        buttonToFXMLMap.put(exitButton, "Login.fxml");
        buttonToFXMLMap.put(homeButton, "Home.fxml");

        Button sourceButton = (Button) event.getSource();
        String fxmlFile = buttonToFXMLMap.get(sourceButton);

        if (fxmlFile != null) {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            Stage regStage = new Stage();
            regStage.setScene(scene);
            regStage.show();
            sourceButton.getScene().getWindow().hide();
        }
    }
    @FXML
    private void UpdateTableUnPaid() {
        // add unpaid transactions to the table
        unpaidid_col.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("Transaction_Id"));
        unpaidprice_col.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("TotalPrice"));
        Remaining_col.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("Remaining"));

        listM = mysqlconnect.getDataTransactionunpaid();

        if (listM.isEmpty()) {
            System.out.println("No data in the database");
        }
        else {
            System.out.println("Data found");
        }
        unpaidOrder_table.setItems(listM);



    }
    public double getTodaysIncome() {
        return mysqlconnect.getTodaysIncome();
    }

    @FXML
    private void UpdateTableTodayIncome() {
        // add unpaid transactions to the table
        double todaysIncome = getTodaysIncome();

        // Set the value to the label Noincomelbl
        Noincomelbl.setText(todaysIncome+ "$ ");

    }

    @FXML
    private void UpdateTableNumEmployess() {
        int numEmployees = mysqlconnect.getNumofEmployees();
        Noempslbl.setText(String.valueOf(numEmployees));
    }

    @FXML
    void UpdateWarehousesTable() {

        warehouseid_col.setCellValueFactory(new PropertyValueFactory<Warehouse, Integer>("Wid"));
        warehouseName_col.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("name"));
        warehouseCap_col.setCellValueFactory(cellData -> {
            Warehouse warehouse = cellData.getValue();
            int availableCapacity = warehouse.getCapacity() - warehouse.getAvailableCars();
            return new SimpleIntegerProperty(availableCapacity).asObject();
        });

        warehousesList = mysqlconnect.getDataWarehousesCapacities();
        Warehouses_table.setItems(warehousesList);

    }


    ObservableList<carBrandSales> carBrandSalesList;
    @FXML
    void UpdateTopCarSalesBarChart() {
        // Check if the user has selected both month and year
        if (TopcarsMonthcbx.getValue() == null || TopCarsYearcbx.getValue() == null) {
            // Show a message if either month or year is not selected
            JOptionPane.showMessageDialog(null, "Please select both a month and a year.");
            return; // Exit the method if inputs are missing
        }

        // Get selected month and year from the ComboBoxes
        int month = TopcarsMonthcbx.getValue();
        int year = TopCarsYearcbx.getValue();

        // Fetch data for the given month and year
        carBrandSalesList = mysqlconnect.getDataCarBrandsSales(year, month);

        // Clear any existing data from the bar chart
        topSellCars_Barchart.getData().clear();

        // Create a new series to hold the data
        XYChart.Series series = new XYChart.Series();
        //series.setName("Top Selling Cars in " + month + "/" + year);

        // Add data to the series
        for (carBrandSales sales : carBrandSalesList) {
            // Add each brand and sales count as a new data point
            series.getData().add(new XYChart.Data(sales.getCarBrand(), sales.getSales()));
        }

        // Add the series to the bar chart
        topSellCars_Barchart.getData().addAll(series);
    }

    @FXML
    private void updateChartData(int year) {
        // Clear existing data
        Saleslinechart.getData().clear();

        // Retrieve data from the database
        Map<String, Double> data = mysqlconnect.getDataTransactionPaidByMonthYear(year);

        // Create a new data series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(year + " Sales");

        // Populate the series with data
        data.forEach((month, totalPaid) -> series.getData().add(new XYChart.Data<>(month, totalPaid)));

        // Add series to the chart
        Saleslinechart.getData().add(series);
    }


    private void filterChartDataByMonth(String month) {
        // Get the currently displayed series
        XYChart.Series<String, Number> series = Saleslinechart.getData().get(0);

        // Filter data points based on the selected month
        series.getData().removeIf(data -> !data.getXValue().equals(month));
    }

    @FXML
    private void UpdateTablecustomer() {
        int customer = mysqlconnect.getNumofCustomer();
        Nocustomerslbl.setText(String.valueOf(customer));
    }


    @FXML
    private void UpdateTablesupplier() {
        int supplier = mysqlconnect.getNumofsuppliers();
        Nosupplierslbl.setText(String.valueOf(supplier));
    }

    @FXML
    void UpdateAvailableCars() {

        carID_col.setCellValueFactory(new PropertyValueFactory<Cars, Integer>("carID"));
        carColor_col.setCellValueFactory(new PropertyValueFactory<Cars, String>("color"));
        carMake_col.setCellValueFactory(new PropertyValueFactory<Cars, String>("make"));
        carModel_col.setCellValueFactory(new PropertyValueFactory<Cars, String>("model"));
        carPrice_col.setCellValueFactory(new PropertyValueFactory<Cars, Double>("price"));
        carYear_col.setCellValueFactory(new PropertyValueFactory<Cars, Integer>("year"));



        availableCars = mysqlconnect.getDataAvailableCars();
        AvailableCarsTable.setItems(availableCars);

    }




    public void initialize (URL url, ResourceBundle rb){
        this.TopCarsYearcbx.setItems(FXCollections.observableArrayList(
                2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025
        ));
        this.TopcarsMonthcbx.setItems(FXCollections.observableArrayList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
        ));
        topSellCars_Barchart.getData().clear();
        topSellCars_Barchart.setStyle(""); // Reset any prior styles
        UpdateTableUnPaid();
        UpdateTableTodayIncome();
        UpdateWarehousesTable();
        UpdateTableNumEmployess();
        UpdateTablecustomer();
        UpdateTablesupplier();
        UpdateAvailableCars();

        // Populate ComboBoxes with sample data
        ObservableList<String> months = FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        ObservableList<String> years = FXCollections.observableArrayList("2023", "2024", "2025");

        SalesMonthcbx.setItems(months);
        SalesYearcbx.setItems(years);

        // Set default axis labels
        SalesXAxis.setLabel("Months");
        SalesYAxis.setLabel("Sales (in USD)");

        // Initialize the chart with default data
        updateChartData(2025);

        // Add listeners to ComboBoxes
        SalesYearcbx.setOnAction(event -> {
            String selectedYear = SalesYearcbx.getValue();
            if (selectedYear != null) {
                updateChartData(Integer.parseInt(selectedYear)); // Convert year to integer
            }
        });

        SalesMonthcbx.setOnAction(event -> {
            String selectedMonth = SalesMonthcbx.getValue();
            if (selectedMonth != null) {
                filterChartDataByMonth(selectedMonth);
            }
        });

    }

}