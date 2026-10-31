package com.kiet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ModifyProductPrice {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/retail_db";
        String username = "root";
        String password = "admin";

        String selectSql =
                "SELECT * FROM Product WHERE product_id = ?";

        String updateSql =
                "UPDATE Product SET price = ? WHERE product_id = ?";

        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter product ID: ");
            int productId = sc.nextInt();

            System.out.print("Enter new price: ");
            double newPrice = sc.nextDouble();

            if (newPrice <= 0) {
                System.out.println(
                        "Price must be greater than zero.");
                sc.close();
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    url, username, password);

            PreparedStatement ps = con.prepareStatement(selectSql);
            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                rs.close();
                ps.close();

                PreparedStatement ps2 =
                        con.prepareStatement(updateSql);

                ps2.setDouble(1, newPrice);
                ps2.setInt(2, productId);
                ps2.executeUpdate();
                ps2.close();

                PreparedStatement ps3 =
                        con.prepareStatement(selectSql);

                ps3.setInt(1, productId);

                ResultSet rs2 = ps3.executeQuery();

                if (rs2.next()) {
                    System.out.println("Price updated successfully.");
                    System.out.println("Product ID: " +
                            rs2.getInt("product_id"));
                    System.out.println("Name: " +
                            rs2.getString("product_name"));
                    System.out.println("Category: " +
                            rs2.getString("category"));
                    System.out.println("Quantity: " +
                            rs2.getInt("quantity"));
                    System.out.println("Updated price: " +
                            rs2.getDouble("price"));
                }

                rs2.close();
                ps3.close();

            } else {
                System.out.println("Product does not exist.");
                rs.close();
                ps.close();
            }

            con.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}