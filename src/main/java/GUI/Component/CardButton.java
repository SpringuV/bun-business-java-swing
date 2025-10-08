package GUI.Component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class CardButton extends JButton {
    private static final Color ICON_CLR = new Color(120, 0, 0);
    private static final Color CARD_BG = Color.WHITE;

    public CardButton(String text, String iconText) {
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
