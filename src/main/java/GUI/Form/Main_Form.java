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
import java.awt.event.MouseEvent;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import GUI.Form.GuestTable.GuestTable_Panel;
import GUI.Form.Profession.Profession_Panel;
import app.AppRouter;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Main_Form extends JFrame {
	CardLayout cardLayout;
	JPanel pnMainContent;
	JButton btnGuestTable, btnProfession, btnCategory, btnAccount;
	GuestTable_Panel guestTablePanel;
	Profession_Panel professionPanel;
	Stack<String> navigationStack = new Stack<>(); // để lưu lịch sử

	public Main_Form() {
		initFrame();
		initPanels();
		initTabBar();
		initActions();
		initController();
	}

	// ======= Hàm khởi tạo frame =======
	private void initFrame() {
		setTitle("Phần mềm quản lý quán O Hà");
		setSize(1250, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		cardLayout = new CardLayout();
		pnMainContent = new JPanel(cardLayout);
		add(pnMainContent, BorderLayout.CENTER);
	}

	// ======= Hàm khởi tạo các panel (trang con) =======
	private void initPanels() {
		guestTablePanel = AppRouter.goToGuestTablePanel();
		professionPanel = AppRouter.goToProfessionPanel();
		JPanel categoryPanel = new JPanel();
		categoryPanel.add(new JLabel("Trang Danh mục"));
		JPanel accountPanel = new JPanel();
		accountPanel.add(new JLabel("Trang Tài khoản"));

		pnMainContent.add(guestTablePanel, "GUEST_TABLE");
		pnMainContent.add(professionPanel, "PROFESSION");
		pnMainContent.add(categoryPanel, "CATEGORY");
		pnMainContent.add(accountPanel, "ACCOUNT");
	}

	// ======= Hàm khởi tạo tabbar =======
	private void initTabBar() {
		JPanel pnTabBar = new JPanel();
		pnTabBar.setBackground(Color.WHITE);

		btnGuestTable = createTabButton("Đặt bàn", "/Images/datban.png");
		btnProfession = createTabButton("Nghiệp vụ", "/Images/nghiepvu.png");
		btnCategory = createTabButton("Danh mục", "/Images/danhmuc.png");
		btnAccount = createTabButton("Tài khoản", "/Images/taikhoan.png");

		pnTabBar.add(btnGuestTable);
		pnTabBar.add(btnProfession);
		pnTabBar.add(btnCategory);
		pnTabBar.add(btnAccount);

		add(pnTabBar, BorderLayout.SOUTH);
	}

	// ======= Hàm khởi tạo sự kiện =======
	private void initActions() {
		btnGuestTable.addActionListener(e -> switchToPanel("GUEST_TABLE"));
		btnProfession.addActionListener(e -> switchToPanel("PROFESSION"));
		btnCategory.addActionListener(e -> switchToPanel("CATEGORY"));
		btnAccount.addActionListener(e -> switchToPanel("ACCOUNT"));
	}

	// ======= Hàm khởi tạo controller =======
	private void initController() {
		new Main_Controller(this);
	}

	// === Chuyển sang panel khác và lưu lịch sử ===
	public void switchToPanel(String name) {
		cardLayout.show(pnMainContent, name);
		navigationStack.push(name); // lưu lịch sử để back được
	}

	// === Quay lại panel trước đó ===
	public void goBack() {
		if (navigationStack.size() > 1) {
			navigationStack.pop(); // bỏ trang hiện tại
			String previous = navigationStack.peek(); // lấy trang trước
			cardLayout.show(pnMainContent, previous);
		} else {
			System.out.println("Không có panel trước để quay lại");
		}
	}

	// ======= Tạo button tab =======
	private JButton createTabButton(String text, String iconPath) {
		JButton button = new JButton(text);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFont(new Font("Segoe UI", Font.BOLD, 14));
		button.setForeground(Color.DARK_GRAY);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);

		// Load icon (nếu có)
		try {
			button.setIcon(new ImageIcon(getClass().getResource(iconPath)));
		} catch (Exception e) {
			System.err.println("Không thể tải icon: " + iconPath);
		}

		// Hiệu ứng hover
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				button.setForeground(new Color(0, 102, 204));
			}

			public void mouseExited(MouseEvent evt) {
				button.setForeground(Color.DARK_GRAY);
			}
		});

		return button;
	}

}
