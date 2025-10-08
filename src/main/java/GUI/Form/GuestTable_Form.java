/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Form;

import GUI.Component.RoundedPanel;
import controller.GuestTable_Controller;
//import controller.GuestTable_Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Admin
 */
public class GuestTable_Form extends JFrame {
    private List<RoundedPanel> tableList = new ArrayList<>();// lưu toàn bộ các bàn tạo ở vòng for
    private JMenuBar menuBarr;
    private JMenu menu;
    private JMenuItem menuQLSanPham, menuQLNhapXuatKho, menuThongKeBaoCao, menuQuanLyKho, menuDatBan;
    private JPanel mainPannel;
    
    public GuestTable_Form() {
        setTitle("Đặt bàn");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        mainPannel = new JPanel();
        mainPannel.setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        //e để tạm cái menu như này 
        menuBarr = new JMenuBar();
        menu = new JMenu("Giao diện");
        menuDatBan = new JMenuItem("Đặt bàn");
        menuQLSanPham = new JMenuItem("Quản lý sản phẩm");
        menuQLNhapXuatKho = new JMenuItem("Quản lý Nhập/xuất");
        menuQuanLyKho = new  JMenuItem("Quản lý kho");
        menuThongKeBaoCao = new JMenuItem("Thông kê và báo cáo");
        menu.add(menuDatBan);
        menu.add(menuQLSanPham);
        menu.add(menuQLNhapXuatKho);
        menu.add(menuQuanLyKho);
        menu.add(menuThongKeBaoCao);
        menuBarr.add(menu);
        setJMenuBar(menuBarr);
        

        JLabel title = new JLabel("Sơ đồ bàn quán bún bò Huế O Hà", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(109, 7, 7));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPannel.add(title, BorderLayout.NORTH);

        // Khu vực chứa bàn
        JPanel tableGrid = new JPanel(new GridLayout(3, 4, 20, 20));
        tableGrid.setBackground(new Color(245, 245, 245));
        tableGrid.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        // Thêm bàn 
        for (int i = 1; i <= 20; i++) {
            boolean available = (i % 3 != 0);
            Color color = available ? new Color(76, 175, 80) : new Color(229, 57, 53);
//-------------------------------tạo giao diện cho cái thẻ bàn-----------------------------------------
            RoundedPanel panel = new RoundedPanel(new BorderLayout(), 25, color);//RoundedPanel tương tự như JPanel, dùng để bo góc cho các thẻ bàn
            panel.setPreferredSize(new Dimension(150, 120));
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel lblIcon = new JLabel("🍜", SwingConstants.CENTER);
            lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
            panel.add(lblIcon, BorderLayout.CENTER);

            JLabel lblName = new JLabel("Bàn " + i, SwingConstants.CENTER);
            lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblName.setForeground(Color.WHITE);
            panel.add(lblName, BorderLayout.NORTH);

            JLabel lblStatus = new JLabel(available ? "Trống" : "Đang phục vụ", SwingConstants.CENTER);
            lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lblStatus.setForeground(Color.WHITE);
            panel.add(lblStatus, BorderLayout.SOUTH);

            panel.putClientProperty("baseColor", color);
            
            tableList.add(panel);
            tableGrid.add(panel);
        }

       
        mainPannel.add(tableGrid, BorderLayout.CENTER);
        this.add(mainPannel);
        GuestTable_Controller controller = new GuestTable_Controller(this);
    }

    public List<RoundedPanel> getTableList() {
        return tableList;
    }

    public JMenuBar getMenuBarr() {
        return menuBarr;
    }

    public JMenu getMenu() {
        return menu;
    }

    public JMenuItem getMenuQLSanPham() {
        return menuQLSanPham;
    }

    public JMenuItem getMenuQLNhapXuatKho() {
        return menuQLNhapXuatKho;
    }

    public JMenuItem getMenuThongKeBaoCao() {
        return menuThongKeBaoCao;
    }

    public JMenuItem getMenuQuanLyKho() {
        return menuQuanLyKho;
    }

    public JMenuItem getMenuDatBan() {
        return menuDatBan;
    }

    public JPanel getMainPannel() {
        return mainPannel;
    }

    public static void main(String[] args) {
        new GuestTable_Form().setVisible(true);
    }
}

