package com.kiet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UpdateStock {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/retail_db";
        String username = "root";
        String password = "admin";

        String updateSql = "UPDATE Product " +
                "SET quantity = quantity + ? WHERE product_id = ?";

        String selectSql = "SELECT product_name, quantity " +
                "FROM Product WHERE product_id = ?";

        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter product ID: ");
            int productId = sc.nextInt();

            System.out.print("Enter quantity to add: ");
            int quantityToAdd = sc.nextInt();

            if (quantityToAdd <= 0) {
                System.out.println(
                        "Quantity to add must be greater than zero.");
                sc.close();
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    url, username, password);

            PreparedStatement ps = con.prepareStatement(updateSql);

            ps.setInt(1, quantityToAdd);
            ps.setInt(2, productId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                PreparedStatement ps2 =
                        con.prepareStatement(selectSql);

                ps2.setInt(1, productId);

                ResultSet rs = ps2.executeQuery();

                if (rs.next()) {
                    System.out.println("Stock updated successfully.");
                    System.out.println("Product: " +
                            rs.getString("product_name"));
                    System.out.println("New quantity: " +
                            rs.getInt("quantity"));
                }

                rs.close();
                ps2.close();

            } else {
                System.out.println(
                        "Product does not exist.");
            }

            ps.close();
            con.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}