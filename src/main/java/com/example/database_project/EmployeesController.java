package com.example.database_project;

import java.awt.Component;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
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
import javax.swing.JOptionPane;

public class EmployeesController implements Initializable {
   @FXML
   private Button AddEmployeeButton;
   @FXML
   private Button AddPhoneNumberButton;
   @FXML
   private Button updEmployeephoneButton1;
   @FXML
   private Button EmployeephoneDeleteButton1;
   @FXML
   private Button updEmployeephoneButton;
   @FXML
   private TableColumn<employees, Integer> EidCol;
   @FXML
   private TableColumn<employees, Integer> EidColMain;
   @FXML
   private TextField EmployeeAddressData;
   @FXML
   private Button EmployeeClearAllButton;
   @FXML
   private Button EmployeeDeleteButton;
   @FXML
   private TextField EmployeeIdData;
   @FXML
   private TextField EmployeeIdData1;
   @FXML
   private TextField EmployeeNameData;
   @FXML
   private TextField EmployeePhoneNumberData;
   @FXML
   private TextField EmployeeSalary;
   @FXML
   private TextField EmployeeSearch;
   @FXML
   private TextField newphone;
   @FXML
   private AnchorPane EmployeeWindow;
   @FXML
   private TableView<employeePhones> EmployeesPhoneNumberTable;
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
   private TableColumn<employees, String> employeeAddressCol;
   @FXML
   private TableColumn<employees, String> employeeNameCol;
   @FXML
   private TableColumn<employees, String> employeePhoneNumberCol;
   @FXML
   private TableColumn<employees, Integer> employeeSalaryCol;
   @FXML
   private TableView<employees> employeeTable;
   @FXML
   private Button employeesButton;
   @FXML
   private Button exitButton;
   @FXML
   private Button homeButton;
   @FXML
   private Button tranButton;
   @FXML
   private Button suppliersButton;
   @FXML
   private Button updEmployeeButton;
   @FXML
   private Button warehousesButton;
   ObservableList<employees> listM;
   ObservableList<employeePhones> listP;
   int index = -1;
   Connection conn = null;
   ResultSet rs = null;
   PreparedStatement pst = null;

