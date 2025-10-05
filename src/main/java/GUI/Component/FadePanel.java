/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class FadePanel extends JPanel {
    private float alpha = 1f;

    public FadePanel() {
        setOpaque(false); // nền trong suốt, để introPanel màu đỏ hiện ra phía sau
    }

    public void setAlpha(float a) {
        alpha = Math.max(0f, Math.min(1f, a));
        repaint();
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    public void paint(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) return;

        // vẽ toàn bộ panel (bao gồm children) lên buffered image
        BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuf = buf.createGraphics();
        // Quan trọng: gọi super.paint(gBuf) để paint children lên buffer
        super.paint(gBuf);
        gBuf.dispose();

        // vẽ buffer với alpha lên Graphics g
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.drawImage(buf, 0, 0, null);
        g2.dispose();
    }
}
