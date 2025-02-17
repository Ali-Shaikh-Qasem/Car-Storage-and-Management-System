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

public class SuppliersController implements Initializable {

    @FXML
    private Button AddSupplierButton;

    @FXML
    private Button DeleteSupplierButton;

    @FXML
    private AnchorPane EmployeeWindow;

    @FXML
    private TableColumn<Suppliers, Integer> SidColMain;

    @FXML
    private Button UpdateSupplierButton;

    @FXML
    private BorderPane ViewSupplierWindow;

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
    private Button supplierClearAllButton;

    @FXML
    private TableColumn<Suppliers, String> supplierCountryCol;

    @FXML
    private TableColumn<Suppliers, String> supplierNameCol;

    @FXML
    private TableColumn<Suppliers, Double> supplierRateCol;

    @FXML
    private TextField supplierSearch;

    @FXML
    private TableColumn<Suppliers, String> supplierrPhoneCol;

    @FXML
    private Button suppliersButton;

    @FXML
    private TableView<Suppliers> suppliersTable;

    @FXML
    private Button tranButton;

    @FXML
    private TextField txt_enter_supplierCountry;

    @FXML
    private TextField txt_enter_supplierName;

    @FXML
    private TextField txt_enter_supplierPhone;

    @FXML
    private TextField txt_enter_supplierRate;

    @FXML
    private TextField txt_enter_supplierID;

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

    ObservableList<Suppliers> listM;
    ObservableList<Suppliers> dataList;

    int index = -1;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_Suppliers (){

        if (!areTextFieldsFilled()) {
            return;
        }

        conn = mysqlconnect.ConnectDB();
        String sql = "INSERT INTO supplier (sname, sphoneNum, scountry, srating) VALUES ( ?, ?, ?, ?);\n";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_enter_supplierName.getText());
            pst.setString(2, txt_enter_supplierPhone.getText());
            pst.setString(3, txt_enter_supplierCountry.getText());
            pst.setString(4, txt_enter_supplierRate.getText());
            pst.execute();

            UpdateTable();
            clearLabels();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void getSelected (MouseEvent event){
        index = suppliersTable.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        txt_enter_supplierID.setText(SidColMain.getCellData(index).toString());
        txt_enter_supplierRate.setText(supplierRateCol.getCellData(index).toString());
        txt_enter_supplierCountry.setText(supplierCountryCol.getCellData(index).toString());
        txt_enter_supplierPhone.setText(supplierrPhoneCol.getCellData(index).toString());
        txt_enter_supplierName.setText(supplierNameCol.getCellData(index).toString());
    }

    public void Edit (){
        try {
            conn = mysqlconnect.ConnectDB();
            String value1 = txt_enter_supplierID.getText();
            String value2 = txt_enter_supplierName.getText();
            String value3 = txt_enter_supplierPhone.getText();
            String value4 = txt_enter_supplierCountry.getText();
            String value5 = txt_enter_supplierRate.getText();

            String sql = "UPDATE warehouse SET sname = '" + value2 +
                    "', sphoneNum = '" + value3 +
                    "', scountry = '" + value4 +
                    "', srating = " + value5 +
                    " WHERE sid = " + value1;

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
        String sql = "delete from supplier where sid = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_enter_supplierID.getText());
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

        String sql = "SELECT * FROM supplier WHERE "+selection+" LIKE ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%"+supplierSearch.getText()+"%");
            rs = pst.executeQuery();
            ObservableList<Suppliers> list = FXCollections.observableArrayList();
            while (rs.next()) {
                list.add(new Suppliers(
                        Integer.parseInt(rs.getString("sid")),
                        rs.getString("sname"),
                        rs.getString("scountry"),
                        rs.getString("sphoneNum"),
                        rs.getDouble("srating")
                ));
            }
            suppliersTable.setItems(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    public void UpdateTable(){
        SidColMain.setCellValueFactory(new PropertyValueFactory<Suppliers, Integer>("Sid"));
        supplierNameCol.setCellValueFactory(new PropertyValueFactory<Suppliers, String>("Sname"));
        supplierCountryCol.setCellValueFactory(new PropertyValueFactory<Suppliers, String>("Scountry"));
        supplierrPhoneCol.setCellValueFactory(new PropertyValueFactory<Suppliers, String>("Sphone"));
        supplierRateCol.setCellValueFactory(new PropertyValueFactory<Suppliers, Double>("Srating"));

        listM = mysqlconnect.getDataSuppliers();
        suppliersTable.setItems(listM);

    }



    public void clearLabels() {
        txt_enter_supplierCountry.setText("");
        txt_enter_supplierName.setText("");
        txt_enter_supplierPhone.setText("");
        txt_enter_supplierRate.setText("");
    }

    public boolean areTextFieldsFilled() {
        if (    txt_enter_supplierCountry.getText().isEmpty() ||
                txt_enter_supplierName.getText().isEmpty() ||
                txt_enter_supplierPhone.getText().isEmpty() ||
                txt_enter_supplierRate.getText().isEmpty() ) {

            JOptionPane.showMessageDialog(null, "Please fill in all the fields before proceeding.");
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        attributeSelection.setItems(FXCollections.observableArrayList("sid",  "sname", "sphoneNum", "scountry", "srating"));
        UpdateTable();
    }
}
