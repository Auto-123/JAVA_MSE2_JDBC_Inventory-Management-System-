package com.kiet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AggregateAndDelete {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/retail_db";
        String username = "root";
        String password = "admin";

        String totalSql = "SELECT SUM(quantity * price) " +
                "AS total_value FROM Product";

        String deleteSql = "DELETE FROM Product " +
                "WHERE product_id = ? AND quantity = 0";

        String selectSql = "SELECT quantity FROM Product " +
                "WHERE product_id = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    url, username, password);

            PreparedStatement ps = con.prepareStatement(totalSql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.printf(
                        "Total inventory value: %.2f%n",
                        rs.getDouble("total_value"));
            }

            rs.close();
            ps.close();

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter product ID to delete: ");
            int productId = sc.nextInt();

            PreparedStatement ps2 =
                    con.prepareStatement(deleteSql);

            ps2.setInt(1, productId);

            int rows = ps2.executeUpdate();

            if (rows > 0) {
                System.out.println("Product deleted successfully.");

            } else {

                PreparedStatement ps3 =
                        con.prepareStatement(selectSql);

                ps3.setInt(1, productId);

                ResultSet rs2 = ps3.executeQuery();

                if (rs2.next()) {
                    System.out.println(
                            "Cannot delete product: quantity is not zero.");
                } else {
                    System.out.println(
                            "Product does not exist.");
                }

                rs2.close();
                ps3.close();
            }

            ps2.close();
            con.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}