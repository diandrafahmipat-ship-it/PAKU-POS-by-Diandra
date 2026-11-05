/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author acer_
 */
public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;

    private DashboardPanel dashboardPanel;
    private KeranjangPanel keranjangPanel;
    private NotaPanel notaPanel;
    private QrisPanel qrisPanel;
    private CashPanel cashPanel;
    private RiwayatPanel riwayatPanel;
    private DetailRiwayatPanel detailRiwayatPanel;
    private EditRiwayatPanel editRiwayatPanel;

    public MainFrame() {
        setTitle("PAKU POS - Martabak Gendis Sari");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        mainContainer.setPreferredSize(new Dimension(393, 852));

        dashboardPanel = new DashboardPanel();
        keranjangPanel = new KeranjangPanel();
        notaPanel = new NotaPanel();
        qrisPanel = new QrisPanel();
        cashPanel = new CashPanel();
        riwayatPanel = new RiwayatPanel();
        detailRiwayatPanel = new DetailRiwayatPanel();
        editRiwayatPanel = new EditRiwayatPanel();

        mainContainer.add(dashboardPanel, "DASHBOARD");
        mainContainer.add(keranjangPanel, "KERANJANG");
        mainContainer.add(notaPanel, "NOTA");
        mainContainer.add(qrisPanel, "QRIS");
        mainContainer.add(cashPanel, "CASH");
        mainContainer.add(riwayatPanel, "RIWAYAT");
        mainContainer.add(detailRiwayatPanel, "DETAIL_RIWAYAT");
        mainContainer.add(editRiwayatPanel, "EDIT_RIWAYAT");

        add(mainContainer);
        pack();
        setLocationRelativeTo(null);
    }

    public void showPage(String pageName) {
        cardLayout.show(mainContainer, pageName);
    }

    public DashboardPanel getDashboardPanel() {
        return dashboardPanel;
    }

    public KeranjangPanel getKeranjangPanel() {
        return keranjangPanel;
    }

    public NotaPanel getNotaPanel() {
        return notaPanel;
    }

    public QrisPanel getQrisPanel() {
        return qrisPanel;
    }

    public CashPanel getCashPanel() {
        return cashPanel;
    }

    public RiwayatPanel getRiwayatPanel() {
        return riwayatPanel;
    }

    public DetailRiwayatPanel getDetailRiwayatPanel() {
        return detailRiwayatPanel;
    }

    public EditRiwayatPanel getEditRiwayatPanel() {
        return editRiwayatPanel;
    }
}
