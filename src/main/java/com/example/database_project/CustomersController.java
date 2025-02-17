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

public class CustomersController implements Initializable {

    @FXML
    private Button AddcustomerButton;

    @FXML
    private TableColumn<Customers, Integer> CidColMain;

    @FXML
    private Button CustomerClearAllButton;

    @FXML
    private Button DeleteCustomerButton;

    @FXML
    private AnchorPane EmployeeWindow;

    @FXML
    private Button UpdateCustomerButton;

    @FXML
    private BorderPane ViewSupplierWindow;

    @FXML
    private ComboBox<String> attributeSelection;

    @FXML
    private Button carsButton;

    @FXML
    private Button clientButton;

    @FXML
    private TableColumn<Customers, String> customerAddressCol;

    @FXML
    private TableColumn<Customers, Integer> customerAgeCol;

    @FXML
    private TableColumn<Customers, Integer> customerLidCol;

    @FXML
    private TableColumn<Customers, String > customerNameCol;

    @FXML
    private TableColumn<Customers, String > customerPnumCol;

    @FXML
    private TextField customerSearch;

    @FXML
    private TableView<Customers> customersTable;

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
    private TextField txt_enter_customerAge;

    @FXML
    private TextField txt_enter_customerLID;

    @FXML
    private TextField txt_enter_customerName;

    @FXML
    private TextField txt_enter_customerAddress;

    @FXML
    private TextField txt_enter_customerPnum;

    @FXML
    private TextField txt_enter_customerID;

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

    ObservableList<Customers> listM;
    ObservableList<Customers> dataList;

    int index = -1;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_Customers (){

        if (!areTextFieldsFilled()) {
            return;
        }

        conn = mysqlconnect.ConnectDB();
        String sql = "INSERT INTO customer (cname, caddress, cage, cLicenceID, cphone) VALUES ( ?, ?, ?, ?, ?);\n";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_enter_customerName.getText());
            pst.setString(2, txt_enter_customerAddress.getText());
            pst.setString(3, txt_enter_customerAge.getText());
            pst.setString(4, txt_enter_customerLID.getText());
            pst.setString(5, txt_enter_customerPnum.getText());
            pst.execute();

            UpdateTable();
            clearLabels();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void getSelected (MouseEvent event){
        index = customersTable.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        txt_enter_customerID.setText(CidColMain.getCellData(index).toString());
        txt_enter_customerLID.setText(customerLidCol.getCellData(index).toString());
        txt_enter_customerAge.setText(customerAgeCol.getCellData(index).toString());
        txt_enter_customerName.setText(customerNameCol.getCellData(index).toString());
        txt_enter_customerAddress.setText(customerAddressCol.getCellData(index).toString());
        txt_enter_customerPnum.setText(customerPnumCol.getCellData(index).toString());
    }

    public void Edit (){
        try {
            conn = mysqlconnect.ConnectDB();
            String value1 = txt_enter_customerID.getText();
            String value2 = txt_enter_customerAddress.getText();
            String value3 = txt_enter_customerAge.getText();
            String value4 = txt_enter_customerPnum.getText();
            String value5 = txt_enter_customerLID.getText();
            String value6 = txt_enter_customerName.getText();

            String sql = "UPDATE customer SET cname = '" + value6 + "', caddress = '" + value2 +
                    "', cage = '" + value3 + "', cLicenceID = '" + value5 +
                    "', cphone = '" + value4 + "' WHERE cid = '" + value1 + "'";

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
        String sql = "delete from customer where cid = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_enter_customerID.getText());
            pst.execute();

            UpdateTable();
            clearLabels();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    @FXML
    void Search (KeyEvent event) {

        conn = mysqlconnect.ConnectDB(); // Corrected method name for ConnectDb
        String selection = attributeSelection.getValue();

        if (selection == null || selection.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a value from the ComboBox before searching.");
            return; // Exit the method to prevent further execution
        }

        String sql = "SELECT * FROM customer WHERE "+selection+" LIKE ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%"+customerSearch.getText()+"%");
            rs = pst.executeQuery();
            ObservableList<Customers> list = FXCollections.observableArrayList();
            while (rs.next()) {
                list.add(new Customers(
                        rs.getInt("cid"),
                        rs.getString("cphone"),
                        rs.getString("cname"),
                        rs.getString("caddress"),
                        rs.getInt("cage"),
                        rs.getInt("cLicenceID")
                ));
            }
            customersTable.setItems(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void UpdateTable(){
        CidColMain.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("Cid"));
        customerPnumCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("cphone"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("Cname"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("Caddress"));
        customerAgeCol.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("Cage"));
        customerLidCol.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("licenceID"));

        listM = mysqlconnect.getDataCustomers();
        customersTable.setItems(listM);

    }

    public void clearLabels() {
        txt_enter_customerPnum.setText("");
        txt_enter_customerLID.setText("");
        txt_enter_customerAge.setText("");
        txt_enter_customerAddress.setText("");
        txt_enter_customerName.setText("");
    }

    public boolean areTextFieldsFilled() {
        if (    txt_enter_customerAddress.getText().isEmpty() ||
                txt_enter_customerName.getText().isEmpty() ||
                txt_enter_customerAge.getText().isEmpty() ||
                txt_enter_customerLID.getText().isEmpty() ||
                txt_enter_customerPnum.getText().isEmpty() ) {

            JOptionPane.showMessageDialog(null, "Please fill in all the fields before proceeding.");
            return false;
        }
        return true;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        attributeSelection.setItems(FXCollections.observableArrayList("cid",  "cname", "caddress","cage", "cLicenceID", "cphone"));
        UpdateTable();
    }
}
