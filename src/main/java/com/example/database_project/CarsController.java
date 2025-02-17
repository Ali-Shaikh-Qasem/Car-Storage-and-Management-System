package com.example.database_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;

public class CarsController implements Initializable {

    @FXML
    private Button AddCarButton;

    @FXML
    private Button CarClearAllButton;

    @FXML
    private TextField CarsSearch;

    @FXML
    private TableColumn<Cars, Integer> CidColMain;

    @FXML
    private Button DeleteCarButton;

    @FXML
    private AnchorPane EmployeeWindow;

    @FXML
    private TableColumn<Cars, Integer> SidCol;

    @FXML
    private Button UpdateCarButton;

    @FXML
    private BorderPane ViewSupplierWindow;

    @FXML
    private TableColumn<Cars, Integer> WidCol;

    @FXML
    private ComboBox<String> attributeSelection;

    @FXML
    private Button carsButton;

    @FXML
    private TableView<Cars> carsTable;

    @FXML
    private Button clientButton;

    @FXML
    private TableColumn<Cars, String> colorCol;

    @FXML
    private Button dashBoardButton;

    @FXML
    private Button employeesButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button homeButton;

    @FXML
    private TableColumn<Cars, String> makeCol;

    @FXML
    private TableColumn<Cars, String> modelCol;

    @FXML
    private TableColumn<Cars, Double> priceCol;

    @FXML
    private Button suppliersButton;

    @FXML
    private Button tranButton;

    @FXML
    private TextField txt_enter_color;

    @FXML
    private TextField txt_enter_make;

    @FXML
    private TextField txt_enter_model;

    @FXML
    private TextField txt_enter_price;

    @FXML
    private TextField txt_enter_supplierID;

    @FXML
    private TextField txt_enter_warehouseID;

    @FXML
    private TextField txt_enter_year;

    @FXML
    private Button warehousesButton;

    @FXML
    private TableColumn<Cars, Integer> yearCol;

    @FXML
    private TextField txt_enter_carID;

