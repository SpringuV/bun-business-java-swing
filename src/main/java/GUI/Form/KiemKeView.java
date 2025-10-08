package GUI.Form;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class KiemKeView extends JPanel {
    private static final Color MAROON = Color.decode("#7A001E");
    private static final Color LIGHT_BG = new Color(250, 250, 250);
    private static final Color CARD_BG = Color.WHITE;

    private JLabel lbTitle;
    private JTable tblNguyenVatLieu;
    private DefaultTableModel tableModel;

    private JSpinner spTuNgay;
    private JSpinner spDenNgay;
    private JButton btnLoc;

    private JButton btnTrangChu;
    private JButton btnNghiepVu;
    private JButton btnBanHang;
    private JButton btnDanhMuc;
    private JButton btnTaiKhoan;

    public KiemKeView() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(LIGHT_BG);

        // ===== Header =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MAROON);
        header.setBorder(new EmptyBorder(10, 16, 10, 16));
        lbTitle = new JLabel("\u2192  Kiểm kê");
        lbTitle.setForeground(Color.WHITE);
        lbTitle.setFont(lbTitle.getFont().deriveFont(Font.BOLD, 16f));
        header.add(lbTitle, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // ===== Bộ lọc ngày =====
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
        filterPanel.setBackground(LIGHT_BG);

        JLabel lTuNgay = new JLabel("Từ ngày:");
        JLabel lDenNgay = new JLabel("Đến ngày:");

        spTuNgay = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        spTuNgay.setEditor(new JSpinner.DateEditor(spTuNgay, "dd/MM/yyyy"));
        spDenNgay = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        spDenNgay.setEditor(new JSpinner.DateEditor(spDenNgay, "dd/MM/yyyy"));

        btnLoc = new JButton("Lọc");
        btnLoc.setBackground(MAROON);
        btnLoc.setForeground(Color.RED);
        btnLoc.setFocusPainted(false);

        filterPanel.add(lTuNgay);
        filterPanel.add(spTuNgay);
        filterPanel.add(lDenNgay);
        filterPanel.add(spDenNgay);
        filterPanel.add(btnLoc);

        // ===== Bảng trung tâm =====
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(LIGHT_BG);
        center.setBorder(new EmptyBorder(10, 30, 20, 30));

        JLabel lTieuDe = new JLabel("Tổng chi nguyên vật liệu", SwingConstants.CENTER);
        lTieuDe.setFont(lTieuDe.getFont().deriveFont(Font.BOLD, 15f));
        lTieuDe.setBorder(new EmptyBorder(8, 0, 8, 0));

        String[] cols = {"STT", "Nhà cung cấp", "Địa chỉ", "Số tiền", "Điện thoại", "Ghi chú"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tblNguyenVatLieu = new JTable(tableModel);
        tblNguyenVatLieu.setRowHeight(26);
        tblNguyenVatLieu.setShowGrid(true);
        tblNguyenVatLieu.setGridColor(MAROON);
        tblNguyenVatLieu.getTableHeader().setReorderingAllowed(false);

        JScrollPane sp = new JScrollPane(tblNguyenVatLieu);
        sp.setBorder(new LineBorder(MAROON, 1, true));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(new CompoundBorder(new LineBorder(MAROON, 1, true), new EmptyBorder(10, 10, 10, 10)));
        card.add(lTieuDe, BorderLayout.NORTH);
        card.add(sp, BorderLayout.CENTER);

        center.add(filterPanel, BorderLayout.NORTH);
        center.add(card, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // ===== Nav bar bottom =====
        JPanel bottom = new JPanel(new GridLayout(1, 5));
        bottom.setBackground(MAROON);
        bottom.setBorder(new EmptyBorder(6, 8, 6, 8));

        btnTrangChu = createNavButton("Trang chủ", "\u2302");
        btnNghiepVu = createNavButton("Nghiệp vụ", "\u270E");
        btnBanHang  = createNavButton("Bán hàng",  "\uD83D\uDED2");
        btnDanhMuc  = createNavButton("Danh mục",  "\u25B3\u25BD");
        btnTaiKhoan = createNavButton("Tài khoản", "\u263A");

        bottom.add(btnTrangChu);
        bottom.add(btnNghiepVu);
        bottom.add(btnBanHang);
        bottom.add(btnDanhMuc);
        bottom.add(btnTaiKhoan);

        add(bottom, BorderLayout.SOUTH);
    }

    private JButton createNavButton(String text, String iconText) {
        JButton btn = new JButton(iconText + "\n" + text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ====== Getters cho Controller ======
    public JTable getTblNguyenVatLieu() { return tblNguyenVatLieu; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JLabel getLbTitle() { return lbTitle; }

    public JSpinner getSpTuNgay() { return spTuNgay; }
    public JSpinner getSpDenNgay() { return spDenNgay; }
    public JButton getBtnLoc() { return btnLoc; }

    public JButton getBtnTrangChu() { return btnTrangChu; }
    public JButton getBtnNghiepVu() { return btnNghiepVu; }
    public JButton getBtnBanHang() { return btnBanHang; }
    public JButton getBtnDanhMuc() { return btnDanhMuc; }
    public JButton getBtnTaiKhoan() { return btnTaiKhoan; }

    // ====== Demo ======
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Preview – Kiểm kê");
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.setContentPane(new KiemKeView());
            f.setSize(900, 550);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}