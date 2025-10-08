package GUI.Form.InventoryTransaction;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Date;

public class Warehouse_Form extends JPanel {

    private JTextField txtNguoiNhap;
    private JSpinner spNgayNhap;
    private JComboBox<String> cboKhoNhap;

    private JTable tblSanPham;
    private DefaultTableModel tableModel;
    private boolean isUpdating = false;

    private JTextField txtTongTien;
    private JButton btnNhap;
    private JButton btnHuy;

    public Warehouse_Form() {
        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Header
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBorder(new TitledBorder(new LineBorder(new Color(220,220,220)), "Thông tin phiếu nhập", TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNguoiNhap = new JLabel("Người nhập:");
        txtNguoiNhap = new JTextField();
        txtNguoiNhap.setEditable(false);

        JLabel lblNgayNhap = new JLabel("Ngày nhập:");
        spNgayNhap = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        spNgayNhap.setEditor(new JSpinner.DateEditor(spNgayNhap, "dd/MM/yyyy"));

        JLabel lblKhoNhap = new JLabel("Kho nhập:");
        cboKhoNhap = new JComboBox<>();
        cboKhoNhap.setEnabled(false);

        gbc.gridy = 0; gbc.gridx = 0; headerPanel.add(lblNguoiNhap, gbc);
        gbc.gridx = 1; gbc.weightx = 1; headerPanel.add(txtNguoiNhap, gbc);
        gbc.gridy = 1; gbc.gridx = 0; gbc.weightx = 0; headerPanel.add(lblNgayNhap, gbc);
        gbc.gridx = 1; gbc.weightx = 1; headerPanel.add(spNgayNhap, gbc);
        gbc.gridy = 2; gbc.gridx = 0; headerPanel.add(lblKhoNhap, gbc);
        gbc.gridx = 1; gbc.weightx = 1; headerPanel.add(cboKhoNhap, gbc);

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

        tblSanPham = new JTable(tableModel);
        tblSanPham.setRowHeight(26);
        tblSanPham.setFillsViewportHeight(true);
        tblSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Căn chỉnh kích thước cột
        JTableHeader header = tblSanPham.getTableHeader();
        header.setReorderingAllowed(false);

        TableColumnModel columnModel = tblSanPham.getColumnModel();
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

        JScrollPane scrollPane = new JScrollPane(tblSanPham);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder(new LineBorder(new Color(220,220,220)), "Danh sách sản phẩm", TitledBorder.LEFT, TitledBorder.TOP));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(6,6,6,6);
        f.gridy = 0; f.gridx = 0; f.anchor = GridBagConstraints.WEST;

        JLabel lblTongTien = new JLabel("Tổng tiền:");
        txtTongTien = new JTextField("0");
        txtTongTien.setEditable(false);
        txtTongTien.setHorizontalAlignment(SwingConstants.RIGHT);

        btnNhap = new JButton("Nhập");
        btnHuy = new JButton("Hủy");

        footerPanel.add(lblTongTien, f);
        f.gridx = 1; f.weightx = 1; f.fill = GridBagConstraints.HORIZONTAL; footerPanel.add(txtTongTien, f);
        f.gridx = 2; f.weightx = 0; f.fill = GridBagConstraints.NONE; footerPanel.add(btnNhap, f);
        f.gridx = 3; footerPanel.add(btnHuy, f);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    // Getters

    public JTextField getTxtNguoiNhap() {
        return txtNguoiNhap;
    }

    public JSpinner getSpNgayNhap() {
        return spNgayNhap;
    }

    public JComboBox<String> getCboKhoNhap() {
        return cboKhoNhap;
    }

    public JTable getTblSanPham() {
        return tblSanPham;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public boolean isIsUpdating() {
        return isUpdating;
    }

    public JTextField getTxtTongTien() {
        return txtTongTien;
    }

    public JButton getBtnNhap() {
        return btnNhap;
    }

    public JButton getBtnHuy() {
        return btnHuy;
    }

}
