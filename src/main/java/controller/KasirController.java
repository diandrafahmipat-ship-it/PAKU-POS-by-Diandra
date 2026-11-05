/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import model.CartItem;
import model.CashPayment;
import model.Product;
import model.QrisPayment;
import service.CartService;
import service.PaymentProcessor;
import view.MainFrame;

/**
 *
 * @author acer_
 */
public class KasirController {

    private MainFrame mainFrame;
    private CartService cartService;
    private PaymentProcessor paymentProcessor;
    private RiwayatController riwayatController;
    private List<Product> currentProducts;

    public KasirController(MainFrame mainFrame, CartService cartService, PaymentProcessor paymentProcessor, RiwayatController riwayatController) {
        this.mainFrame = mainFrame;
        this.cartService = cartService;
        this.paymentProcessor = paymentProcessor;
        this.riwayatController = riwayatController;

        setupInitialStyles();
        initListeners();
        loadProducts("");
    }

    private void setupInitialStyles() {
        mainFrame.getKeranjangPanel().txtSearch.setBounds(50, 130, 255, 42);
        mainFrame.getKeranjangPanel().txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainFrame.getKeranjangPanel().txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225), 1),
                BorderFactory.createEmptyBorder(5, 12, 5, 10)
        ));

        mainFrame.getKeranjangPanel().btnSearch.setBounds(305, 130, 42, 42);
        mainFrame.getKeranjangPanel().btnSearch.setMargin(new Insets(0, 0, 0, 0));
        mainFrame.getKeranjangPanel().btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mainFrame.getKeranjangPanel().btnSearch.setBackground(Color.WHITE);
        mainFrame.getKeranjangPanel().btnSearch.setOpaque(true);
        mainFrame.getKeranjangPanel().btnSearch.setBorder(BorderFactory.createLineBorder(new Color(210, 215, 225), 1));
        mainFrame.getKeranjangPanel().btnSearch.setBorderPainted(true);
        mainFrame.getKeranjangPanel().btnSearch.setContentAreaFilled(true);

        configureScrollPane(mainFrame.getKeranjangPanel().panelListProduk);
        configureScrollPane(mainFrame.getNotaPanel().panelListNota);

        mainFrame.getNotaPanel().panelListNota.setLayout(new BoxLayout(mainFrame.getNotaPanel().panelListNota, BoxLayout.Y_AXIS));

        mainFrame.getQrisPanel().lblTotalQris.setBounds(80, 152, 240, 62);
        mainFrame.getQrisPanel().lblTotalQris.setFont(new Font("Segoe UI", Font.BOLD, 24));
        mainFrame.getQrisPanel().lblTotalQris.setForeground(new Color(20, 30, 70));

        mainFrame.getCashPanel().lblTotalCash.setBounds(24, 145, 345, 70);
        mainFrame.getCashPanel().lblTotalCash.setOpaque(true);
        mainFrame.getCashPanel().lblTotalCash.setBackground(new Color(25, 85, 210));
        mainFrame.getCashPanel().lblTotalCash.setFont(new Font("Segoe UI", Font.BOLD, 26));
        mainFrame.getCashPanel().lblTotalCash.setForeground(Color.WHITE);
        mainFrame.getCashPanel().lblTotalCash.setHorizontalAlignment(SwingConstants.CENTER);

        mainFrame.getCashPanel().lblUangKembalian.setEditable(false);
        mainFrame.getCashPanel().lblStatusCash.setEditable(false);
    }

    private void configureScrollPane(JPanel innerPanel) {
        if (innerPanel.getParent() != null && innerPanel.getParent().getParent() instanceof JScrollPane) {
            JScrollPane sp = (JScrollPane) innerPanel.getParent().getParent();
            sp.setBorder(BorderFactory.createLineBorder(new Color(225, 230, 240), 1));
            sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            sp.getVerticalScrollBar().setUnitIncrement(14);
            sp.getViewport().setBackground(Color.WHITE);
        }
    }

    private void initListeners() {
        mainFrame.getDashboardPanel().btnCatatPenjualan.addActionListener(e -> {
            loadProducts("");
            mainFrame.showPage("KERANJANG");
        });

        mainFrame.getDashboardPanel().btnRiwayatPenjualan.addActionListener(e -> {
            riwayatController.refreshRiwayatList();
            mainFrame.showPage("RIWAYAT");
        });

        mainFrame.getKeranjangPanel().btnBack.addActionListener(e -> {
            mainFrame.showPage("DASHBOARD");
        });

        mainFrame.getKeranjangPanel().btnSearch.addActionListener(e -> {
            loadProducts(mainFrame.getKeranjangPanel().txtSearch.getText().trim());
        });

        mainFrame.getKeranjangPanel().txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                loadProducts(mainFrame.getKeranjangPanel().txtSearch.getText().trim());
            }
        });

        mainFrame.getKeranjangPanel().btnLanjut.addActionListener(e -> {
            if (cartService.getItems().isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Pilih minimal 1 produk terlebih dahulu!");
                return;
            }
            renderNotaItems();
            mainFrame.showPage("NOTA");
        });

        mainFrame.getNotaPanel().btnBack.addActionListener(e -> {
            mainFrame.showPage("KERANJANG");
        });

        mainFrame.getNotaPanel().btnPilihQris.addActionListener(e -> {
            mainFrame.getQrisPanel().lblTotalQris.setText(formatRupiah(cartService.getTotalPrice()));
            mainFrame.showPage("QRIS");
        });

        mainFrame.getNotaPanel().btnPilihCash.addActionListener(e -> {
            mainFrame.getCashPanel().lblTotalCash.setText(formatRupiah(cartService.getTotalPrice()));
            mainFrame.getCashPanel().txtMasukanUang.setText("");
            mainFrame.getCashPanel().lblUangKembalian.setText("Rp. 0");
            mainFrame.getCashPanel().lblStatusCash.setText("Status: Masukan Nominal");
            mainFrame.getCashPanel().lblStatusCash.setBackground(new Color(220, 220, 220));
            mainFrame.getCashPanel().lblStatusCash.setForeground(new Color(40, 40, 40));
            mainFrame.showPage("CASH");
        });

        mainFrame.getQrisPanel().btnBack.addActionListener(e -> {
            mainFrame.showPage("NOTA");
        });

        mainFrame.getCashPanel().btnBack.addActionListener(e -> {
            mainFrame.showPage("NOTA");
        });

        mainFrame.getCashPanel().txtMasukanUang.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateKalkulasiCash();
            }
        });

        mainFrame.getQrisPanel().btnSelesaiQris.addActionListener(e -> {
            String waktu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy-HH:mm"));
            QrisPayment qris = new QrisPayment();
            boolean sukses = paymentProcessor.processTransaction(
                    waktu,
                    cartService.getItemFormatDetail(),
                    cartService.getTotalPrice(),
                    qris
            );
            if (sukses) {
                cartService.clearCart();
                mainFrame.getKeranjangPanel().txtSearch.setText("");
                mainFrame.showPage("DASHBOARD");
            }
        });

        mainFrame.getCashPanel().btnSelesaiCash.addActionListener(e -> {
            double uangMasuk = parseNominal(mainFrame.getCashPanel().txtMasukanUang.getText());
            double totalHarga = cartService.getTotalPrice();
            CashPayment cash = new CashPayment(uangMasuk);

            String waktu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy-HH:mm"));
            boolean sukses = paymentProcessor.processTransaction(
                    waktu,
                    cartService.getItemFormatDetail(),
                    totalHarga,
                    cash
            );

            if (sukses) {
                cartService.clearCart();
                mainFrame.getKeranjangPanel().txtSearch.setText("");
                mainFrame.showPage("DASHBOARD");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Uang kurang, tidak cukup untuk membayar!");
            }
        });
    }

    public void loadProducts(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            currentProducts = Product.getAllProducts();
        } else {
            currentProducts = Product.searchProducts(keyword);
        }
        renderProductList();
    }

    private void renderProductList() {
        JPanel container = mainFrame.getKeranjangPanel().panelListProduk;
        container.removeAll();

        for (Product p : currentProducts) {
            int qty = 0;
            for (CartItem ci : cartService.getItems()) {
                if (ci.getProduct().getId() == p.getId()) {
                    qty = ci.getQuantity();
                    break;
                }
            }

            JPanel row = new JPanel(null);
            row.setBackground(Color.WHITE);
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            Dimension rowDim = new Dimension(338, 72);
            row.setMinimumSize(rowDim);
            row.setPreferredSize(rowDim);
            row.setMaximumSize(new Dimension(345, 72));
            row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(225, 228, 235)));

            JLabel lblName = new JLabel("<html><b>" + p.getName() + "</b></html>");
            lblName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lblName.setBounds(12, 8, 128, 54);
            row.add(lblName);

            JLabel lblPrice = new JLabel(formatRupiah(p.getPrice()));
            lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblPrice.setBounds(142, 22, 85, 26);
            row.add(lblPrice);

            JButton btnPlus = new JButton("+");
            btnPlus.setMargin(new Insets(0, 0, 0, 0));
            btnPlus.setFont(new Font("Segoe UI", Font.BOLD, 15));
            btnPlus.setBackground(new Color(235, 240, 252));
            btnPlus.setForeground(new Color(20, 70, 190));
            btnPlus.setFocusPainted(false);
            btnPlus.setBorder(BorderFactory.createLineBorder(new Color(190, 205, 240), 1));
            btnPlus.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnPlus.setBounds(230, 20, 32, 32);
            btnPlus.addActionListener(e -> {
                cartService.addProduct(p);
                renderProductList();
            });
            row.add(btnPlus);

            JLabel lblQty = new JLabel(String.valueOf(qty), SwingConstants.CENTER);
            lblQty.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblQty.setBounds(264, 20, 30, 32);
            row.add(lblQty);

            JButton btnMinus = new JButton("-");
            btnMinus.setMargin(new Insets(0, 0, 0, 0));
            btnMinus.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btnMinus.setBackground(new Color(245, 245, 248));
            btnMinus.setForeground(new Color(60, 60, 60));
            btnMinus.setFocusPainted(false);
            btnMinus.setBorder(BorderFactory.createLineBorder(new Color(205, 205, 215), 1));
            btnMinus.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnMinus.setBounds(296, 20, 32, 32);
            btnMinus.addActionListener(e -> {
                cartService.removeProduct(p);
                renderProductList();
            });
            row.add(btnMinus);

            container.add(row);
        }

        container.revalidate();
        container.repaint();
    }

    private void renderNotaItems() {
        JPanel container = mainFrame.getNotaPanel().panelListNota;
        container.removeAll();

        JPanel headerRow = new JPanel(null);
        headerRow.setBackground(new Color(244, 246, 250));
        headerRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        Dimension hDim = new Dimension(338, 36);
        headerRow.setMinimumSize(hDim);
        headerRow.setPreferredSize(hDim);
        headerRow.setMaximumSize(new Dimension(345, 36));
        headerRow.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(215, 220, 230)));

        JLabel h1 = new JLabel("Nama Produk");
        h1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        h1.setBounds(12, 6, 150, 24);
        headerRow.add(h1);

        JLabel h2 = new JLabel("Qty", SwingConstants.CENTER);
        h2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        h2.setBounds(180, 6, 40, 24);
        headerRow.add(h2);

        JLabel h3 = new JLabel("Subtotal", SwingConstants.RIGHT);
        h3.setFont(new Font("Segoe UI", Font.BOLD, 12));
        h3.setBounds(225, 6, 102, 24);
        headerRow.add(h3);

        container.add(headerRow);

        for (CartItem item : cartService.getItems()) {
            JPanel row = new JPanel(null);
            row.setBackground(Color.WHITE);
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            Dimension rowDim = new Dimension(338, 62);
            row.setMinimumSize(rowDim);
            row.setPreferredSize(rowDim);
            row.setMaximumSize(new Dimension(345, 62));
            row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));

            JLabel lblName = new JLabel("<html><b>" + item.getProduct().getName() + "</b></html>");
            lblName.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblName.setBounds(12, 8, 165, 45);
            row.add(lblName);

            JLabel lblQty = new JLabel(String.valueOf(item.getQuantity()), SwingConstants.CENTER);
            lblQty.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblQty.setBounds(180, 18, 40, 25);
            row.add(lblQty);

            JLabel lblSub = new JLabel(formatRupiah(item.getSubTotal()), SwingConstants.RIGHT);
            lblSub.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblSub.setBounds(225, 18, 102, 25);
            row.add(lblSub);

            container.add(row);
        }

        mainFrame.getNotaPanel().lblTotalHarga.setText(formatRupiah(cartService.getTotalPrice()));
        container.revalidate();
        container.repaint();
    }

    private void updateKalkulasiCash() {
        double uangMasuk = parseNominal(mainFrame.getCashPanel().txtMasukanUang.getText());
        double total = cartService.getTotalPrice();
        CashPayment cash = new CashPayment(uangMasuk);

        if (uangMasuk >= total && total > 0) {
            double kembalian = cash.getChange(total, uangMasuk);
            mainFrame.getCashPanel().lblUangKembalian.setText(formatRupiah(kembalian));
            mainFrame.getCashPanel().lblStatusCash.setText("Status: Uang Cukup");
            mainFrame.getCashPanel().lblStatusCash.setBackground(new Color(140, 240, 140));
            mainFrame.getCashPanel().lblStatusCash.setForeground(new Color(10, 90, 20));
        } else {
            mainFrame.getCashPanel().lblUangKembalian.setText("Rp. 0");
            mainFrame.getCashPanel().lblStatusCash.setText("Status: Uang Kurang");
            mainFrame.getCashPanel().lblStatusCash.setBackground(new Color(255, 170, 170));
            mainFrame.getCashPanel().lblStatusCash.setForeground(new Color(140, 20, 20));
        }
    }

    public static double parseNominal(String raw) {
        String clean = raw.replaceAll("[^0-9]", "");
        if (clean.isEmpty()) {
            return 0;
        }
        return Double.parseDouble(clean);
    }

    public static String formatRupiah(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,###", symbols);
        return "Rp. " + df.format(value);
    }
}
