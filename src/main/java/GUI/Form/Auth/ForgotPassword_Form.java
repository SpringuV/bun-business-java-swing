package GUI.Form.Auth;

import GUI.Component.RoundedButton;
import GUI.Component.RoundedTextField;
import javax.swing.*;
import java.awt.*;
import org.jdesktop.swingx.prompt.PromptSupport;

public class ForgotPassword_Form extends JFrame {
    private JTextField txtEmail;
    private JButton btnSubmit, btnBack;

    public ForgotPassword_Form() {
        setTitle("Quên mật khẩu");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        

        // Main panel with vertical layout
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Logo (nếu không có ảnh sẽ hiện text)
        JLabel logo;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Images/logoOHA.jpg"));
            Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            logo = new JLabel(new ImageIcon(scaled));
        } catch (Exception ex) {
            logo = new JLabel("LOGO");
            logo.setForeground(Color.WHITE);
            logo.setFont(new Font("Serif", Font.BOLD, 28));
        }
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel lblTitle = new JLabel("Khôi phục mật khẩu");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(Color.decode("#6D0707"));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Email text field
        txtEmail = new RoundedTextField(20, 30);
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setPreferredSize(new Dimension(220, 36));
        txtEmail.setMaximumSize(new Dimension(220, 36));
        PromptSupport.setPrompt("Email", txtEmail);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, txtEmail);
        PromptSupport.setForeground(Color.GRAY, txtEmail); 
        

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer

        // Submit button
        btnSubmit = new RoundedButton("Khôi phục mật khẩu", 30);
        btnSubmit.setBackground(Color.decode("#6D0707"));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setMaximumSize(new Dimension(150, 40));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        // Back button
        btnBack = new RoundedButton("Quay lại", 30);
        btnBack.setBackground(Color.decode("#6D0707"));
        btnBack.setForeground(Color.WHITE);
        btnBack.setMaximumSize(new Dimension(150, 40));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(logo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(txtEmail);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnSubmit);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnBack);
        mainPanel.add(Box.createVerticalGlue());

        // Add panel to frame
        add(mainPanel);
    }


    // Getters
    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JButton getBtnSubmit() {
        return btnSubmit;
    }

    public JButton getBtnBack() {
        return btnBack;
    }
}
