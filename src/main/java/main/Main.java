/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import controller.KasirController;
import controller.RiwayatController;
import javax.swing.SwingUtilities;
import service.CartService;
import service.PaymentProcessor;
import view.MainFrame;

/**
 *
 * @author acer_
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            CartService cartService = new CartService();
            PaymentProcessor paymentProcessor = new PaymentProcessor();

            RiwayatController riwayatController = new RiwayatController(mainFrame);
            new KasirController(mainFrame, cartService, paymentProcessor, riwayatController);

            mainFrame.showPage("DASHBOARD");
            mainFrame.setVisible(true);
        });
    }
}
