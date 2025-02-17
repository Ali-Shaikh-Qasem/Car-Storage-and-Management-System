package com.example.database_project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class mysqlconnect {

    @SuppressWarnings("exports")
    Connection conn = null;

    @SuppressWarnings("exports")
    public static Connection ConnectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AL_Motamad_Auto", "root", "Ali123");
            System.out.println("Connected to database successfully.");
            return conn;
        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e);
            return null;
        }
    }



    public static ObservableList<employees> getDataEmployee() {
        ObservableList<employees> list = FXCollections.observableArrayList();
        Connection conn = ConnectDB(); // Get database connection
        if (conn == null) {
            System.out.println("Failed to connect to database.");
            return list;
        }

        try {
            // Prepare and execute the query
            String query = "SELECT * FROM employee";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and populate the ObservableList
            while (rs.next()) {
                list.add(new employees(rs.getInt("Eid"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("salary")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return list;

    }

    public static ObservableList<employeePhones> getDataEmployeephone() {
        ObservableList<employeePhones> list = FXCollections.observableArrayList();
        Connection conn = ConnectDB(); // Get database connection
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database.");
            return list;
        }

        try {
            // Prepare and execute the query
            String query = "SELECT * FROM Employeephone";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and populate the ObservableList
            while (rs.next()) {
                list.add(new employeePhones(rs.getInt("Eid"),
                        rs.getString("phoneNumber")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return list;

    }

    public static ObservableList<Transaction> getDataTransaction() {
        ObservableList<Transaction> list = FXCollections.observableArrayList();
        Connection conn = ConnectDB(); // Get database connection
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database.");
            return list;
        }

        try {
            // Prepare and execute the query
            String query = "SELECT * FROM transaction";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and populate the ObservableList
            while (rs.next()) {
                list.add(new Transaction(rs.getInt("Transaction_Id"), rs.getInt("emp_id"), rs.getInt("Car_Id"),
                        rs.getInt("Customer_Id"), rs.getDate("Date").toLocalDate(), rs.getString("Pay_Type"),
                        rs.getDouble("price"), rs.getDouble("Discount"), rs.getDouble("Paid_Amount")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return list;
    }

    public static ObservableList<Transaction> getDataTransactionprice() {
        ObservableList<Transaction> list = FXCollections.observableArrayList();
        Connection conn = ConnectDB(); // Get database connection
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database.");
            return list;
        }

        try {
            // Prepare and execute the query
            String query = "SELECT Transaction_Id, emp_id, Car_Id, Customer_Id, Date, Pay_Type, price, Discount, Paid_Amount, " +
                    "(price  - Discount) AS TotalPrice, " +
                    "((price  - Discount) - Paid_Amount) AS Remaining " +
                    "FROM transaction";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and populate the ObservableList
            while (rs.next()) {
                list.add(new Transaction(
                        rs.getInt("Transaction_Id"),
                        rs.getInt("emp_id"),
                        rs.getInt("Car_Id"),
                        rs.getInt("Customer_Id"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getString("Pay_Type"),
                        rs.getDouble("price"),
                        rs.getDouble("Discount"),
                        rs.getDouble("Paid_Amount"),
                        rs.getDouble("TotalPrice"),
                        rs.getDouble("Remaining")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return list;
    }

    public static ObservableList<Transaction> getDataTransactionunpaid() {
        ObservableList<Transaction> list = FXCollections.observableArrayList();
        Connection conn = ConnectDB(); // Get database connection
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database.");
            return list;
        }

        try {
            // Prepare and execute the query
            String query = "SELECT Transaction_Id, emp_id, Car_Id, Customer_Id, Date, Pay_Type, price, Discount, Paid_Amount, " +
                    "(price  - Discount) AS TotalPrice, " +
                    "((price  - Discount) - Paid_Amount) AS Remaining " +
                    "FROM transaction where ((price  - Discount) - Paid_Amount) > 0";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and populate the ObservableList
            while (rs.next()) {
                list.add(new Transaction(
                        rs.getInt("Transaction_Id"),
                        rs.getInt("emp_id"),
                        rs.getInt("Car_Id"),
                        rs.getInt("Customer_Id"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getString("Pay_Type"),
                        rs.getDouble("price"),
                        rs.getDouble("Discount"),
                        rs.getDouble("Paid_Amount"),
                        rs.getDouble("TotalPrice"),
                        rs.getDouble("Remaining")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return list;
    }

    public static double getTodaysIncome() {
        double totalPaidAmountToday = 0.0;  // Initialize the total paid amount
        Connection conn = ConnectDB(); // Get database connection
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database.");
            return totalPaidAmountToday;
        }

        try {
            // Prepare and execute the query to get the total paid amount for today
            String query = "SELECT SUM(Paid_Amount) AS TotalPaidAmountToday "
                    + "FROM transaction WHERE DATE(Date) = CURDATE();";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // If a result exists, retrieve the total paid amount
            if (rs.next()) {
                totalPaidAmountToday = rs.getDouble("TotalPaidAmountToday");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return totalPaidAmountToday;
    }
    public static ObservableList<Cars> getDataCars() {
        Connection conn = ConnectDB();
        ObservableList<Cars> list = FXCollections.observableArrayList();

        if (conn == null) {
            return list; // Return an empty list if connection fails.
        }

        try {
            PreparedStatement ps = conn.prepareStatement("select * from car");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Debugging output
                list.add(new Cars(
                        Integer.parseInt(rs.getString("cid")),
                        Integer.parseInt(rs.getString("wid")),
                        rs.getDouble("cPrice"),
                        rs.getString("cColor"),
                        Integer.parseInt(rs.getString("cyear")),
                        rs.getString("cmodel"),
                        rs.getString("cmake"),
                        Integer.parseInt(rs.getString("sid"))
                ));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return list;
    }

    public static ObservableList<Customers> getDataCustomers() {
        Connection conn = ConnectDB();
        ObservableList<Customers> list = FXCollections.observableArrayList();

        if (conn == null) {
            return list; // Return an empty list if connection fails.
        }

        try {
            PreparedStatement ps = conn.prepareStatement("select * from customer");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Debugging output
                list.add(new Customers(
                        Integer.parseInt(rs.getString("cid")), // Customer ID
                        rs.getString("cphone"),                // Customer phone
                        rs.getString("cname"),                 // Customer name
                        rs.getString("caddress"),              // Customer address
                        Integer.parseInt(rs.getString("cage")),// Customer age
                        Integer.parseInt(rs.getString("cLicenceID"))// Licence ID
                ));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return list;
    }

    public static ObservableList<Suppliers> getDataSuppliers() {
        Connection conn = ConnectDB();
        ObservableList<Suppliers> list = FXCollections.observableArrayList();

        if (conn == null) {
            return list; // Return an empty list if connection fails.
        }

        try {
            PreparedStatement ps = conn.prepareStatement("select * from supplier");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Debugging output
                list.add(new Suppliers(
                        Integer.parseInt(rs.getString("sid")),
                        rs.getString("sname"),
                        rs.getString("sphoneNum"),
                        rs.getString("scountry"),
                        rs.getDouble("srating")
                ));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return list;
    }

    public static ObservableList<Warehouse> getDataWarehouses() {
        Connection conn = ConnectDB();
        ObservableList<Warehouse> list = FXCollections.observableArrayList();

        if (conn == null) {
            return list; // Return an empty list if connection fails.
        }

        try {
            PreparedStatement ps = conn.prepareStatement("select * from warehouse");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Debugging output
                list.add(new Warehouse(
                        Integer.parseInt(rs.getString("wid")),
                        Integer.parseInt(rs.getString("wcapacity")),
                        Integer.parseInt(rs.getString("wavailableCars")),
                        rs.getString("wlocation"),
                        rs.getString("wname")
                ));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return list;
    }

    public static ObservableList<Warehouse> getDataWarehousesCapacities() {
        Connection conn = ConnectDB();
        ObservableList<Warehouse> list = FXCollections.observableArrayList();

        if (conn == null) {
            return list; // Return an empty list if connection fails.
        }

        try {
            PreparedStatement ps = conn.prepareStatement("select * from warehouse order by (wcapacity - wavailableCars) desc");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Debugging output
                list.add(new Warehouse(
                        Integer.parseInt(rs.getString("wid")),
                        Integer.parseInt(rs.getString("wcapacity")),
                        Integer.parseInt(rs.getString("wavailableCars")),
                        rs.getString("wlocation"),
                        rs.getString("wname")
                ));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return list;
    }

    public static int getNumofEmployees() {
        int numEmployees = 0;  // Initialize the total paid amount
        Connection conn = ConnectDB(); // Get database connection
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database.");
            return numEmployees;
        }

        try {
            // Prepare and execute the query to get the total paid amount for today
            String query = "SELECT COUNT(*) AS num_employees\n" +
                           "FROM employee;";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // If a result exists, retrieve the total paid amount
            if (rs.next()) {
                numEmployees = rs.getInt("num_employees");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return numEmployees;
    }

    public static ObservableList<carBrandSales> getDataCarBrandsSales(int year, int month) {
        Connection conn = ConnectDB();
        ObservableList<carBrandSales> list = FXCollections.observableArrayList();

        if (conn == null) {
            return list;
        }

        try {
            // Prepare the SQL query with placeholders for year and month
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT c.cmake AS Car_Brand, COUNT(t.Transaction_Id) AS Total_Sales " +
                            "FROM Transaction t, car c " +
                            "WHERE t.Car_Id = c.cid " +
                            "  AND MONTH(t.Date) = ? " +
                            "  AND YEAR(t.Date) = ? " +
                            "GROUP BY c.cmake " +
                            "ORDER BY Total_Sales DESC;"
            );

            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String carBrand = rs.getString("Car_Brand");
                int totalSales = rs.getInt("Total_Sales");
                list.add(new carBrandSales(carBrand, totalSales));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return list;
    }

    public static Map<String, Double> getDataTransactionPaidByMonthYear(int year) {
        Map<String, Double> dataMap = new HashMap<>();
        Connection conn = ConnectDB(); // Get database connection
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database.");
            return dataMap;
        }

        try {
            // Prepare and execute the query
            String query = "SELECT MONTH(Date) AS TransactionMonth, SUM(Paid_Amount) AS TotalPaid " +
                    "FROM transaction " +
                    "WHERE YEAR(Date) = ? " +
                    "GROUP BY MONTH(Date) " +
                    "ORDER BY MONTH(Date)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and populate the Map
            while (rs.next()) {
                String monthName = getMonthName(rs.getInt("TransactionMonth")); // Convert month number to name
                double totalPaid = rs.getDouble("TotalPaid");
                dataMap.put(monthName, totalPaid);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return dataMap;
    }

    // Utility method to convert month number to name
    public static String getMonthName(int month) {
        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        return (month >= 1 && month <= 12) ? months[month - 1] : "Unknown";
    }

    public static int getNumofCustomer() {
        int num_ofcustomer = 0;  // Initialize the total paid amount
        Connection conn = ConnectDB(); // Get database connection
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database.");
            return num_ofcustomer;
        }



        try {
            // Prepare and execute the query to get the total paid amount for today
            String query = "SELECT COUNT(*) AS num_ofcustomer\n" +
                    "FROM customer";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // If a result exists, retrieve the total paid amount
            if (rs.next()) {
                num_ofcustomer = rs.getInt("num_ofcustomer");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return num_ofcustomer;
    }

    public static int getNumofsuppliers() {
        int numsupplier = 0;  // Initialize the total paid amount
        Connection conn = ConnectDB(); // Get database connection
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database.");
            return numsupplier;
        }

        try {
            // Prepare and execute the query to get the total paid amount for today
            String query = "SELECT COUNT(*) AS supplier\n" +
                    "FROM supplier;";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // If a result exists, retrieve the total paid amount
            if (rs.next()) {
                numsupplier = rs.getInt("supplier");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        }
        return numsupplier;
    }

    public static ObservableList<Cars> getDataAvailableCars() {
        Connection conn = ConnectDB();
        ObservableList<Cars> list = FXCollections.observableArrayList();

        if (conn == null) {
            return list; // Return an empty list if connection fails.
        }

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT *\r\n"
                    + "FROM car\r\n"
                    + "WHERE cid NOT IN (\r\n"
                    + "    SELECT Car_Id FROM Transaction\r\n"
                    + ");\r\n"
                    + "");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Debugging output
                list.add(new Cars(
                        Integer.parseInt(rs.getString("cid")),
                        Integer.parseInt(rs.getString("wid")),
                        rs.getDouble("cPrice"),
                        rs.getString("cColor"),
                        Integer.parseInt(rs.getString("cyear")),
                        rs.getString("cmodel"),
                        rs.getString("cmake"),
                        Integer.parseInt(rs.getString("sid"))
                ));

            }
        } catch (Exception e) {
        }
        return list;
    }


}