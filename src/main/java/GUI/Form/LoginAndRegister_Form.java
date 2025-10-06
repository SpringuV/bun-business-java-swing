/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Form;

import GUI.Component.FadePanel;
import GUI.Component.RoundedBorder;
import GUI.Component.RoundedButton;
import GUI.Component.RoundedButtonToggle;
import GUI.Component.RoundedPasswordField;
import GUI.Component.RoundedTextField;
import controller.LoginAndRegister_Controller;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 *
 * @author Admin
 */
public class LoginAndRegister_Form extends JFrame {
    private JLayeredPane layered;
    private JPanel leftForm, rightForm, introPanel;
    private JTextField txtUserLogin, txtUserRegister, txtEmail;
    private JPasswordField txtPassLogin, txtPassRegister;
    private JButton btnLogin, btnForgot, btnRegister, btnToggle;
    public boolean showingLogin = true;
    private Timer anim;
    private JLabel txt;

 
    private final int FRAME_W = 1016;
    private final int FRAME_H = 600;
    private final int INTRO_W = 416;            // chiều rộng panel đỏ
    private final int FORM_W = FRAME_W - INTRO_W; // 600 nếu FRAME_W=1016

    public LoginAndRegister_Form() {
        setTitle("Login And Register");
        setSize(FRAME_W, FRAME_H);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        layered = new JLayeredPane();
        layered.setBounds(0, 0, FRAME_W, FRAME_H);
        layered.setLayout(null);
        add(layered);

        // tạo 2 form: login (trái) và register (phải)
        leftForm = createLoginPanel();
        leftForm.setBounds(0, 0, FORM_W, FRAME_H);
        layered.add(leftForm, Integer.valueOf(JLayeredPane.DEFAULT_LAYER));

        rightForm = createRegisterPanel();
        rightForm.setBounds(INTRO_W, 0, FORM_W, FRAME_H); // nằm cạnh bên phải
        layered.add(rightForm, Integer.valueOf(JLayeredPane.DEFAULT_LAYER));

        // panel intro (đỏ) đặt trên layer trên cùng, ban đầu nằm ở x = FORM_W (che rightForm)
        introPanel = createIntroPanel();
        introPanel.setBounds(FORM_W, 0, INTRO_W, FRAME_H);
        layered.add(introPanel, Integer.valueOf(JLayeredPane.PALETTE_LAYER));
        
        
    }

// --------------------------------Form Đăng nhập--------------------------------
    private JPanel createLoginPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("ĐĂNG NHẬP");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.decode("#6D0707"));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //ô text nhập tên user
        txtUserLogin = new RoundedTextField(20, 30);
        txtUserLogin.setPreferredSize(new Dimension(220, 36));
        txtUserLogin.setMaximumSize(new Dimension(220, 36));
        PromptSupport.setPrompt("User", txtUserLogin);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, txtUserLogin);
        PromptSupport.setForeground(Color.GRAY, txtUserLogin); 
        
        //ô text nhập MK
        txtPassLogin = new RoundedPasswordField(20, 30);
        txtPassLogin.setPreferredSize(new Dimension(220, 36));
        txtPassLogin.setMaximumSize(new Dimension(220, 36));
        PromptSupport.setPrompt("Password", txtPassLogin);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, txtPassLogin);
        PromptSupport.setForeground(Color.GRAY, txtPassLogin); 
        
        //btn quên MK
        btnForgot = new JButton("Quên mật khẩu?");
        btnForgot.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnForgot.setBackground(Color.WHITE);
        btnForgot.setForeground(new Color(100, 100, 100));
        btnForgot.setBorderPainted(false);
        btnForgot.setFocusPainted(false);
        btnForgot.setContentAreaFilled(false);
        btnForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnForgot.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //btn đăng nhập
        btnLogin = new RoundedButton("ĐĂNG NHẬP", 30);
        btnLogin.setBackground(Color.decode("#6D0707"));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(150, 30));
        btnLogin.setMaximumSize(new Dimension(150, 40));
        btnLogin.setBorder(new RoundedBorder(10));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        p.add(Box.createVerticalGlue());
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(txtUserLogin);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(txtPassLogin);
        p.add(Box.createRigidArea(new Dimension(0, 3)));
        p.add(btnForgot);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(btnLogin);
        p.add(Box.createVerticalGlue());

        return p;
    }
