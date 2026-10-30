/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 *
 * @author acer_
 */
//Class yang digunakan nantinya untuk CRUD pada transaksi
public class Transaction {

    private int id;
    private String transactionTime;
    private String itemsDetil;
    private String paymentMethod;
    private double totalPrice;

    public Transaction(int id, String transactionTime, String itemsDetil, String paymentMethod, double totalPrice) {
        this.id = id;
        this.transactionTime = transactionTime;
        this.itemsDetil = itemsDetil;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
    }

    //getter
    public int getId() {
        return id;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public String getItemsDetil() {
        return itemsDetil;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    //setter
    public void setId(int id) {
        this.id = id;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public void setItemsDetil(String itemsDetil) {
        this.itemsDetil = itemsDetil;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    //Logic
    //Membuat
    public void insert() {
        Database db = new Database();
        db.connect();
        String sql = "INSERT INTO transactions (transaction_time, items_detail, payment_method, total_price) VALUES ('"
                + this.transactionTime + "', '"
                + this.itemsDetil + "', '"
                + this.paymentMethod + "', "
                + this.totalPrice + ")";
        db.query(sql);
        db.disconnect();
    }

    //Menampilkan
    public static List<Transaction> getAllTransaction() {
        List<Transaction> list = new ArrayList<>();
        Database db = new Database();
        db.connect();
        ResultSet rs = db.view("SELECT * FROM transactions ORDER BY id DESC");
        try {
            if (rs != null) {
                while (rs.next()) {
                    list.add(new Transaction(rs.getInt("id"), rs.getString("transaction_time"), rs.getString("items_detail"), rs.getString("payment_method"), rs.getDouble("total_price")
                    ));

                }
            }
        } catch (SQLException ex) {
            System.out.println("Ada error ketika menampilkan transaction : " + ex);
        }

        db.disconnect();
        return list;
    }

    //update
    public Transaction() {
    }

    public void update() {
        Database db = new Database();
        db.connect();
        String sql = "UPDATE transactions SET transaction_time = '" + this.transactionTime
                + "', items_detail = '" + this.itemsDetil
                + "', payment_method = '" + this.paymentMethod
                + "', total_price = " + this.totalPrice
                + " WHERE id = " + this.id;
        db.query(sql);
        db.disconnect();
    }

    //hapus (bikin algoritma buat hapus bisa, hapus nomor mantan kok ngga bisa??)
    public static void delete(int id) {
        Database db = new Database();
        db.connect();
        String sql = "DELETE FROM transactions WHERE id = " + id;
        db.query(sql);
        db.disconnect();
    }
}
