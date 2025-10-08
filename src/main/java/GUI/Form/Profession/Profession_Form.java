package GUI.Form.Profession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import GUI.Component.CardButton;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Profession_Form extends JPanel {
	// Colors
	static final Color MAROON = new Color(120, 0, 0);
	static final Color LIGHT_BG = new Color(250, 250, 250);
	static final Color CARD_BG = Color.WHITE;
	static final Color ICON_CLR = new Color(120, 0, 0);
	// Header
	JLabel lbTitle;
	// Cards
	CardButton btnInputWarehouse;
	CardButton btnOutputWarehouse;
	CardButton btnInventory; // kiểm kê

	CardLayout cardLayout;
	JPanel pnMainContent;

	public Profession_Form() {
		setLayout(new BorderLayout());
		setBackground(LIGHT_BG);

		// e đang sửa đoạn này-----------------
		// cardLayout = new CardLayout();
		// pnMainContent = new JPanel(cardLayout);
		// -------------------------------------

		// ===== Header bar =====
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(MAROON);
		header.setBorder(new EmptyBorder(10, 16, 10, 16));
		lbTitle = new JLabel("Nghiệp vụ");
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setFont(lbTitle.getFont().deriveFont(Font.BOLD, 16f));
		header.add(lbTitle, BorderLayout.WEST);
		add(header, BorderLayout.NORTH);

		// ===== Cards area =====
		JPanel center = new JPanel(new GridBagLayout());
		center.setBackground(LIGHT_BG);
		center.setBorder(new EmptyBorder(24, 24, 24, 24));
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 12, 0, 12);

		btnInputWarehouse = new CardButton("Nhập kho", "\u21B0"); // icon placeholder ↰
		btnOutputWarehouse = new CardButton("Xuất kho", "\u21B1"); // icon placeholder ↱
		btnInventory = new CardButton("Kiểm kê", "\u25A6\u25A6"); // icon placeholder ▦▦

		c.gridx = 0;
		c.gridy = 0;
		center.add(btnInputWarehouse, c);
		c.gridx = 1;
		c.gridy = 0;
		center.add(btnOutputWarehouse, c);
		c.gridx = 2;
		c.gridy = 0;
		center.add(btnInventory, c);

		add(center, BorderLayout.CENTER);

		// e đang sửa-------------------------------------
		// btnNhapKho.setActionCommand("switchToWarehouse");
		// Warehouse_Form warehousePanel = new Warehouse_Form();
		// pnMainContent.add(warehousePanel, "WAREHOUSE");
		// --------------------------------------------------
	}
	// em đang sửa chỗ này
	// // Phương thức để quản gia ra lệnh chuyển phòng
	// public void switchToPanel(String panelName) {
	// cardLayout.show(pnMainContent, panelName);
	// }
}
