package GUI.Form.Profession.Statistical;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.awt.*;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class InventoryFormBase extends JPanel {
	// Header
	JTextField txtActor;
	JSpinner spCreatedAt;
	JComboBox<String> comboBoxWarehouse;

	// Table
	JTable tblProduct;
	DefaultTableModel tableModel;

	// Footer
	JTextField txtTotalMoney;
	JButton btnAction;
	JButton btnCancel;

	boolean isUpdating = false;

	public InventoryFormBase(String headerTitle, String actorLabel, String warehouseLabel, String buttonText) {
		setLayout(new BorderLayout(12, 12));
		setBorder(new EmptyBorder(12, 12, 12, 12));
		initHeader(headerTitle, actorLabel, warehouseLabel);
		initTable();
		initFooter(buttonText);
		addListeners();
	}

	private void initHeader(String headerTitle, String actorLabel, String warehouseLabel) {
		JPanel header = new JPanel(new GridBagLayout());
		header.setBorder(new TitledBorder(new LineBorder(new Color(220, 220, 220)), headerTitle, TitledBorder.LEFT,
				TitledBorder.TOP));
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(6, 6, 6, 6);
		g.fill = GridBagConstraints.HORIZONTAL;

		JLabel lActor = new JLabel(actorLabel);
		txtActor = new JTextField();
		txtActor.setEditable(false);

		JLabel lDate = new JLabel("Ngày:");
		spCreatedAt = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
		spCreatedAt.setEditor(new JSpinner.DateEditor(spCreatedAt, "dd/MM/yyyy"));

		JLabel lWarehouse = new JLabel(warehouseLabel);
		comboBoxWarehouse = new JComboBox<>();
		comboBoxWarehouse.setEnabled(false);

		g.gridy = 0;
		g.gridx = 0;
		header.add(lActor, g);
		g.gridx = 1;
		g.weightx = 1;
		header.add(txtActor, g);
		g.gridy = 1;
		g.gridx = 0;
		g.weightx = 0;
		header.add(lDate, g);
		g.gridx = 1;
		g.weightx = 1;
		header.add(spCreatedAt, g);
		g.gridy = 2;
		g.gridx = 0;
		header.add(lWarehouse, g);
		g.gridx = 1;
		g.weightx = 1;
		header.add(comboBoxWarehouse, g);

		add(header, BorderLayout.NORTH);
	}

	private void initTable() {
		String[] cols = { "STT", "Tên", "Đơn vị", "Số lượng", "Giá", "Tổng" };
		tableModel = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return c == 3 || c == 4;
			}

			@Override
			public Class<?> getColumnClass(int i) {
				switch (i) {
				case 0:
					return Integer.class;
				case 3:
					return Integer.class;
				case 4:
					return Double.class;
				case 5:
					return Double.class;
				default:
					return String.class;
				}
			}
		};

		tblProduct = new JTable(tableModel);
		tblProduct.setRowHeight(26);
		tblProduct.setFillsViewportHeight(true);
		tblProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblProduct.getTableHeader().setReorderingAllowed(false);

		TableColumnModel m = tblProduct.getColumnModel();
		m.getColumn(0).setPreferredWidth(50);
		m.getColumn(1).setPreferredWidth(300);
		m.getColumn(2).setPreferredWidth(100);
		m.getColumn(3).setPreferredWidth(100);
		m.getColumn(4).setPreferredWidth(120);
		m.getColumn(5).setPreferredWidth(150);

		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(SwingConstants.RIGHT);
		m.getColumn(0).setCellRenderer(right);
		m.getColumn(3).setCellRenderer(right);
		m.getColumn(4).setCellRenderer(right);
		m.getColumn(5).setCellRenderer(right);

		JScrollPane sp = new JScrollPane(tblProduct);
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setBorder(new TitledBorder(new LineBorder(new Color(220, 220, 220)), "Danh sách sản phẩm",
				TitledBorder.LEFT, TitledBorder.TOP));
		tablePanel.add(sp, BorderLayout.CENTER);

		add(tablePanel, BorderLayout.CENTER);
	}

	private void initFooter(String buttonText) {
		JPanel footer = new JPanel(new GridBagLayout());
		GridBagConstraints f = new GridBagConstraints();
		f.insets = new Insets(6, 6, 6, 6);
		f.gridy = 0;
		f.gridx = 0;
		f.anchor = GridBagConstraints.WEST;

		JLabel lTotal = new JLabel("Tổng tiền:");
		txtTotalMoney = new JTextField("0");
		txtTotalMoney.setEditable(false);
		txtTotalMoney.setHorizontalAlignment(SwingConstants.RIGHT);

		btnAction = new JButton(buttonText);
		btnCancel = new JButton("Hủy");

		footer.add(lTotal, f);
		f.gridx = 1;
		f.weightx = 1;
		f.fill = GridBagConstraints.HORIZONTAL;
		footer.add(txtTotalMoney, f);
		f.gridx = 2;
		f.weightx = 0;
		f.fill = GridBagConstraints.NONE;
		footer.add(btnAction, f);
		f.gridx = 3;
		footer.add(btnCancel, f);

		add(footer, BorderLayout.SOUTH);
	}

	private void addListeners() {
		tableModel.addTableModelListener(e -> {
			if (isUpdating)
				return;
			if (e.getType() != TableModelEvent.UPDATE)
				return;
			int col = e.getColumn();
			if (col != 3 && col != 4)
				return;
			int row = e.getFirstRow();
			try {
				Object quantityObj = tableModel.getValueAt(row, 3);
				Object pricesObj = tableModel.getValueAt(row, 4);
				if (quantityObj == null || pricesObj == null)
					return;
				int sl = Integer.parseInt(quantityObj.toString());
				double gia = Double.parseDouble(pricesObj.toString());
				double tong = sl * gia;
				Object old = tableModel.getValueAt(row, 5);
				if (old == null || Double.compare(tong, Double.parseDouble(old.toString())) != 0) {
					isUpdating = true;
					tableModel.setValueAt(tong, row, 5);
					isUpdating = false;
				}
				updateTotalMoney();
			} catch (Exception ex) {
			}
		});
	}

	protected void updateTotalMoney() {
		double sum = 0;
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			Object v = tableModel.getValueAt(i, 5);
			if (v != null)
				try {
					sum += Double.parseDouble(v.toString());
				} catch (Exception ignored) {
				}
		}
		txtTotalMoney.setText(String.format("%,.0f", sum));
	}
}
