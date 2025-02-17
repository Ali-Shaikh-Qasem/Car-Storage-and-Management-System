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
import javafx.scene.input.KeyEvent;
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

public class WarehousesController implements Initializable {

    @FXML
    private Button AddWarehouseButton;

    @FXML
    private Button DeleteWarehouseButton;

    @FXML
    private AnchorPane EmployeeWindow;

    @FXML
    private Button UpdateWarehouseButton;

    @FXML
    private BorderPane ViewSupplierWindow;

    @FXML
    private Button WarehouseClearAllButton;

    @FXML
    private TextField WarehousesSearch;

    @FXML
    private TableColumn<Warehouse, Integer> WidColMain;

    @FXML
    private ComboBox<String> attributeSelection;

    @FXML
    private Button carsButton;

    @FXML
    private Button clientButton;

    @FXML
    private Button dashBoardButton;

    @FXML
    private Button employeesButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button suppliersButton;

    @FXML
    private Button tranButton;

    @FXML
    private TextField txt_enter_warehouseCap;

    @FXML
    private TextField txt_enter_warehouseLoc;

    @FXML
    private TextField txt_enter_warehouseName;

    @FXML
    private TextField txt_enter_warehouseAVC;

    @FXML
    private TextField txt_enter_warehouseID;

    @FXML
    private TableColumn<Warehouse, Integer> warehouseAVCCol;

    @FXML
    private TableColumn<Warehouse, Integer> warehouseCapacityCol;

    @FXML
    private TableColumn<Warehouse, String> warehouseLocationCol;

    @FXML
    private TableColumn<Warehouse, String> warehouseNameCol;

    @FXML
    private TableView<Warehouse> warehouseTable;

    @FXML
    private Button warehousesButton;

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

    ObservableList<Warehouse> listM;
    ObservableList<Warehouse> dataList;

    int index = -1;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_Warehouses (){

        if (!areTextFieldsFilled()) {
            return;
        }

        conn = mysqlconnect.ConnectDB();
        String sql = "INSERT INTO warehouse (wname, wlocation, wcapacity) VALUES ( ?, ?, ?);\n";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_enter_warehouseName.getText());
            pst.setString(2, txt_enter_warehouseLoc.getText());
            pst.setString(3, txt_enter_warehouseCap.getText());
            pst.execute();

            UpdateTable();
            clearLabels();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    @FXML
    void getSelected (MouseEvent event){
        index = warehouseTable.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        txt_enter_warehouseID.setText(WidColMain.getCellData(index).toString());
        txt_enter_warehouseName.setText(warehouseNameCol.getCellData(index).toString());
        txt_enter_warehouseLoc.setText(warehouseLocationCol.getCellData(index).toString());
        txt_enter_warehouseCap.setText(warehouseCapacityCol.getCellData(index).toString());
        txt_enter_warehouseAVC.setText(warehouseAVCCol.getCellData(index).toString());
    }

    public void Edit (){
        try {
            conn = mysqlconnect.ConnectDB();
            String value1 = txt_enter_warehouseID.getText();
            String value2 = txt_enter_warehouseName.getText();
            String value3 = txt_enter_warehouseAVC.getText();
            String value4 = txt_enter_warehouseCap.getText();
            String value5 = txt_enter_warehouseLoc.getText();

            String sql = "UPDATE warehouse SET wname = '" + value2 +
                    "', wlocation = '" + value5 +
                    "', wcapacity = " + value4 +
                    " , wavailableCars = " + value3 +
                    " WHERE wid = " + value1;

            pst= conn.prepareStatement(sql);
            pst.execute();

            UpdateTable();
            clearLabels();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void Delete(){
        conn = mysqlconnect.ConnectDB();
        String sql = "delete from warehouse where wid = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_enter_warehouseID.getText());
            pst.execute();

            UpdateTable();
            clearLabels();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    @FXML
    void Search (KeyEvent event) {

        conn = mysqlconnect.ConnectDB();
        String selection = attributeSelection.getValue();

        if (selection == null || selection.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a value from the ComboBox before searching.");
            return; // Exit the method to prevent further execution
        }

        String sql = "SELECT * FROM warehouse WHERE " + selection + " LIKE ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + WarehousesSearch.getText() + "%");
            rs = pst.executeQuery();
            ObservableList<Warehouse> list = FXCollections.observableArrayList();
            while (rs.next()) {
                list.add(new Warehouse(
                        Integer.parseInt(rs.getString("wid")),
                        Integer.parseInt(rs.getString("wcapacity")),
                        Integer.parseInt(rs.getString("wavailableCars")),
                        rs.getString("wlocation"),
                        rs.getString("wname")
                ));
            }
            warehouseTable.setItems(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void UpdateTable(){
        WidColMain.setCellValueFactory(new PropertyValueFactory<Warehouse, Integer>("Wid"));
        warehouseNameCol.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("name"));
        warehouseAVCCol.setCellValueFactory(new PropertyValueFactory<Warehouse, Integer>("availableCars"));
        warehouseCapacityCol.setCellValueFactory(new PropertyValueFactory<Warehouse, Integer>("capacity"));
        warehouseLocationCol.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("location"));

        listM = mysqlconnect.getDataWarehouses();
        warehouseTable.setItems(listM);

    }

    public void clearLabels() {
        txt_enter_warehouseCap.setText("");
        txt_enter_warehouseLoc.setText("");
        txt_enter_warehouseName.setText("");
    }

    public boolean areTextFieldsFilled() {
        if (    txt_enter_warehouseCap.getText().isEmpty() ||
                txt_enter_warehouseLoc.getText().isEmpty() ||
                txt_enter_warehouseName.getText().isEmpty()  ) {

            JOptionPane.showMessageDialog(null, "Please fill in all the fields before proceeding.");
            return false;
        }
        return true;
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        attributeSelection.setItems(FXCollections.observableArrayList("wid",  "wname", "wlocation", "wcapacity", "wavailableCars"));
        UpdateTable();
    }


}