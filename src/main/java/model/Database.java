/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

//izin pake all aja karena males ngurus error nya wkwkwkwk
import java.sql.*;

/**
 *
 * @author acer_
 */
public class Database {

    static final String URL = "jdbc:mysql://localhost:3306/db_gendissari";
    static final String USER = "root";
    static final String PASS = "";

    public Connection conn;
    public Statement stmt;
    public ResultSet rs;

    //Menyambungkan
    public void connect() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("Gagal terhubung mas, kie erro e : " + e.getMessage());
        }
    }

    //Untuk insert, Update, dan Delate
    public void query(String sql) {
        try {
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println("Kesalahan pada query : " + ex.getMessage());
        }
    }

    //Untuk melihat/membaca
    public ResultSet view(String sql) {
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Kesalahan nang view iki mas : " + ex.getMessage());
        }

        return rs;
    }

    //Untuk close database saat ditutup
    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Kesalahan pada menutup koneksine kie mas, : " + ex.getMessage());
        }
    }
}
