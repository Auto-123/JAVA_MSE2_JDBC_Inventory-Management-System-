package com.kiet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertProduct {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/retail_db";
        String username = "root";
        String password = "admin";

        String sql = "INSERT INTO Product (product_id, product_name, category, quantity, price) VALUES (?, ?, ?, ?, ?)";

        try {

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection con = DriverManager.getConnection(
                    url, username, password);

            // Create PreparedStatement
            PreparedStatement ps = con.prepareStatement(sql);

            // Set values
            ps.setInt(1, 107);
            ps.setString(2, "Desk");
            ps.setString(3, "Furniture");
            ps.setInt(4, 12);
            ps.setDouble(5, 6000.00);
            ps.addBatch();



            // Execute INSERT
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Product inserted successfully!");
            }

            // Close resources
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}