package GUI.Form.InventoryTransaction;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

/**
 * Base class cho các form chọn kho (nhập / xuất)
 * Cung cấp:
 *  - Header (màu đỏ mận + nút Back + tiêu đề)
 *  - Ô tìm kiếm
 *  - Danh sách kho (JList)
 *  - Nút Tiếp tục
 *  - Thanh navigation dưới cùng
 *
 * Các lớp con (InputInventoryTransaction_Form / OutputInventoryTransaction_Form)
 * chỉ cần:
 *  - setTitle() & lblTitle.setText()
 *  - gọi setWarehouses()
 *  - gán cellRenderer phù hợp
 *  
 *  
 *   * Form "Chọn kho xuất" — phiên bản đơn giản theo ảnh mẫu + nút Tiếp tục
 *  - Header nền đỏ mận + nút Back + tiêu đề
 *  - Ô tìm kiếm "Nhập tên, mã kho"
 *  - Danh sách kho dạng thẻ (tên đậm, địa chỉ mờ)
 *  - Nút **Tiếp tục** căn phải phía trên thanh điều hướng
 *  - Thanh điều hướng dưới (đỏ mận)
 * UI only; cung cấp getter để Controller gắn logic chọn kho mở form chi tiết sau.
 */
public abstract class BaseInputOutputInventory<T> extends JFrame {
	// ===== Theme =====
    protected static final Color PRIMARY = new Color(128, 0, 0); // đỏ mận
    protected static final Color CARD_DIVIDER = new Color(245, 245, 245);
    protected static final Color TEXT_MUTED = new Color(90, 90, 90);

    // ===== Header =====
    protected final JButton btnBack = new JButton("←");
    protected final JLabel lblTitle = new JLabel();

    // ===== Search + List =====
    protected final JTextField txtSearch = new JTextField();
    protected final DefaultListModel<T> listModel = new DefaultListModel<>();
    protected final JList<T> warehouseList = new JList<>(listModel);

    // ===== Action =====
    protected final JButton btnContinue = new JButton("Tiếp tục");

    // ===== Bottom navigation =====
    protected final JButton navHome = new JButton("Trang chủ");
    protected final JButton navOps  = new JButton("Nghiệp vụ");
    protected final JButton navSale = new JButton("Bán hàng");
    protected final JButton navCat  = new JButton("Danh mục");
    protected final JButton navUser = new JButton("Tài khoản");

    public BaseInputOutputInventory() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(920, 560);
        setLocationRelativeTo(null);
        initUI();
    }

    /** Cấu trúc UI chung */
    private void initUI() {
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

        // ===== Center (search + list + button) =====
        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBorder(new EmptyBorder(10, 12, 10, 12));

        // Search box
        JPanel searchWrap = new JPanel(new BorderLayout());
        searchWrap.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220)),
                new EmptyBorder(8, 10, 8, 10)
        ));
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

        root.add(center, BorderLayout.CENTER);

        // ===== Bottom Navigation =====
        JPanel bottom = new JPanel(new GridLayout(1, 5));
        bottom.setBackground(PRIMARY);
        bottom.setBorder(new EmptyBorder(6, 6, 6, 6));
        for (JButton b : new JButton[]{navHome, navOps, navSale, navCat, navUser}) {
            styleNavButton(b);
            bottom.add(b);
        }
        root.add(bottom, BorderLayout.SOUTH);
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

    /** Gán danh sách kho */
    public void setWarehouses(List<T> list) {
        listModel.clear();
        for (T item : list) {
            listModel.addElement(item);
        }
    }
}
