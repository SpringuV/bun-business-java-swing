
package GUI.Form.InventoryTransaction;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.Date;

public class Export_Form extends JFrame {
    // Header fields
    private JTextField txtNguoiXuat;
    private JSpinner spNgayXuat;
    private JComboBox<String> cboKhoXuat;
    // Table
    private JTable tblSanPham;
    private DefaultTableModel tableModel;
    // Footer
    private JTextField txtTongTien;
    private JButton btnXuat;
    private JButton btnHuy;

    private boolean isUpdating = false; // chặn đệ quy khi setValueAt

    public Export_Form() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Phiếu Xuất Kho");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 550);

        JPanel main = new JPanel(new BorderLayout(12,12));
        main.setBorder(new EmptyBorder(12,12,12,12));

        // ==== Header ====
        JPanel header = new JPanel(new GridBagLayout());
        header.setBorder(new TitledBorder(new LineBorder(new Color(220,220,220)), "Thông tin phiếu xuất", TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6,6,6,6);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel lNguoi = new JLabel("Người xuất:");
        txtNguoiXuat = new JTextField();
        txtNguoiXuat.setEditable(false);
        txtNguoiXuat.setToolTipText("Tự động từ tài khoản đang đăng nhập");

        JLabel lNgay = new JLabel("Ngày xuất:");
        spNgayXuat = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        spNgayXuat.setEditor(new JSpinner.DateEditor(spNgayXuat, "dd/MM/yyyy"));

        JLabel lKho = new JLabel("Kho xuất:");
        cboKhoXuat = new JComboBox<>();
        cboKhoXuat.setEnabled(false);
        cboKhoXuat.setPrototypeDisplayValue("Kho Xuất Rất Dài - 000");

        g.gridy=0; g.gridx=0; header.add(lNguoi, g);
        g.gridx=1; g.weightx=1; header.add(txtNguoiXuat, g);
        g.gridy=1; g.gridx=0; g.weightx=0; header.add(lNgay, g);
        g.gridx=1; g.weightx=1; header.add(spNgayXuat, g);
        g.gridy=2; g.gridx=0; g.weightx=0; header.add(lKho, g);
        g.gridx=1; g.weightx=1; header.add(cboKhoXuat, g);

        // ==== Table ====
        String[] cols = {"STT", "Tên", "Đơn vị", "Số lượng", "Giá", "Tổng"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 3 || c == 4; }
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

        JTableHeader th = tblSanPham.getTableHeader();
        th.setReorderingAllowed(false);

        TableColumnModel m = tblSanPham.getColumnModel();
        m.getColumn(0).setPreferredWidth(50);   // STT nhỏ
        m.getColumn(1).setPreferredWidth(300);  // Tên to
        m.getColumn(2).setPreferredWidth(100);  // Đơn vị
        m.getColumn(3).setPreferredWidth(100);  // Số lượng
        m.getColumn(4).setPreferredWidth(120);  // Giá
        m.getColumn(5).setPreferredWidth(150);  // Tổng

        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        m.getColumn(0).setCellRenderer(right);
        m.getColumn(3).setCellRenderer(right);
        m.getColumn(4).setCellRenderer(right);
        m.getColumn(5).setCellRenderer(right);

        JScrollPane sp = new JScrollPane(tblSanPham);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder(new LineBorder(new Color(220,220,220)), "Danh sách sản phẩm", TitledBorder.LEFT, TitledBorder.TOP));
        tablePanel.add(sp, BorderLayout.CENTER);

        // ==== Footer ====
        JPanel footer = new JPanel(new GridBagLayout());
        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(6,6,6,6);
        f.gridy = 0; f.gridx = 0; f.anchor = GridBagConstraints.WEST;

        JLabel lTong = new JLabel("Tổng tiền:");
        txtTongTien = new JTextField("0");
        txtTongTien.setEditable(false);
        txtTongTien.setHorizontalAlignment(SwingConstants.RIGHT);

