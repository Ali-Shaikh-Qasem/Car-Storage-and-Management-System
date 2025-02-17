package com.example.database_project;

import java.time.LocalDate;

public class Transaction {
   public Integer Transaction_Id;
   public Integer Customer_Id;
   public Integer Car_Id;
   public Integer emp_id;
   public LocalDate Date;
   public String Pay_Type;
   public Double Discount;
   public Double Paid_Amount;
   public Double price;
   public Double TotalPrice;
   public Double Remaining;

   public Transaction(int Transaction_Id, int Customer_Id, int Car_Id, int emp_id, LocalDate date, String pay_Type, double price, double discount, double paid_Amount, double totalPrice, double remaining) {
      this.Transaction_Id = Transaction_Id;
      this.Customer_Id = Customer_Id;
      this.Car_Id = Car_Id;
      this.emp_id = emp_id;
      this.Date = date;
      this.Pay_Type = pay_Type;
      this.price = price;
      this.Discount = discount;
      this.Paid_Amount = paid_Amount;
      this.TotalPrice = totalPrice;
      this.Remaining = remaining;
   }

   public Transaction(int Transaction_Id, int Customer_Id, int Car_Id, int emp_id, LocalDate date, String pay_Type, double price, double discount, double paid_Amount) {
      this.Transaction_Id = Transaction_Id;
      this.Customer_Id = Customer_Id;
      this.Car_Id = Car_Id;
      this.emp_id = emp_id;
      this.Date = date;
      this.Pay_Type = pay_Type;
      this.price = price;
      this.Discount = discount;
      this.Paid_Amount = paid_Amount;
   }

   public Transaction(int Transaction_Id, double totalPrice, double remaining) {
      this.Transaction_Id = Transaction_Id;
      this.TotalPrice = totalPrice;
      this.Remaining = remaining;
   }

   public Integer getTransaction_Id() {
      return this.Transaction_Id;
   }

   public void setTransaction_Id(Integer transaction_Id) {
      this.Transaction_Id = transaction_Id;
   }

   public Integer getCustomer_Id() {
      return this.Customer_Id;
   }

   public void setCustomer_Id(Integer customer_Id) {
      this.Customer_Id = customer_Id;
   }

   public Integer getCar_Id() {
      return this.Car_Id;
   }

   public void setCar_Id(Integer car_Id) {
      this.Car_Id = car_Id;
   }

   public Integer getEmp_id() {
      return this.emp_id;
   }

   public void setEmp_id(Integer emp_id) {
      this.emp_id = emp_id;
   }

   public LocalDate getDate() {
      return this.Date;
   }

   public void setDate(LocalDate date) {
      this.Date = date;
   }

   public String getPay_Type() {
      return this.Pay_Type;
   }

   public void setPay_Type(String pay_Type) {
      this.Pay_Type = pay_Type;
   }

   public Double getDiscount() {
      return this.Discount;
   }

   public void setDiscount(Double discount) {
      this.Discount = discount;
   }

   public Double getPaid_Amount() {
      return this.Paid_Amount;
   }

   public void setPaid_Amount(Double paid_Amount) {
      this.Paid_Amount = paid_Amount;
   }

   public Double getPrice() {
      return this.price;
   }

   public void setPrice(Double price) {
      this.price = price;
   }

   public Double getTotalPrice() {
      return this.TotalPrice;
   }

   public void setTotalPrice(Double totalPrice) {
      this.TotalPrice = totalPrice;
   }

   public Double getRemaining() {
      return this.Remaining;
   }

   public void setRemaining(Double remaining) {
      this.Remaining = remaining;
   }
}
