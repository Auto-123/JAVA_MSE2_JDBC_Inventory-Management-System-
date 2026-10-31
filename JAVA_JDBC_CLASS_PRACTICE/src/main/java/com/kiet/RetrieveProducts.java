package com.kiet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class RetrieveProducts {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/retail_db";
        String username = "root";
        String password = "admin";

        String sql = "SELECT * FROM Product " +
                "WHERE category = ? AND quantity < 20 " +
                "ORDER BY price DESC";

        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter category: ");
            String category = sc.nextLine();

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    url, username, password);

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, category);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("Product ID: " +
                        rs.getInt("product_id"));
                System.out.println("Name: " +
                        rs.getString("product_name"));
                System.out.println("Category: " +
                        rs.getString("category"));
                System.out.println("Quantity: " +
                        rs.getInt("quantity"));
                System.out.println("Price: " +
                        rs.getDouble("price"));
                System.out.println("--------------------");
            }

            if (!found) {
                System.out.println("No matching products found.");
            }

            rs.close();
            ps.close();
            con.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}