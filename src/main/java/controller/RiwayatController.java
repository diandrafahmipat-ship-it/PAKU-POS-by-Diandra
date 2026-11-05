/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import model.Transaction;
import view.MainFrame;

/**
 *
 * @author acer_
 */
public class RiwayatController {

    private MainFrame mainFrame;
    private boolean deleteMode = false;
    private boolean isAddManualMode = false;
    private Transaction selectedTransaction;

    private List<JTextField> editNameFields = new ArrayList<>();
    private List<JTextField> editQtyFields = new ArrayList<>();
    private List<JTextField> editPriceFields = new ArrayList<>();
    private List<Double> editUnitPrices = new ArrayList<>();

    private JTextField txtManualNama;
    private JTextField txtManualQty;
    private JTextField txtManualHargaSatuan;
    private JTextField txtManualJam;
    private JComboBox<String> cbManualMetode;

    public RiwayatController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setupInitialStyles();
        initListeners();
    }

    private void setupInitialStyles() {
        configureScrollPane(mainFrame.getRiwayatPanel().panelListRiwayat, false);
        configureScrollPane(mainFrame.getDetailRiwayatPanel().panelListDetail, true);
        configureScrollPane(mainFrame.getEditRiwayatPanel().panelListEdit, true);

        for (Component c : mainFrame.getDetailRiwayatPanel().getComponents()) {
            if (c instanceof JLabel && c != mainFrame.getDetailRiwayatPanel().lblDetailHarga
                    && c != mainFrame.getDetailRiwayatPanel().lblDetailWaktuMetode
                    && c != mainFrame.getDetailRiwayatPanel().lblTotalDetail) {
                JLabel lbl = (JLabel) c;
                if (lbl.getBounds().y >= 120 && lbl.getBounds().y <= 145) {
                    lbl.setVisible(false);
                }
                if ("TOTAL HARGA".equals(lbl.getText())) {
                    lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    lbl.setBounds(24, 670, 140, 25);
                }
            }
        }

        for (Component c : mainFrame.getEditRiwayatPanel().getComponents()) {
            if (c instanceof JLabel && "TOTAL HARGA".equals(((JLabel) c).getText())
                    && c != mainFrame.getEditRiwayatPanel().lblTotalEdit) {
                c.setFont(new Font("Segoe UI", Font.BOLD, 15));
                c.setBounds(24, 670, 140, 25);
            }
        }

        mainFrame.getDetailRiwayatPanel().lblDetailHarga.setOpaque(true);
        mainFrame.getDetailRiwayatPanel().lblDetailHarga.setBackground(new Color(25, 80, 200));
        mainFrame.getDetailRiwayatPanel().lblDetailHarga.setForeground(Color.WHITE);
        mainFrame.getDetailRiwayatPanel().lblDetailHarga.setFont(new Font("Segoe UI", Font.BOLD, 22));
        mainFrame.getDetailRiwayatPanel().lblDetailHarga.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
        mainFrame.getDetailRiwayatPanel().lblDetailHarga.setBounds(24, 145, 200, 70);

        mainFrame.getDetailRiwayatPanel().lblDetailWaktuMetode.setOpaque(true);
        mainFrame.getDetailRiwayatPanel().lblDetailWaktuMetode.setBackground(new Color(25, 80, 200));
        mainFrame.getDetailRiwayatPanel().lblDetailWaktuMetode.setForeground(Color.WHITE);
        mainFrame.getDetailRiwayatPanel().lblDetailWaktuMetode.setFont(new Font("Segoe UI", Font.BOLD, 13));
        mainFrame.getDetailRiwayatPanel().lblDetailWaktuMetode.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 16));
        mainFrame.getDetailRiwayatPanel().lblDetailWaktuMetode.setBounds(224, 145, 145, 70);

        mainFrame.getDetailRiwayatPanel().lblTotalDetail.setFont(new Font("Segoe UI", Font.BOLD, 16));
        mainFrame.getDetailRiwayatPanel().lblTotalDetail.setBounds(170, 670, 199, 25);

        mainFrame.getEditRiwayatPanel().lblEditHarga.setOpaque(true);
        mainFrame.getEditRiwayatPanel().lblEditHarga.setBackground(new Color(25, 80, 200));
        mainFrame.getEditRiwayatPanel().lblEditHarga.setForeground(Color.WHITE);
        mainFrame.getEditRiwayatPanel().lblEditHarga.setFont(new Font("Segoe UI", Font.BOLD, 22));
        mainFrame.getEditRiwayatPanel().lblEditHarga.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
        mainFrame.getEditRiwayatPanel().lblEditHarga.setBounds(24, 145, 200, 70);

        mainFrame.getEditRiwayatPanel().lblEditWaktuMetode.setOpaque(true);
        mainFrame.getEditRiwayatPanel().lblEditWaktuMetode.setBackground(new Color(25, 80, 200));
        mainFrame.getEditRiwayatPanel().lblEditWaktuMetode.setForeground(Color.WHITE);
        mainFrame.getEditRiwayatPanel().lblEditWaktuMetode.setFont(new Font("Segoe UI", Font.BOLD, 13));
        mainFrame.getEditRiwayatPanel().lblEditWaktuMetode.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 16));
        mainFrame.getEditRiwayatPanel().lblEditWaktuMetode.setBounds(224, 145, 145, 70);

        mainFrame.getEditRiwayatPanel().lblTotalEdit.setFont(new Font("Segoe UI", Font.BOLD, 16));
        mainFrame.getEditRiwayatPanel().lblTotalEdit.setBounds(170, 670, 199, 25);
    }

    private void configureScrollPane(JPanel innerPanel, boolean whiteCard) {
        if (innerPanel.getParent() != null && innerPanel.getParent().getParent() instanceof JScrollPane) {
            JScrollPane sp = (JScrollPane) innerPanel.getParent().getParent();
            sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            sp.getVerticalScrollBar().setUnitIncrement(14);
            if (whiteCard) {
                sp.setBorder(BorderFactory.createLineBorder(new Color(225, 230, 240), 1));
                sp.getViewport().setBackground(Color.WHITE);
                innerPanel.setBackground(Color.WHITE);
            } else {
                sp.setBorder(null);
                sp.setOpaque(false);
                sp.getViewport().setOpaque(false);
                innerPanel.setOpaque(false);
            }
        }
    }

    private void initListeners() {
        mainFrame.getRiwayatPanel().btnBack.addActionListener(e -> {
            deleteMode = false;
            mainFrame.showPage("DASHBOARD");
        });

        mainFrame.getRiwayatPanel().btnDeleteMode.addActionListener(e -> {
            deleteMode = !deleteMode;
            refreshRiwayatList();
        });

        mainFrame.getRiwayatPanel().btnAdd.addActionListener(e -> {
            isAddManualMode = true;
            renderFormAddManual();
            mainFrame.showPage("EDIT_RIWAYAT");
        });

        mainFrame.getDetailRiwayatPanel().btnBack.addActionListener(e -> {
            refreshRiwayatList();
            mainFrame.showPage("RIWAYAT");
        });

        mainFrame.getDetailRiwayatPanel().btnKembali.addActionListener(e -> {
            refreshRiwayatList();
            mainFrame.showPage("RIWAYAT");
        });

        mainFrame.getDetailRiwayatPanel().btnEdit.addActionListener(e -> {
            if (selectedTransaction != null) {
                isAddManualMode = false;
                renderEditItems(selectedTransaction);
                mainFrame.showPage("EDIT_RIWAYAT");
            }
        });

        mainFrame.getEditRiwayatPanel().btnBack.addActionListener(e -> {
            if (isAddManualMode) {
                refreshRiwayatList();
                mainFrame.showPage("RIWAYAT");
            } else {
                renderDetailItems(selectedTransaction);
                mainFrame.showPage("DETAIL_RIWAYAT");
            }
        });

        mainFrame.getEditRiwayatPanel().btnSimpan.addActionListener(e -> {
            if (isAddManualMode) {
                simpanDataManualBaru();
            } else {
                simpanPerubahanEdit();
            }
        });
    }

    public void refreshRiwayatList() {
        JPanel container = mainFrame.getRiwayatPanel().panelListRiwayat;
        container.removeAll();

        List<Transaction> list = Transaction.getAllTransaction();
        for (Transaction t : list) {
            JPanel rowWrapper = new JPanel(new BorderLayout(8, 0));
            rowWrapper.setOpaque(false);
            rowWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
            Dimension cardDim = new Dimension(340, 72);
            rowWrapper.setMinimumSize(cardDim);
            rowWrapper.setPreferredSize(cardDim);
            rowWrapper.setMaximumSize(new Dimension(345, 72));

            if (deleteMode) {
                JButton btnDelItem = new JButton("🗑");
                btnDelItem.setMargin(new Insets(0, 0, 0, 0));
                btnDelItem.setFont(new Font("Segoe UI", Font.BOLD, 16));
                btnDelItem.setBackground(new Color(220, 50, 50));
                btnDelItem.setForeground(Color.WHITE);
                btnDelItem.setFocusPainted(false);
                btnDelItem.setPreferredSize(new Dimension(50, 72));
                btnDelItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnDelItem.addActionListener(ev -> {
                    Transaction.delete(t.getId());
                    refreshRiwayatList();
                });
                rowWrapper.add(btnDelItem, BorderLayout.WEST);
            }

            JButton cardBtn = new JButton();
            cardBtn.setLayout(new BorderLayout());
            cardBtn.setBackground(new Color(25, 80, 200));
            cardBtn.setFocusPainted(false);
            cardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            cardBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

            JLabel lblPrice = new JLabel(KasirController.formatRupiah(t.getTotalPrice()));
            lblPrice.setForeground(Color.WHITE);
            lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 21));

            JLabel lblRight = new JLabel("<html><right><b>" + t.getPaymentMethod() + "</b><br><font size='2'>" + t.getTransactionTime() + "</font></right></html>", SwingConstants.RIGHT);
            lblRight.setForeground(Color.WHITE);

            cardBtn.add(lblPrice, BorderLayout.WEST);
            cardBtn.add(lblRight, BorderLayout.EAST);
            cardBtn.addActionListener(ev -> {
                selectedTransaction = t;
                renderDetailItems(t);
                mainFrame.showPage("DETAIL_RIWAYAT");
            });

            rowWrapper.add(cardBtn, BorderLayout.CENTER);
            container.add(rowWrapper);

            Component strut = Box.createVerticalStrut(10);
            if (strut instanceof javax.swing.JComponent) {
                ((javax.swing.JComponent) strut).setAlignmentX(Component.LEFT_ALIGNMENT);
            }
            container.add(strut);
        }

        container.revalidate();
        container.repaint();
    }

    private String[] parseItemPart(String part, double defaultTotal) {
        String name = part.trim();
        String qty = "1";
        double subPrice = defaultTotal;

        int xIdx = part.indexOf("x ");
        int rpIdx = part.lastIndexOf("(Rp");

        if (xIdx > 0 && rpIdx > xIdx) {
            qty = part.substring(0, xIdx).trim();
            name = part.substring(xIdx + 2, rpIdx).trim();
            String rawSub = part.substring(rpIdx + 3).replace(")", "").trim();
            try {
                subPrice = Double.parseDouble(rawSub);
            } catch (NumberFormatException ex) {
                subPrice = defaultTotal;
            }
        } else if (xIdx > 0) {
            String candidateQty = part.substring(0, xIdx).trim();
            if (candidateQty.matches("\\d+")) {
                qty = candidateQty;
                name = part.substring(xIdx + 2).trim();
            }
        }

        return new String[]{name, qty, String.valueOf((long) subPrice)};
    }

    private void renderDetailItems(Transaction t) {
        mainFrame.getDetailRiwayatPanel().lblDetailHarga.setText(KasirController.formatRupiah(t.getTotalPrice()));
        mainFrame.getDetailRiwayatPanel().lblDetailWaktuMetode.setText("<html><right><b>" + t.getPaymentMethod() + "</b><br><font size='2'>" + t.getTransactionTime() + "</font></right></html>");
        mainFrame.getDetailRiwayatPanel().lblTotalDetail.setText(KasirController.formatRupiah(t.getTotalPrice()));

        JPanel container = mainFrame.getDetailRiwayatPanel().panelListDetail;
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
        h2.setBounds(175, 6, 40, 24);
        headerRow.add(h2);

        JLabel h3 = new JLabel("Harga", SwingConstants.RIGHT);
        h3.setFont(new Font("Segoe UI", Font.BOLD, 12));
        h3.setBounds(220, 6, 108, 24);
        headerRow.add(h3);

        container.add(headerRow);

        String[] parts = t.getItemsDetil().split(", ");
        for (String part : parts) {
            String[] parsed = parseItemPart(part, t.getTotalPrice());
            String name = parsed[0];
            String qty = parsed[1];
            double subVal = Double.parseDouble(parsed[2]);

            JPanel row = new JPanel(null);
            row.setBackground(Color.WHITE);
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            Dimension rowDim = new Dimension(338, 60);
            row.setMinimumSize(rowDim);
            row.setPreferredSize(rowDim);
            row.setMaximumSize(new Dimension(345, 60));
            row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));

            JLabel lblName = new JLabel("<html><b>" + name + "</b></html>");
            lblName.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblName.setBounds(12, 6, 160, 46);
            row.add(lblName);

            JLabel lblQ = new JLabel(qty, SwingConstants.CENTER);
            lblQ.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblQ.setBounds(175, 16, 40, 26);
            row.add(lblQ);

            JLabel lblP = new JLabel(KasirController.formatRupiah(subVal), SwingConstants.RIGHT);
            lblP.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblP.setBounds(220, 16, 108, 26);
            row.add(lblP);

            container.add(row);
        }

        container.revalidate();
        container.repaint();
    }

    private void renderEditItems(Transaction t) {
        mainFrame.getEditRiwayatPanel().lblEditHarga.setText(KasirController.formatRupiah(t.getTotalPrice()));
        mainFrame.getEditRiwayatPanel().lblEditWaktuMetode.setText("<html><right><b>" + t.getPaymentMethod() + "</b><br><font size='2'>" + t.getTransactionTime() + "</font></right></html>");
        mainFrame.getEditRiwayatPanel().lblTotalEdit.setText(KasirController.formatRupiah(t.getTotalPrice()));

        JPanel container = mainFrame.getEditRiwayatPanel().panelListEdit;
        container.removeAll();
        editNameFields.clear();
        editQtyFields.clear();
        editPriceFields.clear();
        editUnitPrices.clear();

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
        h1.setBounds(12, 6, 145, 24);
        headerRow.add(h1);

        JLabel h2 = new JLabel("Qty", SwingConstants.CENTER);
        h2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        h2.setBounds(168, 6, 48, 24);
        headerRow.add(h2);

        JLabel h3 = new JLabel("Harga (Rp)", SwingConstants.RIGHT);
        h3.setFont(new Font("Segoe UI", Font.BOLD, 12));
        h3.setBounds(222, 6, 106, 24);
        headerRow.add(h3);

        container.add(headerRow);

        String[] parts = t.getItemsDetil().split(", ");
        for (int i = 0; i < parts.length; i++) {
            final int rowIndex = i;
            String[] parsed = parseItemPart(parts[i], t.getTotalPrice());
            String name = parsed[0];
            String qtyStr = parsed[1];
            String rawSub = parsed[2];

            int initQty = Integer.parseInt(qtyStr);
            double initSub = Double.parseDouble(rawSub);
            double unitPrice = initQty > 0 ? (initSub / initQty) : initSub;
            editUnitPrices.add(unitPrice);

            JPanel row = new JPanel(null);
            row.setBackground(Color.WHITE);
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            Dimension rowDim = new Dimension(338, 62);
            row.setMinimumSize(rowDim);
            row.setPreferredSize(rowDim);
            row.setMaximumSize(new Dimension(345, 62));
            row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));

            JTextField txtName = new JTextField(name);
            txtName.setFont(new Font("Segoe UI", Font.BOLD, 12));
            txtName.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(215, 220, 230)),
                    BorderFactory.createEmptyBorder(2, 6, 2, 6)
            ));
            txtName.setBounds(10, 14, 152, 34);
            row.add(txtName);

            JTextField txtQty = new JTextField(qtyStr, SwingConstants.CENTER);
            txtQty.setFont(new Font("Segoe UI", Font.BOLD, 13));
            txtQty.setBackground(new Color(238, 242, 250));
            txtQty.setBorder(BorderFactory.createLineBorder(new Color(190, 205, 235)));
            txtQty.setBounds(168, 14, 48, 34);
            row.add(txtQty);

            JTextField txtPrice = new JTextField(rawSub, SwingConstants.RIGHT);
            txtPrice.setFont(new Font("Segoe UI", Font.BOLD, 13));
            txtPrice.setBackground(new Color(238, 242, 250));
            txtPrice.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(190, 205, 235)),
                    BorderFactory.createEmptyBorder(2, 6, 2, 6)
            ));
            txtPrice.setBounds(222, 14, 106, 34);
            row.add(txtPrice);

            txtQty.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    recalculateRowFromQty(rowIndex);
                }
            });
            txtQty.addActionListener(e -> recalculateRowFromQty(rowIndex));

            txtPrice.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    recalculateEditGrandTotal();
                }
            });
            txtPrice.addActionListener(e -> recalculateEditGrandTotal());

            editNameFields.add(txtName);
            editQtyFields.add(txtQty);
            editPriceFields.add(txtPrice);

            container.add(row);
        }

        container.revalidate();
        container.repaint();
    }

    private void recalculateRowFromQty(int index) {
        String qStr = editQtyFields.get(index).getText().replaceAll("[^0-9]", "");
        int qty = qStr.isEmpty() ? 0 : Integer.parseInt(qStr);
        double unitPrice = editUnitPrices.get(index);
        long newSubtotal = Math.round(qty * unitPrice);
        editPriceFields.get(index).setText(String.valueOf(newSubtotal));
        recalculateEditGrandTotal();
    }

    private void recalculateEditGrandTotal() {
        double total = 0;
        for (JTextField pf : editPriceFields) {
            total += KasirController.parseNominal(pf.getText());
        }
        String formatted = KasirController.formatRupiah(total);
        mainFrame.getEditRiwayatPanel().lblTotalEdit.setText(formatted);
        mainFrame.getEditRiwayatPanel().lblEditHarga.setText(formatted);
    }

    private void renderFormAddManual() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy-HH:mm"));
        mainFrame.getEditRiwayatPanel().lblEditHarga.setText("Tambah Baru");
        mainFrame.getEditRiwayatPanel().lblEditWaktuMetode.setText(now);
        mainFrame.getEditRiwayatPanel().lblTotalEdit.setText("Rp. 0");

        JPanel container = mainFrame.getEditRiwayatPanel().panelListEdit;
        container.removeAll();

        JPanel form = new JPanel(null);
        form.setBackground(Color.WHITE);
        form.setAlignmentX(Component.LEFT_ALIGNMENT);
        Dimension formDim = new Dimension(338, 390);
        form.setMinimumSize(formDim);
        form.setPreferredSize(formDim);
        form.setMaximumSize(new Dimension(345, 390));

        JLabel l1 = new JLabel("Nama Produk:");
        l1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l1.setBounds(18, 12, 305, 22);
        form.add(l1);

        txtManualNama = new JTextField();
        txtManualNama.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtManualNama.setBounds(18, 36, 305, 36);
        form.add(txtManualNama);

        JLabel lQty = new JLabel("Kuantitas (Jumlah):");
        lQty.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lQty.setBounds(18, 82, 135, 22);
        form.add(lQty);

        txtManualQty = new JTextField("1", SwingConstants.CENTER);
        txtManualQty.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtManualQty.setBounds(18, 106, 135, 36);
        form.add(txtManualQty);

        JLabel lHarga = new JLabel("Harga Satuan (Rp):");
        lHarga.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lHarga.setBounds(168, 82, 155, 22);
        form.add(lHarga);

        txtManualHargaSatuan = new JTextField();
        txtManualHargaSatuan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtManualHargaSatuan.setBounds(168, 106, 155, 36);
        form.add(txtManualHargaSatuan);

        JLabel l2 = new JLabel("Jam Terbeli:");
        l2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l2.setBounds(18, 154, 305, 22);
        form.add(l2);

        txtManualJam = new JTextField(now);
        txtManualJam.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtManualJam.setBounds(18, 178, 305, 36);
        form.add(txtManualJam);

        JLabel l3 = new JLabel("Jenis Pembayaran:");
        l3.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l3.setBounds(18, 226, 305, 22);
        form.add(l3);

        cbManualMetode = new JComboBox<>(new String[]{"Cash", "Qris"});
        cbManualMetode.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cbManualMetode.setBounds(18, 250, 305, 38);
        form.add(cbManualMetode);

        KeyAdapter calcManualListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateManualTotalPreview();
            }
        };
        txtManualQty.addKeyListener(calcManualListener);
        txtManualHargaSatuan.addKeyListener(calcManualListener);
        txtManualQty.addActionListener(e -> updateManualTotalPreview());
        txtManualHargaSatuan.addActionListener(e -> updateManualTotalPreview());

        container.add(form);
        container.revalidate();
        container.repaint();
    }

    private void updateManualTotalPreview() {
        String qStr = txtManualQty.getText().replaceAll("[^0-9]", "");
        int qty = qStr.isEmpty() ? 0 : Integer.parseInt(qStr);
        double hargaSatuan = KasirController.parseNominal(txtManualHargaSatuan.getText());
        double total = qty * hargaSatuan;
        mainFrame.getEditRiwayatPanel().lblTotalEdit.setText(KasirController.formatRupiah(total));
    }

    private void simpanDataManualBaru() {
        String nama = txtManualNama.getText().trim();
        String jam = txtManualJam.getText().trim();
        String metode = (String) cbManualMetode.getSelectedItem();
        String qStr = txtManualQty.getText().replaceAll("[^0-9]", "");
        int qty = qStr.isEmpty() ? 1 : Integer.parseInt(qStr);
        double hargaSatuan = KasirController.parseNominal(txtManualHargaSatuan.getText());
        double total = qty * hargaSatuan;

        if (nama.isEmpty() || jam.isEmpty() || total <= 0) {
            JOptionPane.showMessageDialog(mainFrame, "Isi nama produk, kuantitas, jam, dan harga dengan benar!");
            return;
        }

        String detail = qty + "x " + nama + " (Rp" + (long) total + ")";
        Transaction baru = new Transaction(0, jam, detail, metode, total);
        baru.insert();

        refreshRiwayatList();
        mainFrame.showPage("RIWAYAT");
    }

    private void simpanPerubahanEdit() {
        if (selectedTransaction == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        double grandTotal = 0;

        for (int i = 0; i < editNameFields.size(); i++) {
            String n = editNameFields.get(i).getText().trim();
            String qStr = editQtyFields.get(i).getText().replaceAll("[^0-9]", "");
            double sub = KasirController.parseNominal(editPriceFields.get(i).getText());

            int q = qStr.isEmpty() ? 1 : Integer.parseInt(qStr);
            grandTotal += sub;

            sb.append(q).append("x ").append(n).append(" (Rp").append((long) sub).append(")");
            if (i < editNameFields.size() - 1) {
                sb.append(", ");
            }
        }

        selectedTransaction.setItemsDetil(sb.toString());
        selectedTransaction.setTotalPrice(grandTotal);
        selectedTransaction.update();

        renderDetailItems(selectedTransaction);
        mainFrame.showPage("DETAIL_RIWAYAT");
    }
}
