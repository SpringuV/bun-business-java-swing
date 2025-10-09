/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component.Notification;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Admin
 */
public class ModernButton extends JButton{
    public enum ButtonStyle {
        PRIMARY,    // Nút cho hành động chính
        SECONDARY   // Nút cho hành động phụ
    }

    private ButtonStyle style = ButtonStyle.PRIMARY;
    private int cornerRadius = 12; // Độ bo góc

    // Màu sắc cho từng trạng thái
    private Color primaryNormalColor = new Color(25, 118, 210);
    private Color primaryHoverColor = new Color(21, 101, 192);
    private Color secondaryNormalColor = new Color(224, 224, 224);
    private Color secondaryHoverColor = new Color(206, 206, 206);
    private Color secondaryText = Color.DARK_GRAY;

    private Color currentColor;

    public ModernButton(String text) {
        super(text);
        setContentAreaFilled(false); // Quan trọng: Tắt chế độ vẽ mặc định của nút
        setFocusPainted(false);
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(new Font("SansSerif", Font.BOLD, 13));

        currentColor = primaryNormalColor; // Mặc định là nút primary

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (style == ButtonStyle.PRIMARY) {
                    currentColor = primaryHoverColor;
                } else {
                    currentColor = secondaryHoverColor;
                }
                repaint(); // Vẽ lại nút với màu mới
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (style == ButtonStyle.PRIMARY) {
                    currentColor = primaryNormalColor;
                } else {
                    currentColor = secondaryNormalColor;
                }
                repaint();
            }
        });
    }
    // Phương thức để thay đổi phong cách (primary/secondary)
    public void setButtonStyle(ButtonStyle style) {
        this.style = style;
        if (style == ButtonStyle.PRIMARY) {
            currentColor = primaryNormalColor;
            setForeground(Color.WHITE);
        } else {
            currentColor = secondaryNormalColor;
            setForeground(secondaryText);
        }
        repaint();
    }

    // PHƯƠNG THỨC QUAN TRỌNG NHẤT: VẼ NÚT
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Bật chế độ khử răng cưa để góc bo được mịn
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ hình chữ nhật bo góc làm nền
        g2.setColor(currentColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        g2.dispose();

        // Để lớp cha vẽ chữ (text) lên trên
        super.paintComponent(g);
    }
}
