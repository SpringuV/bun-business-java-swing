/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.Border;

/**
 *
 * @author Admin
 */
public class RoundedBorder implements Border {
    private int radius;
    
    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY); // màu viền
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
    
}