        btnXuat = new JButton("Xuất");
        btnHuy = new JButton("Hủy");

        footer.add(lTong, f);
        f.gridx = 1; f.weightx = 1; f.fill = GridBagConstraints.HORIZONTAL; footer.add(txtTongTien, f);
        f.gridx = 2; f.weightx = 0; f.fill = GridBagConstraints.NONE; footer.add(btnXuat, f);
        f.gridx = 3; footer.add(btnHuy, f);

        // add panels
        main.add(header, BorderLayout.NORTH);
        main.add(tablePanel, BorderLayout.CENTER);
        main.add(footer, BorderLayout.SOUTH);
        setContentPane(main);

        // auto-calc when quantity/price change
        tableModel.addTableModelListener(new TableModelListener() {
            @Override public void tableChanged(TableModelEvent e) {
                if (isUpdating) return;
                if (e.getType() != TableModelEvent.UPDATE) return;
                int col = e.getColumn();
                if (col != 3 && col != 4) return;
                int row = e.getFirstRow();
                try {
                    Object slObj = tableModel.getValueAt(row, 3);
                    Object giaObj = tableModel.getValueAt(row, 4);
                    if (slObj == null || giaObj == null) return;
                    int soLuong = Integer.parseInt(slObj.toString());
                    double gia = Double.parseDouble(giaObj.toString());
                    double tong = soLuong * gia;
                    Object old = tableModel.getValueAt(row, 5);
                    if (old == null || Double.compare(tong, Double.parseDouble(old.toString())) != 0) {
                        isUpdating = true;
                        tableModel.setValueAt(tong, row, 5);
                        isUpdating = false;
                    }
                    capNhatTongTien();
                } catch (Exception ex) {
                    // ignore format errors
                }
            }
        });
    }

    private void capNhatTongTien() {
        double sum = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object v = tableModel.getValueAt(i, 5);
            if (v != null) try { sum += Double.parseDouble(v.toString()); } catch (Exception ignored) {}
        }
        txtTongTien.setText(String.format("%,.0f", sum));
    }

    // ===== Getters/Setters =====
    public JTextField getTxtNguoiXuat() { return txtNguoiXuat; }
    public void setNguoiXuat(String ten) { txtNguoiXuat.setText(ten); }

    public JSpinner getSpNgayXuat() { return spNgayXuat; }
    public void setNgayXuat(Date d) { spNgayXuat.setValue(d); }
    public Date getNgayXuat() { return (Date) spNgayXuat.getValue(); }

    public JComboBox<String> getCboKhoXuat() { return cboKhoXuat; }
    public void setKhoXuatModel(ComboBoxModel<String> model) { cboKhoXuat.setModel(model); }
    public void setKhoXuatSelected(Object value) { cboKhoXuat.setSelectedItem(value); }

    public JTable getTblSanPham() { return tblSanPham; }
    public DefaultTableModel getTableModel() { return tableModel; }

    public JTextField getTxtTongTien() { return txtTongTien; }
    public void setTongTien(double money) { txtTongTien.setText(String.format("%,.0f", money)); }

    public JButton getBtnXuat() { return btnXuat; }
    public JButton getBtnHuy() { return btnHuy; }

    // ===== Demo để xem nhanh UI =====
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> {
            Export_Form f = new Export_Form();
            f.setNguoiXuat("Nguyễn Văn B");
            f.setKhoXuatModel(new DefaultComboBoxModel<>(new String[]{"Kho Tổng", "Kho Chi Nhánh 1", "Kho Chi Nhánh 2"}));
            f.setKhoXuatSelected("Kho Tổng");
            f.getTableModel().addRow(new Object[]{1, "Bún tươi", "kg", 5, 25000d, 125000d});
            f.getTableModel().addRow(new Object[]{2, "Chả", "kg", 3, 150000d, 450000d});
            f.getTableModel().addRow(new Object[]{3, "Rau sống", "bó", 10, 6000d, 60000d});
            f.setVisible(true);
        });
    }
}

