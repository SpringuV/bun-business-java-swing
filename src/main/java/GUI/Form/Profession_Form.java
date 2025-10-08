package GUI.Form;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;


public class Profession_Form extends JPanel {
    // Colors
    private static final Color MAROON = new Color(120, 0, 0);
    private static final Color LIGHT_BG = new Color(250, 250, 250);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color ICON_CLR = new Color(120, 0, 0);
    // Header
    private JLabel lbTitle;
    // Cards
    private CardButton btnNhapKho;
    private CardButton btnXuatKho;
    private CardButton btnKiemKe;
    
    private CardLayout cardLayout;
    private JPanel pnMainContent;
    


  
    public Profession_Form() {
        setLayout(new BorderLayout());
        setBackground(LIGHT_BG);
        
        //e đang sửa đoạn này-----------------
//        cardLayout = new CardLayout();
//        pnMainContent = new JPanel(cardLayout);
        //-------------------------------------
        
        // ===== Header bar =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MAROON);
        header.setBorder(new EmptyBorder(10, 16, 10, 16));
        lbTitle = new JLabel("Nghiệp vụ");
        lbTitle.setForeground(Color.WHITE);
        lbTitle.setFont(lbTitle.getFont().deriveFont(Font.BOLD, 16f));
        header.add(lbTitle, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // ===== Cards area =====
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(LIGHT_BG);
        center.setBorder(new EmptyBorder(24, 24, 24, 24));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 12, 0, 12);

        btnNhapKho = new CardButton("Nhập kho", "\u21B0");      // icon placeholder ↰
        btnXuatKho = new CardButton("Xuất kho", "\u21B1");      // icon placeholder ↱
        btnKiemKe  = new CardButton("Kiểm kê", "\u25A6\u25A6"); // icon placeholder ▦▦

        c.gridx = 0; c.gridy = 0; center.add(btnNhapKho, c);
        c.gridx = 1; c.gridy = 0; center.add(btnXuatKho, c);
        c.gridx = 2; c.gridy = 0; center.add(btnKiemKe,  c);

        add(center, BorderLayout.CENTER);
        
        //e đang sửa-------------------------------------
//        btnNhapKho.setActionCommand("switchToWarehouse");
//        Warehouse_Form warehousePanel = new Warehouse_Form();
//        pnMainContent.add(warehousePanel, "WAREHOUSE");
        //--------------------------------------------------
    }
    //em đang sửa chỗ này
//    // Phương thức để quản gia ra lệnh chuyển phòng
//    public void switchToPanel(String panelName) {
//        cardLayout.show(pnMainContent, panelName);
//    }


    // ======== Custom card button ========
    private class CardButton extends JButton {
        CardButton(String text, String iconText) {
            setLayout(new BorderLayout());
            setBackground(CARD_BG);
            setBorder(new LineBorder(new Color(235, 235, 235), 1, true));
            setFocusPainted(false);
            setPreferredSize(new Dimension(170, 120));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel icon = new JLabel(iconText, SwingConstants.CENTER);
            icon.setForeground(ICON_CLR);
            icon.setFont(getFont().deriveFont(Font.PLAIN, 28f));
            icon.setBorder(new EmptyBorder(16, 0, 8, 0));

            JLabel lb = new JLabel(text, SwingConstants.CENTER);
            lb.setForeground(ICON_CLR);
            lb.setFont(getFont().deriveFont(Font.PLAIN, 14f));
            lb.setBorder(new EmptyBorder(0, 0, 16, 0));

            add(icon, BorderLayout.CENTER);
            add(lb, BorderLayout.SOUTH);
        }
    }

    public CardButton getBtnNhapKho() {
        return btnNhapKho;
    }

    public CardButton getBtnXuatKho() {
        return btnXuatKho;
    }

    public CardButton getBtnKiemKe() {
        return btnKiemKe;
    }
    
    
}
