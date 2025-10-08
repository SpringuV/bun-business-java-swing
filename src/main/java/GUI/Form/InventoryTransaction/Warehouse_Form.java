package GUI.Form.InventoryTransaction;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Warehouse_Form extends JPanel {

    JTextField txtActorInput;
    JSpinner spinnerCreatedAt;
    JComboBox<String> comboBoxInputWarehouse;

    JTable tblProductFood;
    DefaultTableModel tableModel;
    boolean isUpdating = false;

    JTextField txtTotalMoney;
    JButton btnInput;
    JButton btnCancel;

    public Warehouse_Form() {
        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Header
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBorder(new TitledBorder(new LineBorder(new Color(220,220,220)), "Thông tin phiếu nhập", TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblActorInput = new JLabel("Người nhập:");
        txtActorInput = new JTextField();
        txtActorInput.setEditable(false);

        JLabel lblCreateAt = new JLabel("Ngày nhập:");
        spinnerCreatedAt = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        spinnerCreatedAt.setEditor(new JSpinner.DateEditor(spinnerCreatedAt, "dd/MM/yyyy"));

        JLabel lblInputWarehouse = new JLabel("Kho nhập:");
        comboBoxInputWarehouse = new JComboBox<>();
        comboBoxInputWarehouse.setEnabled(false);

        gbc.gridy = 0; gbc.gridx = 0; headerPanel.add(lblActorInput, gbc);
        gbc.gridx = 1; gbc.weightx = 1; headerPanel.add(txtActorInput, gbc);
        gbc.gridy = 1; gbc.gridx = 0; gbc.weightx = 0; headerPanel.add(lblCreateAt, gbc);
        gbc.gridx = 1; gbc.weightx = 1; headerPanel.add(spinnerCreatedAt, gbc);
        gbc.gridy = 2; gbc.gridx = 0; headerPanel.add(lblInputWarehouse, gbc);
        gbc.gridx = 1; gbc.weightx = 1; headerPanel.add(comboBoxInputWarehouse, gbc);

        // Table
        String[] cols = {"STT", "Tên", "Đơn vị", "Số lượng", "Giá", "Tổng"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) {
                return c == 3 || c == 4; // cho phép nhập Số lượng và Giá
            }
            @Override public Class<?> getColumnClass(int i) {
                switch (i) {
                    case 0: return Integer.class;
                    case 3: return Integer.class;
                    case 4: return Double.class;
                    case 5: return Double.class;
                    default: return String.class;
                }
            }
        };

        tblProductFood = new JTable(tableModel);
        tblProductFood.setRowHeight(26);
        tblProductFood.setFillsViewportHeight(true);
        tblProductFood.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Căn chỉnh kích thước cột
        JTableHeader header = tblProductFood.getTableHeader();
        header.setReorderingAllowed(false);

        TableColumnModel columnModel = tblProductFood.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);   // STT
        columnModel.getColumn(1).setPreferredWidth(300);  // Tên sản phẩm
        columnModel.getColumn(2).setPreferredWidth(100);  // Đơn vị
        columnModel.getColumn(3).setPreferredWidth(100);  // Số lượng
        columnModel.getColumn(4).setPreferredWidth(120);  // Giá
        columnModel.getColumn(5).setPreferredWidth(150);  // Tổng

        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        rightAlign.setHorizontalAlignment(SwingConstants.RIGHT);
        columnModel.getColumn(0).setCellRenderer(rightAlign);
        columnModel.getColumn(3).setCellRenderer(rightAlign);
        columnModel.getColumn(4).setCellRenderer(rightAlign);
        columnModel.getColumn(5).setCellRenderer(rightAlign);

        JScrollPane scrollPane = new JScrollPane(tblProductFood);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder(new LineBorder(new Color(220,220,220)), "Danh sách sản phẩm", TitledBorder.LEFT, TitledBorder.TOP));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(6,6,6,6);
        f.gridy = 0; f.gridx = 0; f.anchor = GridBagConstraints.WEST;

        JLabel lblTongTien = new JLabel("Tổng tiền:");
        txtTotalMoney = new JTextField("0");
        txtTotalMoney.setEditable(false);
        txtTotalMoney.setHorizontalAlignment(SwingConstants.RIGHT);

        btnInput = new JButton("Nhập");
        btnCancel = new JButton("Hủy");

        footerPanel.add(lblTongTien, f);
        f.gridx = 1; f.weightx = 1; f.fill = GridBagConstraints.HORIZONTAL; footerPanel.add(txtTotalMoney, f);
        f.gridx = 2; f.weightx = 0; f.fill = GridBagConstraints.NONE; footerPanel.add(btnInput, f);
        f.gridx = 3; footerPanel.add(btnCancel, f);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }
}
