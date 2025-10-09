package GUI.Form.GuestTable;

import GUI.Component.RoundedPanel;
import GUI.Interface.IGuestTableView;
import controller.GuestTable_Controller;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import model.GuestTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class GuestTable_Panel extends JPanel implements IGuestTableView {
	List<RoundedPanel> tableList = new ArrayList<>();// lưu toàn bộ các bàn tạo ở vòng for
	JMenuBar menuBarr;
	JMenu menu;
	JMenuItem menuQLSanPham, menuQLNhapXuatKho, menuThongKeBaoCao, menuQuanLyKho, menuDatBan, menuThemBan;
	JPanel mainPanel;
	GuestTable_Controller controller;

	public GuestTable_Panel() {
		init();
	}

	// ============== INITIALIZATION ==============
	private void init() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		mainPanel = new JPanel(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);

		setupMenuBar();
		setupTitle();
	}

	private void setupMenuBar() {
		// thêm menu bar như 1 panel con ở phía trên
		menuBarr = new JMenuBar();
		menu = new JMenu("Giao diện");
		menuDatBan = new JMenuItem("Đặt bàn");
		menuQLSanPham = new JMenuItem("Quản lý sản phẩm");
		menuQLNhapXuatKho = new JMenuItem("Quản lý Nhập/xuất");
		menuQuanLyKho = new JMenuItem("Quản lý kho");
		menuThongKeBaoCao = new JMenuItem("Thống kê và báo cáo");
		menuThemBan = new JMenuItem("Thêm bàn mới");

		menu.add(menuThemBan);
		menu.add(menuDatBan);
		menu.add(menuQLSanPham);
		menu.add(menuQLNhapXuatKho);
		menu.add(menuQuanLyKho);
		menu.add(menuThongKeBaoCao);
		menuBarr.add(menu);

		// Đặt menu bar lên trên panel chính
		add(menuBarr, BorderLayout.NORTH);

		// Gắn sự kiện
		menuThemBan.addActionListener(e -> {
			if (controller != null)
				controller.onAddTableClicked();
		});
		menuDatBan.addActionListener(e -> showMessage("Tính năng đặt bàn đang phát triển!"));
	}

	private void setupTitle() {
		JLabel title = new JLabel("Sơ đồ bàn quán bún bò Huế O Hà", SwingConstants.CENTER);
		title.setFont(new Font("Segoe UI", Font.BOLD, 26));
		title.setForeground(new Color(109, 7, 7));
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		mainPanel.add(title, BorderLayout.NORTH);
	}

	// ================= IGuestTableView =================
	@Override
	public void displayTables(List<GuestTable> tables) {
		JPanel tableGrid = new JPanel(new GridLayout(3, 4, 20, 20));
		tableGrid.setBackground(new Color(245, 245, 245));
		tableGrid.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
		tableList.clear();

		for (GuestTable t : tables) {
			RoundedPanel panel = createTablePanel(t);
			tableList.add(panel);
			tableGrid.add(panel);
		}

		mainPanel.removeAll();
		setupTitle();
		mainPanel.add(tableGrid, BorderLayout.CENTER);
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	private RoundedPanel createTablePanel(GuestTable table) {
		boolean available = table.is_available();
		Color color = available ? new Color(76, 175, 80) : new Color(229, 57, 53);

		RoundedPanel panel = new RoundedPanel(new BorderLayout(), 25, color);
		panel.setPreferredSize(new Dimension(150, 120));
		panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JLabel lblIcon = new JLabel("🍜", SwingConstants.CENTER);
		lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
		panel.add(lblIcon, BorderLayout.CENTER);

		JLabel lblName = new JLabel("Bàn " + table.getId_table(), SwingConstants.CENTER);
		lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblName.setForeground(Color.WHITE);
		panel.add(lblName, BorderLayout.NORTH);

		JLabel lblStatus = new JLabel(available ? "Trống" : "Đang phục vụ", SwingConstants.CENTER);
		lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblStatus.setForeground(Color.WHITE);
		panel.add(lblStatus, BorderLayout.SOUTH);

		panel.putClientProperty("guestTable", table);

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.onTableClicked(table);
			}
		});

		return panel;
	}

	@Override
	public void addMenuDatBanListener(ActionListener listener) {
		menuDatBan.addActionListener(listener);
	}

	@Override
	public void addTablePanelMouseListener(MouseAdapter listener) {
		for (RoundedPanel panel : tableList) {
			panel.addMouseListener(listener);
		}
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	@Override
	public void openAddTableForm() {
		new AddGuestTable_Form(controller).setVisible(true);
	}

	@Override
	public void openOrderForm(GuestTable table) {
		showMessage("Mở Order cho bàn " + table.getId_table());
	}

	public void setController(GuestTable_Controller controller) {
		this.controller = controller;
	}

}
