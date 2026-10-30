/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

//ini hanya uji coba saja dulu yawww
import model.Product;
import model.CartItem;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author acer_
 */
public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        //tampilkan semua produk
        for (Product p : Product.getAllProducts()) {
            System.out.println(p.getId() + "| Nama Produk: " + p.getName() + " | hargane: " + p.getPrice());
        }

        //Memilih satu produk
        List<Product> daftarMenuMartabak = Product.getAllProducts();

        if (!daftarMenuMartabak.isEmpty()) {
            Product martabakManis = daftarMenuMartabak.get(0);

            System.out.println("Nama Produk : " + martabakManis.getName() + " , Harga : " + martabakManis.getPrice());
            System.out.print("\n masukan jumlah yang ingin dibeli : ");
            int quantitySubItem = input.nextInt();

            CartItem itemMartabakManis = new CartItem(martabakManis, quantitySubItem);

            System.out.println("\n total harga untuk item " + itemMartabakManis.getProduct().getName() + " adalah " + itemMartabakManis.getSubTotal() + " | " + itemMartabakManis.getQuantity());

        }
    }

}
