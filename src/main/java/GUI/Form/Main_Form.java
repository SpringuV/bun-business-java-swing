/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Form;

import controller.Main_Controller;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import GUI.Form.Profession.Profession_Form;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Main_Form extends JFrame {
	CardLayout cardLayout;
	JPanel pnMainContent;
	JButton btnGuestTable, btnProfession, btnCategory, btnAccount;

	public Main_Form() {
		setTitle("Phần mềm quản lý quán O Hà");
		setSize(1250, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		cardLayout = new CardLayout();
		pnMainContent = new JPanel(cardLayout);

		JPanel pnTabBar = new JPanel();
		pnTabBar.setBackground(Color.WHITE);

		// Tạo các nút cho Tab Bar
		btnGuestTable = createTabButton("Đặt bàn", "/Images/datban.png");
		btnProfession = createTabButton("Nghiệp vụ", "/Images/nghiepvu.png");
		btnCategory = createTabButton("Danh mục", "/Images/danhmuc.png");
		btnAccount = createTabButton("Tài khoản", "/Images/taikhoan.png");

		// Gắn ActionCommand cho các nút
		btnGuestTable.setActionCommand("switchToGuestTable");
		btnProfession.setActionCommand("switchToProfession");

		pnTabBar.add(btnGuestTable);
		pnTabBar.add(btnProfession);
		pnTabBar.add(btnCategory);
		pnTabBar.add(btnAccount);

//        GuestTable_Form guestTablePanel = new GuestTable_Form(); // <== Chính là form cũ của bạn
		Profession_Form professionPanel = new Profession_Form(); // Panel nghiệp vụ

//        pnMainContent.add(guestTablePanel, "GUEST_TABLE");
		pnMainContent.add(professionPanel, "PROFESSION");

		this.add(pnMainContent, BorderLayout.CENTER);
		this.add(pnTabBar, BorderLayout.SOUTH);

		Main_Controller controller = new Main_Controller(this); // Controller giờ sẽ quản lý MainFrame

	}

	// Phương thức để chuyển panel
	public void switchToPanel(String panelName) {
		cardLayout.show(pnMainContent, panelName);
	}

	private JButton createTabButton(String text, String iconPath) {
		JButton button = new JButton(text);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setFont(new Font("Segoe UI", Font.BOLD, 14));
		button.setForeground(Color.DARK_GRAY);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);

		// icon
		try {
			ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
			button.setIcon(icon);
		} catch (Exception e) {
			System.err.println("Không thể tải icon: " + iconPath);

		}

		// --- THÊM HIỆU ỨNG HOVER ---
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setForeground(new Color(0, 102, 204)); // Đổi thành màu xanh dương
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setForeground(Color.DARK_GRAY);
			}
		});
		// ---------------------------

		return button;
	}
}
