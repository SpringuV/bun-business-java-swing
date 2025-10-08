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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Admin
 */
public class GuestTable_Form extends JPanel {
    private List<RoundedPanel> tableList = new ArrayList<>();// lưu toàn bộ các bàn tạo ở vòng for

    
    public GuestTable_Form() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 245, 245));

        JLabel lbTitle = new JLabel("Sơ đồ bàn quán bún bò Huế O Hà");
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(120, 0, 0) );
        header.setBorder(new EmptyBorder(10, 16, 10, 16));
        lbTitle.setForeground(Color.WHITE);
        lbTitle.setFont(lbTitle.getFont().deriveFont(Font.BOLD, 16f));
        header.add(lbTitle, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);
        
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

       
        this.add(tableGrid, BorderLayout.CENTER);
        
        
        
        GuestTable_Controller controller = new GuestTable_Controller(this);
    }
    public List<RoundedPanel> getTableList() {
        return tableList;
    }

}

