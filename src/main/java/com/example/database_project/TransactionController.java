package com.example.database_project;

import java.awt.Component;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
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

public class TransactionController implements Initializable {
   @FXML
   private Button AddtranButton;
   @FXML
   private TextField EmpIDDATA;
   @FXML
   private AnchorPane EmployeeWindow;
   @FXML
   private TableColumn<Transaction, Integer> Transaction_Id;
   @FXML
   private BorderPane ViewSupplierWindow;
   @FXML
   private ComboBox<String> attributeSelection;
   @FXML
   private Button carsButton;
   @FXML
   private Button cleartranButton;
   @FXML
   private Button clientButton;
   @FXML
   private Button dashBoardButton;
   @FXML
   private Button deletetranButton;
   @FXML
   private TableView<Transaction> TransactionTable;
   @FXML
   private Button employeesButton;
   @FXML
   private Button exitButton;
   @FXML
   private Button homeButton;
   @FXML
   private TableView<Transaction> price;
   @FXML
   private TextField priceDATA;
   @FXML
   private Button suppliersButton;
   @FXML
   private Button tranButton;
   @FXML
   private TextField tranSearch;
   @FXML
   private TableColumn<Transaction, LocalDate> transationDATECol;
   @FXML
   private TableColumn<Transaction, Double> transationDISCOUNTCol;
   @FXML
   private TableColumn<Transaction, Integer> transationEmpIDCol;
   @FXML
   private TableColumn<Transaction, String> transationPAYTYPECol;
   @FXML
   private TableColumn<Transaction, Double> transationPaidAmountCol;
   @FXML
   private TableColumn<Transaction, Double> transationREMAININGCol;
   @FXML
   private TableColumn<Transaction, Double> transationTotalpriceCol;
   @FXML
   private TableColumn<Transaction, Integer> transationcarIDCol;
   @FXML
   private TableColumn<Transaction, Integer> transationcustIDCol;
   @FXML
   private TableColumn<Transaction, Double> transationpriceCol;
   @FXML
   private TableColumn<Transaction, Integer> transationtranidCol;
   @FXML
   private TableColumn<Transaction, Double> transationPaidAmountCol2;
   @FXML
   private TextField txt_enter_carID;
   @FXML
   private TextField txt_enter_cusID;
   @FXML
   private TextField txt_enter_date;
   @FXML
   private TextField txt_Paid_Amount;
   @FXML
   private TextField txt_enter_discount;
   @FXML
   private Label txt_enter_empID;
   @FXML
   private TextField txt_enter_paytype;
   @FXML
   private Label txt_enter_price;
   @FXML
   private TextField txt_enter_tranID;
   @FXML
   private Button updtranButton;
   @FXML
   private Button warehousesButton;
   ObservableList<Transaction> listM;
   ObservableList<Transaction> listP;
   int index = -1;
   Connection conn = null;
   ResultSet rs = null;
   PreparedStatement pst = null;