    @FXML
    void switchForm(ActionEvent event) throws IOException {
        Map<Button, String> buttonToFXMLMap = new HashMap<>();
        buttonToFXMLMap.put(dashBoardButton, "Dashboard.fxml");
        buttonToFXMLMap.put(warehousesButton, "Warehouses.fxml");
        buttonToFXMLMap.put(clientButton, "Customers.fxml");
        buttonToFXMLMap.put(carsButton, "Cars.fxml");
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

    ObservableList<Cars> listM;
    ObservableList<Cars> dataList;


    int index = -1;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    private int oldWarehouseID = -1;


    public void Add_Cars () {

        if (!areTextFieldsFilled()) {
            return;
        }

        conn = mysqlconnect.ConnectDB();
        String warehouseID = txt_enter_warehouseID.getText();

        // Check if the warehouse capacity is exceeded
        try {
            String checkCapacitySQL = "SELECT wavailableCars, wcapacity FROM warehouse WHERE wid = ?";
            pst = conn.prepareStatement(checkCapacitySQL);
            pst.setString(1, warehouseID);
            rs = pst.executeQuery();

            if (rs.next()) {
                int availableCars = rs.getInt("wavailableCars");
                int warehouseCapacity = rs.getInt("wcapacity");

                // If available cars are greater than or equal to the capacity, prevent adding the car
                if (availableCars >= warehouseCapacity) {
                    JOptionPane.showMessageDialog(null, "The warehouse has reached its capacity. Cannot add more cars.");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Warehouse not found.");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return;
        }

        String sql = "INSERT INTO car (cyear, cPrice, cmake, cmodel, cColor, wid, sid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_enter_year.getText());
            pst.setString(2, txt_enter_price.getText());
            pst.setString(3, txt_enter_make.getText());
            pst.setString(4, txt_enter_model.getText());
            pst.setString(5, txt_enter_color.getText());
            pst.setString(6, txt_enter_warehouseID.getText());
            pst.setString(7, txt_enter_supplierID.getText());
            pst.execute();

            UpdateTable();
            clearLabels();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }




    @FXML
    void getSelected (MouseEvent event){
        index = carsTable.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        txt_enter_carID.setText(CidColMain.getCellData(index).toString());
        txt_enter_color.setText(colorCol.getCellData(index).toString());
        txt_enter_make.setText(makeCol.getCellData(index).toString());
        txt_enter_model.setText(modelCol.getCellData(index).toString());
        txt_enter_price.setText(priceCol.getCellData(index).toString());
        txt_enter_year.setText(yearCol.getCellData(index).toString());
        txt_enter_warehouseID.setText(WidCol.getCellData(index).toString());
        txt_enter_supplierID.setText(SidCol.getCellData(index).toString());

        // Store the old warehouse ID
        oldWarehouseID = Integer.parseInt(WidCol.getCellData(index).toString());
    }

    public void Edit () {
        try {
            conn = mysqlconnect.ConnectDB();

            // Retrieve input values from text fields
            String value1 = txt_enter_carID.getText();   // Car ID
            String value2 = txt_enter_color.getText();   // Car color
            String value3 = txt_enter_make.getText();    // Car make
            String value4 = txt_enter_model.getText();   // Car model
            String value5 = txt_enter_price.getText();   // Car price
            String value6 = txt_enter_year.getText();    // Car year
            String value7 = txt_enter_supplierID.getText();  // Supplier ID
            String value8 = txt_enter_warehouseID.getText(); // Warehouse ID

            int newWarehouseID = Integer.parseInt(value8);  // Convert new warehouse ID to integer

            // Check the capacity of the new warehouse before updating
            String checkCapacitySQL = "SELECT wavailableCars, wcapacity FROM warehouse WHERE wid = ?";
            pst = conn.prepareStatement(checkCapacitySQL);
            pst.setInt(1, newWarehouseID);  // New warehouse ID
            rs = pst.executeQuery();

            if (rs.next()) {
                int availableCars = rs.getInt("wavailableCars");
                int warehouseCapacity = rs.getInt("wcapacity");

                // If the available cars are greater than or equal to the warehouse capacity, show an error
                if (availableCars >= warehouseCapacity) {
                    JOptionPane.showMessageDialog(null, "The warehouse has reached its capacity. Cannot update the car.");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Warehouse not found.");
                return;
            }

            // Update the car details in the database
            String sql = "UPDATE car SET " +
                    "cyear = '"+value6+"', " +
                    "cPrice = '"+value5+"', " +
                    "cmake = '"+value3+"', " +
                    "cmodel = '"+value4+"', " +
                    "cColor = '"+value2+"', " +
                    "wid = '"+value8+"', " +
                    "sid = '"+value7+"' " +
                    "WHERE cid='"+value1+"'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            // Now update the available cars count for the old and new warehouse
            // First, update the old warehouse (decrement the count) if the warehouse ID has changed
            if (oldWarehouseID != newWarehouseID) {  // Only update if the warehouse ID has changed
                // Decrement the count in the old warehouse
                String updateOldWarehouseSQL = "UPDATE warehouse SET wavailableCars = wavailableCars - 1 WHERE wid = ?";
                pst = conn.prepareStatement(updateOldWarehouseSQL);
                pst.setInt(1, oldWarehouseID);
                pst.execute();

                // Increment the count in the new warehouse
                String updateNewWarehouseSQL = "UPDATE warehouse SET wavailableCars = wavailableCars + 1 WHERE wid = ?";
                pst = conn.prepareStatement(updateNewWarehouseSQL);
                pst.setInt(1, newWarehouseID);  // New warehouse ID
                pst.execute();
            }

            // Update the table and clear labels
            UpdateTable();
            clearLabels();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }



    public void UpdateTable(){
        CidColMain.setCellValueFactory(new PropertyValueFactory<Cars, Integer>("carID"));
        makeCol.setCellValueFactory(new PropertyValueFactory<Cars, String>("make"));
        modelCol.setCellValueFactory(new PropertyValueFactory<Cars, String>("model"));
        yearCol.setCellValueFactory(new PropertyValueFactory<Cars, Integer>("year"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Cars, Double>("price"));
        colorCol.setCellValueFactory(new PropertyValueFactory<Cars, String>("color"));
        WidCol.setCellValueFactory(new PropertyValueFactory<Cars, Integer>("warehouseID"));
        SidCol.setCellValueFactory(new PropertyValueFactory<Cars, Integer>("supplierID"));

        listM = mysqlconnect.getDataCars();
        carsTable.setItems(listM);
    }

    public void Delete() {
        conn = mysqlconnect.ConnectDB();
        String sql = "DELETE FROM car WHERE cid = ?";

        try {

            // Delete the car
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_enter_carID.getText());
            pst.execute();

            UpdateTable();
            clearLabels();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void clearLabels() {
        txt_enter_supplierID.setText("");
        txt_enter_warehouseID.setText("");
        txt_enter_color.setText("");
        txt_enter_model.setText("");
        txt_enter_make.setText("");
        txt_enter_price.setText("");
        txt_enter_year.setText("");
    }

    @FXML
    void Search (KeyEvent event) {

        conn = mysqlconnect.ConnectDB(); // Corrected method name for ConnectDb
        String selection = attributeSelection.getValue();

        if (selection == null || selection.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a value from the ComboBox before searching.");
            return; // Exit the method to prevent further execution
        }

        String sql = "SELECT * FROM car WHERE "+selection+" LIKE ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%"+CarsSearch.getText()+"%");
            rs = pst.executeQuery();
            ObservableList<Cars> list = FXCollections.observableArrayList();
            while (rs.next()) {
                list.add(new Cars(
                        rs.getInt("cid"),           // carID column from database
                        rs.getInt("wid"),           // warehouseID column from database
                        rs.getDouble("cPrice"),     // price column from database
                        rs.getString("cColor"),     // color column from database
                        rs.getInt("cyear"),         // year column from database
                        rs.getString("cmodel"),     // model column from database
                        rs.getString("cmake"),      // make column from database
                        rs.getInt("sid")            // supplierID column from database
                ));
            }
            carsTable.setItems(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean areTextFieldsFilled() {
        if (    txt_enter_color.getText().isEmpty() ||
                txt_enter_make.getText().isEmpty() ||
                txt_enter_model.getText().isEmpty() ||
                txt_enter_price.getText().isEmpty() ||
                txt_enter_year.getText().isEmpty() ||
                txt_enter_supplierID.getText().isEmpty() ||
                txt_enter_warehouseID.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please fill in all the fields before proceeding.");
            return false;
        }
        return true;
    }



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        attributeSelection.setItems(FXCollections.observableArrayList("cid",  "wid", "cmake","cmodel", "cyear", "cprice", "cColor", "sid"));
        UpdateTable();
    }
    
}
