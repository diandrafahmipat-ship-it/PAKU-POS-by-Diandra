/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer_
 */
public class Product {

    int id;
    String name;
    double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public static List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        Database db = new Database();
        db.connect();

        ResultSet rs = db.view("SELECT * FROM products");
        try {
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Ada error nih mas saat mau nampilin data : " + ex.getMessage());
        }

        db.disconnect();
        return list;
    }

    public static List<Product> searchProducts(String kataKunci) {
        List<Product> list = new ArrayList<>();
        Database db = new Database();
        db.connect();
        ResultSet rs = db.view("SELECT * FROM products WHERE name LIKE '%" + kataKunci + "%'");
        try {
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Ada error iki mas ketika cari produk :" + ex.getMessage());
        }
        db.disconnect();
        return list;
    }
}
