/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

//ini hanya uji coba saja dulu yawww
import model.Product;
import model.CartItem;
import model.Transaction;
import model.IPayment;
import service.CartService;
import service.PaymentProcessor;
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

            input.nextLine();

            System.out.print("\nMasukan kata kunci cari produk : ");
            String kataKunci = input.nextLine();
            for (Product p : Product.searchProducts(kataKunci)) {
                System.out.println(p.getId() + " | " + p.getName() + " | " + p.getPrice());
            }

            String detailBeli = itemMartabakManis.getQuantity() + "x " + itemMartabakManis.getProduct().getName();
            Transaction trxBaru = new Transaction(0, "2026-10-30 23:00", detailBeli, "QRIS", itemMartabakManis.getSubTotal());
            trxBaru.insert();
            System.out.println("\nData transaksi berhasil ditambahkan!");

            List<Transaction> daftarTransaksi = Transaction.getAllTransaction();
            for (Transaction t : daftarTransaksi) {
                System.out.println(t.getId() + " | " + t.getTransactionTime() + " | " + t.getItemsDetil() + " | " + t.getPaymentMethod() + " | " + t.getTotalPrice());
            }

            if (!daftarTransaksi.isEmpty()) {
                Transaction trxEdit = daftarTransaksi.get(0);
                trxEdit.setPaymentMethod("Cash");
                trxEdit.setTotalPrice(99000);
                trxEdit.update();
                System.out.println("\nSetelah di-update (ID " + trxEdit.getId() + "):");

                for (Transaction t : Transaction.getAllTransaction()) {
                    System.out.println(t.getId() + " | " + t.getTransactionTime() + " | " + t.getItemsDetil() + " | " + t.getPaymentMethod() + " | " + t.getTotalPrice());
                }

                System.out.print("\nMasukan ID transaksi yang ingin dihapus : ");
                int idHapus = input.nextInt();
                Transaction.delete(idHapus);

                System.out.println("\nSetelah dihapus:");
                for (Transaction t : Transaction.getAllTransaction()) {
                    System.out.println(t.getId() + " | " + t.getTransactionTime() + " | " + t.getItemsDetil() + " | " + t.getPaymentMethod() + " | " + t.getTotalPrice());
                }
            }

            CartService cartService = new CartService();
            cartService.addProduct(martabakManis);
            cartService.addProduct(martabakManis);
            if (daftarMenuMartabak.size() > 1) {
                cartService.addProduct(daftarMenuMartabak.get(1));
            }

            System.out.println("\nIsi Keranjang setelah addProduct:");
            for (CartItem ci : cartService.getItems()) {
                System.out.println(ci.getProduct().getName() + " | Qty: " + ci.getQuantity() + " | Subtotal: " + ci.getSubTotal());
            }
            System.out.println("Format Detail Keranjang: " + cartService.getItemFormatDetail());
            System.out.println("Total Harga Keranjang: " + cartService.getTotalPrice());

            cartService.removeProduct(martabakManis);
            System.out.println("\nIsi Keranjang setelah removeProduct 1x:");
            System.out.println("Format Detail Keranjang: " + cartService.getItemFormatDetail());
            System.out.println("Total Harga Keranjang: " + cartService.getTotalPrice());

            PaymentProcessor processor = new PaymentProcessor();
            IPayment metodeQris = new IPayment() {
                @Override
                public boolean pay(double totalAmount) {
                    return true;
                }

                @Override
                public String getMethodName() {
                    return "QRIS";
                }
            };

            boolean statusBayar = processor.processTransaction(
                    "2026-10-31 14:45",
                    cartService.getItemFormatDetail(),
                    cartService.getTotalPrice(),
                    metodeQris
            );

            System.out.println("\nStatus Proses Pembayaran via PaymentProcessor: " + statusBayar);
            for (Transaction t : Transaction.getAllTransaction()) {
                System.out.println(t.getId() + " | " + t.getTransactionTime() + " | " + t.getItemsDetil() + " | " + t.getPaymentMethod() + " | " + t.getTotalPrice());
            }

            cartService.clearCart();
            System.out.println("\nJumlah item di keranjang setelah clearCart: " + cartService.getItems().size());
        }
    }

}
