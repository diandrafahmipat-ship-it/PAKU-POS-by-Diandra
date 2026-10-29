/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

//ini hanya uji coba saja dulu yawww
import model.Product;

/**
 *
 * @author acer_
 */
public class Main {

    public static void main(String[] args) {
        for (Product p : Product.getAllProducts()) {
            System.out.println(p.getId() + "| Nama Produk: " + p.getName() + " | hargane: " + p.getPrice());
        }
    }
}
