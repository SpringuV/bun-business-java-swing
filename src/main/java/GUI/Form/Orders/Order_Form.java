package GUI.Form.Orders;

import GUI.Component.RoundedPanel;
import GUI.Component.WrapLayout;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import model.Food;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order_Form extends JPanel {

    // === Các hằng số cho màu sắc và Font chữ ===
    static final Color COLOR_BACKGROUND = new Color(245, 245, 245);
    static final Color COLOR_PRIMARY = new Color(237, 125, 49); // Màu cam chủ đạo
    static final Color COLOR_TEXT_DARK = new Color(51, 51, 51);
    static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    static final Font FONT_CATEGORY = new Font("Segoe UI", Font.BOLD, 16);
    static final Font FONT_FOOD_NAME = new Font("Segoe UI", Font.BOLD, 14);
    static final Font FONT_FOOD_PRICE = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 16);

    // === Các thành phần giao diện ===
    JList<String> categoryList;
    DefaultListModel<String> categoryModel;
    JPanel foodPanel;
    JTable billTable;
    DefaultTableModel billModel;
    JLabel totalLabel;

    // === Dữ liệu ===
    final Map<String, List<Food>> menuData = new LinkedHashMap<>();

    public Order_Form() {
        // --- 1. Thiết lập Panel chính ---
        this.setLayout(new BorderLayout(15, 15));
        this.setBackground(COLOR_BACKGROUND);
        this.setBorder(new EmptyBorder(10, 15, 15, 15));

        // --- 2. Khởi tạo và sắp xếp các thành phần con ---
        JPanel pnTitle = new JPanel();
        pnTitle.setBackground(COLOR_BACKGROUND);
        JLabel title = new JLabel("Sơ đồ bàn quán bún bò Huế O Hà", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(109, 7, 7));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        pnTitle.add(title);
        JPanel categoryPanel = createCategoryPanel();
        JScrollPane menuPanel = createMenuPanel(); // JScrollPane chứa các món ăn
        JPanel billPanel = createBillPanel();

        this.add(pnTitle, BorderLayout.NORTH);
        this.add(categoryPanel, BorderLayout.WEST);
        this.add(menuPanel, BorderLayout.CENTER);
        this.add(billPanel, BorderLayout.EAST);

        // --- 3. Tải dữ liệu mẫu ---
//        initSampleData();
        populateCategories();
    }

    // === PHƯƠNG THỨC TẠO CÁC PANEL CON ===

    private JPanel createCategoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(220, 0));

        categoryModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryModel);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setFixedCellHeight(60);
        categoryList.setFont(FONT_CATEGORY);
        categoryList.setSelectionBackground(COLOR_PRIMARY);
        categoryList.setSelectionForeground(Color.WHITE);
        categoryList.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Xử lý sự kiện khi chọn một danh mục
        categoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCategory = categoryList.getSelectedValue();
                if (selectedCategory != null) {
                    loadFoods(selectedCategory);
                }
            }
        });

        panel.add(new JScrollPane(categoryList), BorderLayout.CENTER);
        return panel;
    }

    private JScrollPane createMenuPanel() {
        foodPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 15, 15));
        foodPanel.setBackground(COLOR_BACKGROUND);

        JScrollPane scrollPane = new JScrollPane(foodPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JPanel createBillPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setPreferredSize(new Dimension(380, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Tiêu đề
        JLabel billTitle = new JLabel("HÓA ĐƠN", SwingConstants.CENTER);
        billTitle.setFont(FONT_TITLE);
        billTitle.setForeground(COLOR_TEXT_DARK);
        panel.add(billTitle, BorderLayout.NORTH);

        // Bảng hóa đơn
        String[] columnNames = {"Món ăn", "SL", "Đơn giá", "Thành tiền"};
        billModel = new DefaultTableModel(columnNames, 0);
        billTable = new JTable(billModel);
        billTable.setRowHeight(30);
        billTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(new JScrollPane(billTable), BorderLayout.CENTER);

        // Phần dưới: tổng tiền và nút
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        totalLabel = new JLabel("Tổng: 0đ", SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLabel.setForeground(COLOR_PRIMARY);
        
        JButton btnPay = new JButton("THANH TOÁN");
        btnPay.setBackground(COLOR_PRIMARY);
        btnPay.setForeground(Color.WHITE);
        btnPay.setFont(FONT_BUTTON);
        btnPay.setPreferredSize(new Dimension(0, 50));

        bottomPanel.add(totalLabel, BorderLayout.NORTH);
        bottomPanel.add(btnPay, BorderLayout.CENTER);

        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    // === PHƯƠNG THỨC XỬ LÝ DỮ LIỆU ===
    private void populateCategories() {
        for (String category : menuData.keySet()) {
            categoryModel.addElement(category);
        }
        categoryList.setSelectedIndex(0); // Tự động chọn "Tất cả"
    }

    private void loadFoods(String category) {
        foodPanel.removeAll();
        List<Food> foods = menuData.get(category);
        if (foods == null) return;

        for (Food f : foods) {
            JPanel card = createFoodCard(f);
            foodPanel.add(card);
        }
        foodPanel.revalidate();
        foodPanel.repaint();
    }

    private JPanel createFoodCard(Food food) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setPreferredSize(new Dimension(160, 160));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Ảnh món ăn (thay thế sau)
        JLabel lblImage = new JLabel();
        // Giả sử bạn có icon, nếu không có thể dùng text
        // ImageIcon icon = new ImageIcon(getClass().getResource(food.imagePath));
        lblImage.setIcon(new ImageIcon(getClass().getResource("/Images/danhmuc.png"))); // 👈 THAY ĐỔI: Dùng ảnh placeholder
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblImage, BorderLayout.CENTER);

        // Panel chứa tên và giá
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JLabel lblName = new JLabel("<html><center>" + food.getName_food() + "</center></html>");
        lblName.setFont(FONT_FOOD_NAME);
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblPrice = new JLabel(getFormattedPrice(food.getPrices()));
        lblPrice.setFont(FONT_FOOD_PRICE);
        lblPrice.setForeground(COLOR_PRIMARY);
        lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
        
        infoPanel.add(lblName);
        infoPanel.add(lblPrice);
        card.add(infoPanel, BorderLayout.SOUTH);
        
        // Hiệu ứng hover
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY, 2));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
            }
        });
        
        return card;
    }
    
    private String getFormattedPrice(double prices) {
        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(prices);
    }
}