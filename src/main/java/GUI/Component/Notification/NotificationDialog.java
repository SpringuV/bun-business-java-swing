/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component.Notification;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Admin
 */
public class NotificationDialog extends JDialog {
    // Biến để lưu kết quả lựa chọn của người dùng
    private int result = JOptionPane.CLOSED_OPTION;

    private NotificationDialog(Frame owner, String title, String message, NotificationType type, OptionType optionType) {
        super(owner, title, true);

        // --- Giao diện (Tương tự như trước) ---
        JPanel mainPanel = new JPanel(new BorderLayout(15, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 15, 20));
        mainPanel.setBackground(type.getBackgroundColor());
        
        JLabel lbTitle = new JLabel(title);
        lbTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lbTitle.setForeground(type.getForegroundColor());
        
        FontMetrics metrics = lbTitle.getFontMetrics(lbTitle.getFont());
        int titleHeight = metrics.getHeight(); // Đây chính là chiều cao của lbTitle


        JLabel lbIcon = new JLabel();
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(type.getIconPath()));
            // Thay thế "48, 48" bằng "titleHeight, titleHeight"
            Image scaledImage = originalIcon.getImage().getScaledInstance(titleHeight, titleHeight, Image.SCALE_SMOOTH);
            lbIcon.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("Không tìm thấy icon: " + type.getIconPath());
        }
        
        // TẠO MỘT PANEL BỌC BÊN NGOÀI ICON ĐỂ CĂN CHỈNH
        JPanel iconContainer = new JPanel(new BorderLayout());
        iconContainer.setOpaque(false); // Làm cho panel này trong suốt để giữ màu nền
        iconContainer.add(lbIcon, BorderLayout.NORTH); // Đẩy icon lên phía trên cùng của panel này

        JPanel contentPanel = new JPanel(new BorderLayout(0, 10));
        contentPanel.setOpaque(false);

        

        JTextArea txtMessage = new JTextArea(message);
        txtMessage.setEditable(false);
        txtMessage.setLineWrap(true);
        txtMessage.setWrapStyleWord(true);
        txtMessage.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtMessage.setOpaque(false);
        txtMessage.setFocusable(false); 
        txtMessage.setForeground(type.getForegroundColor());

        // --- PHẦN NÚT BẤM ĐƯỢC NÂNG CẤP ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        // Dựa vào optionType để tạo các nút tương ứng
        if (optionType == OptionType.OK) {
            ModernButton btnOk = new ModernButton("OK");
            btnOk.addActionListener(e -> {
                result = JOptionPane.OK_OPTION; // Đặt kết quả
                dispose(); // Đóng dialog
            });
            buttonPanel.add(btnOk);
        } else if (optionType == OptionType.YES_NO) {
            ModernButton btnYes = new ModernButton("Yes");
            btnYes.setButtonStyle(ModernButton.ButtonStyle.PRIMARY);
            btnYes.addActionListener(e -> {
                result = JOptionPane.YES_OPTION; // Đặt kết quả
                dispose();
            });
            buttonPanel.add(btnYes);

            ModernButton btnNo = new ModernButton("No");
            btnNo.setButtonStyle(ModernButton.ButtonStyle.SECONDARY);
            btnNo.addActionListener(e -> {
                result = JOptionPane.NO_OPTION; // Đặt kết quả
                dispose();
            });
            buttonPanel.add(btnNo);
        }

        // --- Ghép các thành phần ---
        contentPanel.add(lbTitle, BorderLayout.NORTH);
        contentPanel.add(txtMessage, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(iconContainer, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        pack();
        setMinimumSize(new Dimension(400, 170));
        setLocationRelativeTo(owner);
    }

    // Getter để lấy kết quả
    public int getResult() {
        return result;
    }

    // --- CÁC PHƯƠNG THỨC TĨNH TIỆN LỢI ---
    //Hiển thị một thông báo thông tin (chỉ có nút OK).
    public static void showMessage(Frame owner, String title, String message, NotificationType type) {
        new NotificationDialog(owner, title, message, type, OptionType.OK).setVisible(true);
    }


    //Hiển thị một hộp thoại xác nhận (có nút Yes/No) và trả về lựa chọn của người dùng.
    public static int showConfirm(Frame owner, String title, String message, NotificationType type) {
        NotificationDialog dialog = new NotificationDialog(owner, title, message, type, OptionType.YES_NO);
        dialog.setVisible(true); // Lệnh này sẽ dừng cho đến khi dialog được đóng
        return dialog.getResult(); // Trả về kết quả sau khi dialog đã đóng
    }

}