   @FXML
   void AddEmployeeButton(ActionEvent event) {
      this.conn = mysqlconnect.ConnectDB();
      String sql = "INSERT INTO employee (name, address, salary) VALUES (?, ?, ?)";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, this.EmployeeNameData.getText());
         this.pst.setString(2, this.EmployeeAddressData.getText());
         this.pst.setString(3, this.EmployeeSalary.getText());
         this.pst.execute();
         JOptionPane.showMessageDialog((Component)null, "Customer added successfully");
      } catch (Exception var4) {
         JOptionPane.showMessageDialog((Component)null, var4.getMessage());
      }

      this.UpdateTable();
   }

   @FXML
   void AddPhoneNumberButton(ActionEvent event) {
      this.conn = mysqlconnect.ConnectDB();
      String sql = "INSERT INTO Employeephone (phoneNumber, Eid) VALUES (?, ?)";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, this.EmployeePhoneNumberData.getText());
         this.pst.setString(2, this.EmployeeIdData1.getText());
         this.pst.execute();
         JOptionPane.showMessageDialog((Component)null, "Phone number added successfully");
      } catch (Exception var4) {
         JOptionPane.showMessageDialog((Component)null, var4.getMessage());
      }

      this.UpdateTable();
   }

   @FXML
   void EmployeeClearAllButton(ActionEvent event) {
      this.EmployeeNameData.clear();
      this.EmployeeAddressData.clear();
      this.EmployeeSalary.clear();
      this.EmployeePhoneNumberData.clear();
      this.EmployeeIdData1.clear();
   }

   @FXML
   void EmployeephoneDeleteButton(ActionEvent event) {
      this.conn = mysqlconnect.ConnectDB();
      String sql = "DELETE FROM Employeephone WHERE Eid = ? and phoneNumber = ?";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, this.EmployeeIdData1.getText());
         this.pst.setString(2, this.EmployeePhoneNumberData.getText());
         this.pst.execute();
         JOptionPane.showMessageDialog((Component)null, "Phone number deleted successfully");
      } catch (Exception var4) {
         JOptionPane.showMessageDialog((Component)null, var4.getMessage());
      }

      this.UpdateTable();
   }

   @FXML
   void EmployeeDeleteButton(ActionEvent event) {
      this.conn = mysqlconnect.ConnectDB();
      String sql = "DELETE FROM employee WHERE Eid = ?";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, this.EmployeeIdData.getText());
         this.pst.execute();
         JOptionPane.showMessageDialog((Component)null, "Employee deleted successfully");
      } catch (Exception var5) {
         JOptionPane.showMessageDialog((Component)null, var5.getMessage());
      }

      sql = "DELETE FROM Employeephone WHERE Eid = ? and phoneNumber = ?";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, this.EmployeeIdData1.getText());
         this.pst.setString(2, this.EmployeePhoneNumberData.getText());
         this.pst.execute();
         JOptionPane.showMessageDialog((Component)null, "Phone number deleted successfully");
      } catch (Exception var4) {
         JOptionPane.showMessageDialog((Component)null, var4.getMessage());
      }

      this.UpdateTable();
   }

   @FXML
   void Search(KeyEvent event) {
      this.conn = mysqlconnect.ConnectDB();
      String selection = (String)this.attributeSelection.getValue();
      String sql = "SELECT * FROM employee WHERE " + selection + " LIKE ?";

      ObservableList list;
      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, "%" + this.EmployeeSearch.getText() + "%");
         this.rs = this.pst.executeQuery();
         list = FXCollections.observableArrayList();

         while(this.rs.next()) {
            list.add(new employees(this.rs.getInt("Eid"), this.rs.getString("name"), this.rs.getString("address"), this.rs.getInt("salary")));
         }

         this.employeeTable.setItems(list);
      } catch (Exception var6) {
         System.out.println(var6.getMessage());
      }

      sql = "SELECT * FROM Employeephone WHERE " + selection + " LIKE ?";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, "%" + this.EmployeeSearch.getText() + "%");
         this.rs = this.pst.executeQuery();
         list = FXCollections.observableArrayList();

         while(this.rs.next()) {
            list.add(new employeePhones(this.rs.getInt("Eid"), this.rs.getString("phoneNumber")));
         }

         this.EmployeesPhoneNumberTable.setItems(list);
      } catch (Exception var5) {
         System.out.println(var5.getMessage());
      }

   }

   @FXML
   void switchForm(ActionEvent event) throws IOException {
      Map<Button, String> buttonToFXMLMap = new HashMap();
      buttonToFXMLMap.put(this.dashBoardButton, "Dashboard.fxml");
      buttonToFXMLMap.put(this.warehousesButton, "Warehouses.fxml");
      buttonToFXMLMap.put(this.clientButton, "Customers.fxml");
      buttonToFXMLMap.put(this.carsButton, "Cars.fxml");
      buttonToFXMLMap.put(this.employeesButton, "Employees.fxml");
      buttonToFXMLMap.put(this.tranButton, "Transaction.fxml");
      buttonToFXMLMap.put(this.suppliersButton, "Suppliers.fxml");
      buttonToFXMLMap.put(this.exitButton, "Login.fxml");
      buttonToFXMLMap.put(homeButton, "Home.fxml");

      Button sourceButton = (Button)event.getSource();
      String fxmlFile = (String)buttonToFXMLMap.get(sourceButton);
      if (fxmlFile != null) {
         Parent root = (Parent)FXMLLoader.load(this.getClass().getResource(fxmlFile));
         Scene scene = new Scene(root);
         Stage regStage = new Stage();
         regStage.setScene(scene);
         regStage.show();
         sourceButton.getScene().getWindow().hide();
      }

   }

   private void UpdateTable() {
      this.EidColMain.setCellValueFactory(new PropertyValueFactory("eid"));
      this.employeeNameCol.setCellValueFactory(new PropertyValueFactory("name"));
      this.employeeAddressCol.setCellValueFactory(new PropertyValueFactory("address"));
      this.employeeSalaryCol.setCellValueFactory(new PropertyValueFactory("salary"));
      this.employeePhoneNumberCol.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
      this.EidCol.setCellValueFactory(new PropertyValueFactory("eid"));
      this.listM = mysqlconnect.getDataEmployee();
      if (this.listM != null && !this.listM.isEmpty()) {
         System.out.println("Data loaded successfully.");
      } else {
         System.out.println("No data retrieved.");
      }

      this.listP = mysqlconnect.getDataEmployeephone();
      if (this.listP != null && !this.listP.isEmpty()) {
         System.out.println("Data loaded successfully.");
      } else {
         System.out.println("No data retrieved.");
      }

      this.employeeTable.setItems(this.listM);
      this.EmployeesPhoneNumberTable.setItems(this.listP);
   }

   @FXML
   void updEmployeeButton(ActionEvent event) {
      this.conn = mysqlconnect.ConnectDB();
      String sql = "UPDATE employee SET name = ?, address = ?, salary = ? WHERE Eid = ?";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, this.EmployeeNameData.getText());
         this.pst.setString(2, this.EmployeeAddressData.getText());
         this.pst.setString(3, this.EmployeeSalary.getText());
         this.pst.setString(4, this.EmployeeIdData.getText());
         this.pst.execute();
         JOptionPane.showMessageDialog((Component)null, "Employee updated successfully");
      } catch (Exception var4) {
         JOptionPane.showMessageDialog((Component)null, var4.getMessage());
      }

      this.UpdateTable();
   }

   @FXML
   private void getSelected(MouseEvent event) {
      this.index = this.employeeTable.getSelectionModel().getSelectedIndex();
      if (this.index > -1) {
         this.EmployeeIdData.setText(((Integer)this.EidColMain.getCellData(this.index)).toString());
         this.EmployeeNameData.setText(((String)this.employeeNameCol.getCellData(this.index)).toString());
         this.EmployeeAddressData.setText(((String)this.employeeAddressCol.getCellData(this.index)).toString());
         this.EmployeeSalary.setText(((Integer)this.employeeSalaryCol.getCellData(this.index)).toString());
      }
   }

   @FXML
   private void updEmployeephoneButton(ActionEvent event) {
      this.conn = mysqlconnect.ConnectDB();
      String sql = "UPDATE Employeephone SET phoneNumber = ? WHERE Eid = ? and phoneNumber = ?";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, this.newphone.getText());
         this.pst.setString(2, this.EmployeeIdData1.getText());
         this.pst.setString(3, this.EmployeePhoneNumberData.getText());
         this.pst.execute();
         JOptionPane.showMessageDialog((Component)null, "Phone number updated successfully");
      } catch (Exception var4) {
         JOptionPane.showMessageDialog((Component)null, var4.getMessage());
      }

      this.UpdateTable();
   }

   @FXML
   private void getSelectedphone(MouseEvent event) {
      this.index = this.EmployeesPhoneNumberTable.getSelectionModel().getSelectedIndex();
      if (this.index > -1) {
         this.EmployeeIdData1.setText(((Integer)this.EidCol.getCellData(this.index)).toString());
         this.EmployeePhoneNumberData.setText(((String)this.employeePhoneNumberCol.getCellData(this.index)).toString());
      }
   }

   public void initialize(URL location, ResourceBundle resources) {
      this.attributeSelection.setItems(FXCollections.observableArrayList(new String[]{"Eid", "Name", "Address", "Salary", "phoneNumber"}));
      this.UpdateTable();
   }
}
