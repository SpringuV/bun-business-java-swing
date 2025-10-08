package GUI.Form.InventoryTransaction;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Form "Chọn kho xuất" — phiên bản đơn giản theo ảnh mẫu + nút Tiếp tục
 *  - Header nền đỏ mận + nút Back + tiêu đề
 *  - Ô tìm kiếm "Nhập tên, mã kho"
 *  - Danh sách kho dạng thẻ (tên đậm, địa chỉ mờ)
 *  - Nút **Tiếp tục** căn phải phía trên thanh điều hướng
 *  - Thanh điều hướng dưới (đỏ mận)
 * UI only; cung cấp getter để Controller gắn logic chọn kho mở form chi tiết sau.
 */
public class InputInventoryTransaction_Form extends JFrame {

    // ===== Theme =====
    private static final Color PRIMARY = new Color(128, 0, 0); // đỏ mận
    private static final Color CARD_DIVIDER = new Color(245,245,245);
    private static final Color TEXT_MUTED = new Color(90,90,90);

    // Header
    private final JButton btnBack = new JButton("←");
    private final JLabel lblTitle = new JLabel("Chọn kho nhập");

    // Search + List
    private final JTextField txtSearch = new JTextField();
    private final DefaultListModel<Kho> listModel = new DefaultListModel<>();
    private final JList<Kho> lstKho = new JList<>(listModel);

    // Action
    private final JButton btnTiepTuc = new JButton("Tiếp tục");

    // Bottom navigation
    private final JButton navHome = new JButton("Trang chủ");
    private final JButton navOps  = new JButton("Nghiệp vụ");
    private final JButton navSale = new JButton("Bán hàng");
    private final JButton navCat  = new JButton("Danh mục");
    private final JButton navUser = new JButton("Tài khoản");

    public InputInventoryTransaction_Form() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Chọn kho nhập");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(920, 560); // theo yêu cầu

        JPanel root = new JPanel(new BorderLayout());
        setContentPane(root);

        // ===== Header =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY);
        header.setBorder(new EmptyBorder(10, 12, 10, 12));
        styleHeaderButton(btnBack);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 16f));
        header.add(btnBack, BorderLayout.WEST);
        header.add(lblTitle, BorderLayout.CENTER);
        root.add(header, BorderLayout.NORTH);

        // ===== Center: search + list + actions =====
        JPanel center = new JPanel(new BorderLayout(10,10));
        center.setBorder(new EmptyBorder(10, 12, 10, 12));

        // search
        JPanel searchWrap = new JPanel(new BorderLayout());
        searchWrap.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(220,220,220)), new EmptyBorder(8,10,8,10)));
        JLabel icon = new JLabel("🔎  ");
        txtSearch.putClientProperty("JTextField.placeholderText", "Nhập tên, mã kho");
        txtSearch.setBorder(null);
        searchWrap.add(icon, BorderLayout.WEST);
        searchWrap.add(txtSearch, BorderLayout.CENTER);
        center.add(searchWrap, BorderLayout.NORTH);

        // list
        lstKho.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstKho.setFixedCellHeight(78);
        lstKho.setCellRenderer(new KhoItemRenderer());
        JScrollPane sp = new JScrollPane(lstKho);
        sp.setBorder(new EmptyBorder(0,0,0,0));
        center.add(sp, BorderLayout.CENTER);

        // actions (Tiếp tục)
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(btnTiepTuc);
        center.add(actions, BorderLayout.SOUTH);

        root.add(center, BorderLayout.CENTER);

        // ===== Bottom Navigation =====
        JPanel bottom = new JPanel(new GridLayout(1,5));
        bottom.setBackground(PRIMARY);
        bottom.setBorder(new EmptyBorder(6,6,6,6));
        for (JButton b : new JButton[]{navHome, navOps, navSale, navCat, navUser}) {
            styleNavButton(b);
            bottom.add(b);
        }
        root.add(bottom, BorderLayout.SOUTH);
    }

    private void styleHeaderButton(AbstractButton b) {
        b.setForeground(Color.WHITE);
        b.setBackground(PRIMARY);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 16f));
    }

    private void styleNavButton(AbstractButton b) {
        b.setForeground(Color.WHITE);
        b.setBackground(PRIMARY);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFont(b.getFont().deriveFont(Font.PLAIN, 12f));
    }

    // ===== Public API =====
    public JTextField getTxtSearch() { return txtSearch; }
    public JList<Kho> getLstKho() { return lstKho; }
    public DefaultListModel<Kho> getListModel() { return listModel; }
    public JButton getBtnBack() { return btnBack; }
    public JButton getBtnTiepTuc() { return btnTiepTuc; }
    public JButton getNavHome() { return navHome; }
    public JButton getNavOps()  { return navOps; }
    public JButton getNavSale() { return navSale; }
    public JButton getNavCat()  { return navCat; }
    public JButton getNavUser() { return navUser; }

    public void setWarehouses(List<Kho> ds) {
        listModel.clear();
        for (Kho k : ds) listModel.addElement(k);
    }

    // ===== Demo =====
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> {
            InputInventoryTransaction_Form f = new InputInventoryTransaction_Form();
            List<Kho> demo = new ArrayList<>();
            demo.add(new Kho("Bún Bò Huế O Hà - Điểm Võ Chí Công", "Địa chỉ: 86 Võ Chí Công, Nghĩa Đô, Cầu Giấy, Hà Nội."));
            demo.add(new Kho("Bún Bò Huế O Hà - Điểm Xuân La", "Địa chỉ: 107 D. Xuân La, Xuân La, Tây Hồ, Hà Nội."));
            f.setWarehouses(demo);
            f.setVisible(true);
        });
    }

    // ===== Model & Renderer =====
    public static class Kho {
        public final String ten; public final String diaChi;
        public Kho(String ten, String diaChi) { this.ten = ten; this.diaChi = diaChi; }
        @Override public String toString() { return ten; }
    }

    static class KhoItemRenderer extends JPanel implements ListCellRenderer<Kho> {
        private final JLabel lbTen = new JLabel();
        private final JLabel lbDiaChi = new JLabel();
        KhoItemRenderer() {
            setLayout(new BorderLayout());
            JPanel box = new JPanel();
            box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
            box.setBorder(new EmptyBorder(12, 12, 12, 12));
            lbTen.setFont(lbTen.getFont().deriveFont(Font.BOLD, 14f));
            lbDiaChi.setFont(lbDiaChi.getFont().deriveFont(Font.PLAIN, 12f));
            lbDiaChi.setForeground(TEXT_MUTED);
            box.add(lbTen);
            box.add(Box.createVerticalStrut(4));
            box.add(lbDiaChi);
            add(box, BorderLayout.CENTER);
            setBorder(BorderFactory.createMatteBorder(0, 0, 8, 0, CARD_DIVIDER));
        }
        @Override
        public Component getListCellRendererComponent(JList<? extends Kho> list, Kho value, int index, boolean isSelected, boolean cellHasFocus) {
            lbTen.setText(value.ten);
            lbDiaChi.setText(value.diaChi);
            setOpaque(true);
            setBackground(isSelected ? new Color(230,243,255) : Color.WHITE);
            return this;
        }
    }
}