//----------------------------End Form Đăng nhập-----------------------------

    
// -------------------------------Form Đăng ký--------------------------------
    private JPanel createRegisterPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("ĐĂNG KÝ");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.decode("#6D0707"));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //ô text nhập tên user
        txtUserRegister = new RoundedTextField(20, 30);
        txtUserRegister.setPreferredSize(new Dimension(220, 36));
        txtUserRegister.setMaximumSize(new Dimension(220, 36));
        txtUserRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        PromptSupport.setPrompt("User", txtUserRegister);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, txtUserRegister);
        PromptSupport.setForeground(Color.GRAY, txtUserRegister); 
        
        //ô text nhập email
        txtEmail = new RoundedTextField(20, 30);
        txtEmail.setPreferredSize(new Dimension(220, 36));
        txtEmail.setMaximumSize(new Dimension(220, 36));
        txtEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        PromptSupport.setPrompt("Email", txtEmail);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, txtEmail);
        PromptSupport.setForeground(Color.GRAY, txtEmail); 
        
        //ô text nhập MK
        txtPassRegister = new RoundedPasswordField(20, 30);
        txtPassRegister.setPreferredSize(new Dimension(220, 36));
        txtPassRegister.setMaximumSize(new Dimension(220, 36));
        txtPassRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        PromptSupport.setPrompt("Password", txtPassRegister);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, txtPassRegister);
        PromptSupport.setForeground(Color.GRAY, txtPassRegister); 

        //btn đăng ký
        btnRegister = new RoundedButton("TẠO TÀI KHOẢN", 30);
        btnRegister.setBackground(Color.decode("#6D0707"));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setMaximumSize(new Dimension(150, 40));
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(Box.createVerticalGlue());
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(txtUserRegister);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(txtEmail);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(txtPassRegister);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(btnRegister);
        p.add(Box.createVerticalGlue());

        return p;
    }
 // -------------------------End Form Đăng ký--------------------------------   
    
// --------------------Tạo Intro(cái bìa đỏ đỏ)--------------------------------
    private JPanel createIntroPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(Color.decode("#6D0707"));
        FadePanel content = new FadePanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBounds(0, 0, INTRO_W, FRAME_H);
        p.add(content);

        // Logo (nếu không có ảnh sẽ hiện text)
        JLabel logo;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Images/logoOHA.jpg"));
            Image scaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            logo = new JLabel(new ImageIcon(scaled));
        } catch (Exception ex) {
            logo = new JLabel("LOGO");
            logo.setForeground(Color.WHITE);
            logo.setFont(new Font("Serif", Font.BOLD, 28));
        }
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // dòng chữ trên intro
        txt = new JLabel("<html><div style='text-align: center; color:white; font-family: Serif; font-size: 13px;'>"
                        + "Bạn chưa có tài khoản tại<br>"
                        + "<span style='font-family: Serif; font-size: 25px; font-weight: plain; color: white;'>Bún bò huế O HÀ</span>"
                        + "</div></html>");
        txt.setForeground(Color.WHITE);
        txt.setFont(new Font("Serif", Font.ITALIC, 18));
        txt.setHorizontalAlignment(SwingConstants.CENTER); // căn giữa text trong label
        txt.setAlignmentX(Component.CENTER_ALIGNMENT);     // căn giữa label trong BoxLayout
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, txt.getPreferredSize().height));

        // nút chuyển đổi form
        btnToggle = new RoundedButtonToggle("ĐĂNG KÝ", 30);
        btnToggle.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnToggle.setMaximumSize(new Dimension(150, 36));
        
        LoginAndRegister_Controller controller = new LoginAndRegister_Controller(this);
        

        content.add(Box.createVerticalGlue());
        content.add(logo);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(txt);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(btnToggle);
        content.add(Box.createVerticalGlue());
        
        p.putClientProperty("contentPanel", content);

        return p;
    }
//---------------------------End Intro-----------------------------------------
    

//getter
    public JTextField getTxtUserLogin() {return txtUserLogin;}
    public JTextField getTxtUserRegister() {return txtUserRegister;}
    public JTextField getTxtEmail() {return txtEmail;}
    public JPasswordField getTxtPassLogin() {return txtPassLogin;}
    public JPasswordField getTxtPassRegister() {return txtPassRegister;}
    public JButton getBtnLogin() {return btnLogin;}
    public JButton getBtnForgot() {return btnForgot;}
    public JButton getBtnRegister() {return btnRegister;}
    public JButton getBtnToggle() {return btnToggle;}
    public Timer getAnim() {return anim;}
    public boolean isShowingLogin() {return showingLogin;}
    public JLabel getTxt() {return txt;}
    public JLayeredPane getLayered() {return layered;}
    public JPanel getLeftForm() {return leftForm;}
    public JPanel getIntroPanel() {return introPanel;}
    public JPanel getRightForm() {return rightForm;}
    public int getFRAME_W() {return FRAME_W;}
    public int getFRAME_H() {return FRAME_H;}
    public int getINTRO_W() {return INTRO_W;}
    public int getFORM_W() {return FORM_W;}
    public void setAnim(Timer anim) {this.anim = anim;}
    
     
//     public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new LoginAndRegister_Form().setVisible(true));
//    }
}
