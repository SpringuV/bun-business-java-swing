package GUI.Form.Profession.OutputInput;

import javax.swing.*;
import javax.swing.border.*;

import GUI.Form.Main_Form;
import GUI.Form.Profession.Profession_Panel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import utils.SimpleDocumentListener;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Base class cho các form chọn kho (nhập / xuất) Cung cấp: - Header (màu đỏ mận
 * + nút Back + tiêu đề) - Ô tìm kiếm - Danh sách kho (JList) - Nút Tiếp tục -
 * Thanh navigation dưới cùng
 *
 * Các lớp con (InputInventoryTransaction_Form /
 * OutputInventoryTransaction_Form) chỉ cần: - setTitle() & lblTitle.setText() -
 * gọi setWarehouses() - gán cellRenderer phù hợp
 */
@FieldDefaults(level = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class BaseInputOutputInventory<T> extends JPanel {
	// ===== Theme =====
	static final Color PRIMARY = new Color(128, 0, 0); // đỏ mận
	static final Color CARD_DIVIDER = new Color(245, 245, 245);
	static final Color TEXT_MUTED = new Color(90, 90, 90);

	// ===== Header =====
	final JButton btnBack = new JButton("←");
	final JLabel lblTitle = new JLabel();

	// ===== Search + List =====
	public final JTextField txtSearch = new JTextField();
	final DefaultListModel<T> listModel = new DefaultListModel<>();
	final JList<T> warehouseList = new JList<>(listModel);

	// ===== Action =====
	final JButton btnContinue = new JButton("Tiếp tục");

	protected abstract void onContinue();

	public BaseInputOutputInventory() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(920, 560));
		initUI();
	}

	/** Cấu trúc UI chung */
	private void initUI() {
		// ===== Header =====
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(PRIMARY);
		header.setBorder(new EmptyBorder(10, 12, 10, 12));
		styleHeaderButton(btnBack);

		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 16f));

		header.add(btnBack, BorderLayout.WEST);
		header.add(lblTitle, BorderLayout.CENTER);
		add(header, BorderLayout.NORTH);

		// ===== Center (search + list + button) =====
		JPanel center = new JPanel(new BorderLayout(10, 10));
		center.setBorder(new EmptyBorder(10, 12, 10, 12));

		// Search box
		JPanel searchWrap = new JPanel(new BorderLayout());
		searchWrap.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(220, 220, 220)),
				new EmptyBorder(8, 10, 8, 10)));
		JLabel icon = new JLabel("🔎  ");
		txtSearch.putClientProperty("JTextField.placeholderText", "Nhập tên, mã kho");
		txtSearch.setBorder(null);
		searchWrap.add(icon, BorderLayout.WEST);
		searchWrap.add(txtSearch, BorderLayout.CENTER);
		center.add(searchWrap, BorderLayout.NORTH);

		// List
		warehouseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		warehouseList.setFixedCellHeight(78);
		JScrollPane sp = new JScrollPane(warehouseList);
		sp.setBorder(new EmptyBorder(0, 0, 0, 0));
		center.add(sp, BorderLayout.CENTER);

		// Action button (Tiếp tục)
		JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		actions.add(btnContinue);
		center.add(actions, BorderLayout.SOUTH);

		add(center, BorderLayout.CENTER);

		// ===== Nút quay lại =====
		btnBack.addActionListener(e -> {
			Container parent = getParent();
			while (parent != null) {
				if (parent instanceof Profession_Panel) {
					((Profession_Panel) parent).showMainMenu();
					return;
				}
				parent = parent.getParent();
			}

			// Nếu không nằm trong Profession_Panel thì fallback quay lại frame chính
			Container top = getTopLevelAncestor();
			if (top instanceof Main_Form) {
				((Main_Form) top).goBack();
			}
		});

		btnContinue.addActionListener(e -> onContinue());

	}

	/** Styling cho header button */
	protected void styleHeaderButton(AbstractButton b) {
		b.setForeground(Color.WHITE);
		b.setBackground(PRIMARY);
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setContentAreaFilled(false);
		b.setFont(b.getFont().deriveFont(Font.BOLD, 16f));
	}

	/** Styling cho navigation button */
	protected void styleNavButton(AbstractButton b) {
		b.setForeground(Color.WHITE);
		b.setBackground(PRIMARY);
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setContentAreaFilled(false);
		b.setFont(b.getFont().deriveFont(Font.PLAIN, 12f));
	}

	// ===================== Thêm cho MVC =====================
	public T getSelectedWarehouse() {
		return warehouseList.getSelectedValue();
	}

	/** Gán danh sách kho */
	public void setWarehouses(List<T> list) {
		listModel.clear();
		for (T item : list) {
			listModel.addElement(item);
		}
	}

	public String getSearchText() {
		return txtSearch.getText().trim().toLowerCase();
	}

	/** Gắn listener cho sự kiện search (document change) */
	public void addSearchListener(Runnable listener) {
		txtSearch.getDocument().addDocumentListener(new SimpleDocumentListener(listener));
	}

	/** Gắn listener cho nút Continue (nếu controller muốn override) */
	public void addContinueListener(Runnable listener) {
		for (ActionListener al : btnContinue.getActionListeners()) {
			btnContinue.removeActionListener(al);
		}
		btnContinue.addActionListener(e -> listener.run());
	}

	/** Gắn listener cho nút Back (nếu controller muốn override) */
	public void addBackListener(Runnable listener) {
		for (ActionListener al : btnBack.getActionListeners()) {
			btnBack.removeActionListener(al);
		}
		btnBack.addActionListener(e -> listener.run());
	}

}