   @FXML
   void AddtranButton(ActionEvent event) {
      this.conn = mysqlconnect.ConnectDB();
      String sql = "INSERT INTO Transaction (Car_Id, Customer_Id, Emp_id, Date, Price, Pay_Type, Paid_Amount, Discount) VALUES(?,?,?,?,?,?,?,?)";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, this.txt_enter_carID.getText());
         this.pst.setString(2, this.txt_enter_cusID.getText());
         this.pst.setString(3, this.EmpIDDATA.getText());
         this.pst.setString(4, this.txt_enter_date.getText());
         this.pst.setString(5, this.priceDATA.getText());
         this.pst.setString(6, this.txt_enter_paytype.getText());
         this.pst.setString(7, this.txt_Paid_Amount.getText());
         this.pst.setString(8, this.txt_enter_discount.getText());
         this.pst.execute();
         JOptionPane.showMessageDialog((Component)null, "Transaction Added");
      } catch (Exception var4) {
         JOptionPane.showMessageDialog((Component)null, var4);
      }

      this.UpdateTable();
   }

   @FXML
   void Search(KeyEvent event) {
      this.conn = mysqlconnect.ConnectDB();
      String selection = (String)this.attributeSelection.getValue();
      String search = this.tranSearch.getText();
      String sql = "SELECT * FROM Transaction WHERE " + selection + " LIKE ?";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, "%" + search + "%");
         this.rs = this.pst.executeQuery();
         ObservableList list = FXCollections.observableArrayList();

         while(this.rs.next()) {
            list.add(new Transaction(this.rs.getInt("Transaction_Id"), this.rs.getInt("Customer_Id"), this.rs.getInt("Car_Id"), this.rs.getInt("Emp_id"), this.rs.getDate("Date").toLocalDate(), this.rs.getString("Pay_Type"), this.rs.getDouble("Paid_Amount"), this.rs.getDouble("Price"), this.rs.getDouble("Discount")));
         }

         this.TransactionTable.setItems(list);
      } catch (Exception var6) {
         System.out.println(var6);
      }

   }

   @FXML
   void cleartranButton(ActionEvent event) {
      this.txt_enter_carID.clear();
      this.txt_enter_cusID.clear();
      this.txt_enter_date.clear();
      this.txt_enter_discount.clear();
      this.EmpIDDATA.clear();
      this.txt_enter_paytype.clear();
      this.priceDATA.clear();
      this.txt_enter_tranID.clear();
      this.txt_Paid_Amount.clear();
   }

   @FXML
   void deletetranButton(ActionEvent event) {
      this.conn = mysqlconnect.ConnectDB();
      String sql = "DELETE FROM Transaction WHERE Transaction_Id = ?";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, this.txt_enter_tranID.getText());
         this.pst.execute();
         JOptionPane.showMessageDialog((Component)null, "Transaction Deleted");
      } catch (Exception var4) {
         JOptionPane.showMessageDialog((Component)null, var4);
      }

      this.UpdateTable();
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

   @FXML
   private void UpdateTable() {
      this.transationtranidCol.setCellValueFactory(new PropertyValueFactory("Transaction_Id"));
      this.transationcustIDCol.setCellValueFactory(new PropertyValueFactory("Customer_Id"));
      this.transationcarIDCol.setCellValueFactory(new PropertyValueFactory("Car_Id"));
      this.transationEmpIDCol.setCellValueFactory(new PropertyValueFactory("Emp_id"));
      this.transationDATECol.setCellValueFactory(new PropertyValueFactory("Date"));
      this.transationpriceCol.setCellValueFactory(new PropertyValueFactory("Price"));
      this.transationPAYTYPECol.setCellValueFactory(new PropertyValueFactory("Pay_Type"));
      this.transationPaidAmountCol.setCellValueFactory(new PropertyValueFactory("Paid_Amount"));
      this.transationDISCOUNTCol.setCellValueFactory(new PropertyValueFactory("Discount"));
      this.transationTotalpriceCol.setCellValueFactory(new PropertyValueFactory("TotalPrice"));
      this.transationPaidAmountCol2.setCellValueFactory(new PropertyValueFactory("Paid_Amount"));
      this.transationREMAININGCol.setCellValueFactory(new PropertyValueFactory("Remaining"));
      this.Transaction_Id.setCellValueFactory(new PropertyValueFactory("Transaction_Id"));
      this.listM = mysqlconnect.getDataTransactionprice();
      if (this.listM.isEmpty()) {
         System.out.println("No data in the database");
      } else {
         System.out.println("Data found");
      }

      this.TransactionTable.setItems(this.listM);
      this.price.setItems(this.listM);
   }

   @FXML
   void updtranButton(ActionEvent event) {
      this.conn = mysqlconnect.ConnectDB();
      String sql = "UPDATE Transaction SET Car_Id = ?, Customer_Id = ?, Emp_id = ?, Date = ?, Price = ?, Pay_Type = ?, Paid_Amount = ?, Discount = ? WHERE Transaction_Id = ?";

      try {
         this.pst = this.conn.prepareStatement(sql);
         this.pst.setString(1, this.txt_enter_carID.getText());
         this.pst.setString(2, this.txt_enter_cusID.getText());
         this.pst.setString(3, this.EmpIDDATA.getText());
         this.pst.setString(4, this.txt_enter_date.getText());
         this.pst.setString(5, this.priceDATA.getText());
         this.pst.setString(6, this.txt_enter_paytype.getText());
         this.pst.setString(7, this.txt_Paid_Amount.getText());
         this.pst.setString(8, this.txt_enter_discount.getText());
         this.pst.setString(9, this.txt_enter_tranID.getText());
         this.pst.execute();
         JOptionPane.showMessageDialog((Component)null, "Transaction Updated");
      } catch (Exception var4) {
         JOptionPane.showMessageDialog((Component)null, var4);
      }

      this.UpdateTable();
   }

   @FXML
   private void getSelected(MouseEvent event) {
      this.index = this.TransactionTable.getSelectionModel().getSelectedIndex();
      if (this.index > -1) {
         this.txt_enter_tranID.setText(((Integer)this.transationtranidCol.getCellData(this.index)).toString());
         this.txt_enter_cusID.setText(((Integer)this.transationcustIDCol.getCellData(this.index)).toString());
         this.txt_enter_carID.setText(((Integer)this.transationcarIDCol.getCellData(this.index)).toString());
         this.EmpIDDATA.setText(((Integer)this.transationEmpIDCol.getCellData(this.index)).toString());
         this.txt_enter_date.setText(((LocalDate)this.transationDATECol.getCellData(this.index)).toString());
         this.priceDATA.setText(((Double)this.transationpriceCol.getCellData(this.index)).toString());
         this.txt_enter_paytype.setText(((String)this.transationPAYTYPECol.getCellData(this.index)).toString());
         this.txt_Paid_Amount.setText(((Double)this.transationPaidAmountCol.getCellData(this.index)).toString());
         this.txt_enter_discount.setText(((Double)this.transationDISCOUNTCol.getCellData(this.index)).toString());
      }
   }

   public void initialize(URL location, ResourceBundle resources) {
      this.UpdateTable();
      this.attributeSelection.setItems(FXCollections.observableArrayList(new String[]{"Transaction_Id", "Customer_Id", "Car_Id", "Emp_id", "Date", "Pay_Type", "Paid_Amount", "Price", "Discount"}));
   }
}
