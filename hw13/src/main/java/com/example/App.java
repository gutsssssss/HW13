package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Step 1: Register JDBC driver
            Class.forName("com.microsoft.sqlserver");

            // Step 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433/hw13", "root", "677099whp");

            // Step 3: Execute a query to get the record
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, first, last, age FROM Employees";
            ResultSet rs = stmt.executeQuery(sql);

            // Step 4: Extract data from result set for the first record
            if(rs.next()){
                // Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                // Display values
                System.out.println("ID: " + id);
                System.out.println(", Age: " + age);
                System.out.println(", First: " + first);
                System.out.println(", Last: " + last);
            }

            // Step 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Finally block used to close resources
            try {
                if(stmt!=null){
                    stmt.close();
                }
            } catch(SQLException se2) {
            } // Nothing we can do
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch(SQLException se) {
                se.printStackTrace();
            } // End finally try
        } // End try
        System.out.println("Goodbye!");
    } // End main
} // End DatabaseAccess