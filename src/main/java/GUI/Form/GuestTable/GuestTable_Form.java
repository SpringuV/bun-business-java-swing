/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Form.GuestTable;

import GUI.Component.RoundedPanel;
import app.AppContext;
import controller.GuestTable_Controller;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import model.GuestTable;
import repository.interfaceRepo.GuestTableRepository;

//import controller.GuestTable_Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Admin
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class GuestTable_Form extends JFrame {
	List<RoundedPanel> tableList = new ArrayList<>();// lưu toàn bộ các bàn tạo ở vòng for
	JMenuBar menuBarr;
	JMenu menu;
	JMenuItem menuQLSanPham, menuQLNhapXuatKho, menuThongKeBaoCao, menuQuanLyKho, menuDatBan, menuThemBan;
	JPanel mainPannel;
	GuestTableRepository guestTableRepository;
	GuestTable_Controller guestTable_Controller;

	/**
	 * Tạo form với repository và controller đầy đủ
	 * 
	 * @return GuestTable_Form instance
	 */
	public static GuestTable_Form create(GuestTableRepository repository, GuestTable_Controller controller) {
		GuestTable_Form form = new GuestTable_Form();
		form.guestTableRepository = repository;
		form.guestTable_Controller = controller;
		form.init();
		return form;
	}

	/**
	 * Tạo form chỉ với repository (controller sẽ được tạo tự động)
	 * 
	 * @return GuestTable_Form instance
	 */
	public static GuestTable_Form create(GuestTableRepository repository) {
		GuestTable_Form form = new GuestTable_Form();
		form.guestTableRepository = repository;
		form.init();
		// Controller sẽ được inject sau thông qua setController()
		return form;
	}

	/**
	 * Tạo form mặc định (dùng cho testing hoặc khi có dependency injection từ
	 * ngoài)
	 * 
	 * @return GuestTable_Form instance
	 */
	public static GuestTable_Form create() {
		GuestTable_Form form = new GuestTable_Form();
		form.init();
		return form;
	}

	// ============== PRIVATE CONSTRUCTOR ==============

	// Constructor private để bắt buộc dùng factory methods
	private GuestTable_Form() {
		// Empty constructor
	}

	// ============== INITIALIZATION ==============
	private void init() {
        setTitle("Đặt bàn");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPannel = new JPanel();
        mainPannel.setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        setupMenuBar();
        setupTitle();
        setupTableGrid();
        
        add(mainPannel);
    }
    
    private void setupMenuBar() {
        menuBarr = new JMenuBar();
        menu = new JMenu("Giao diện");
        menuDatBan = new JMenuItem("Đặt bàn");
        menuQLSanPham = new JMenuItem("Quản lý sản phẩm");
        menuQLNhapXuatKho = new JMenuItem("Quản lý Nhập/xuất");
        menuQuanLyKho = new JMenuItem("Quản lý kho");
        menuThongKeBaoCao = new JMenuItem("Thông kê và báo cáo");
        menuThemBan = new JMenuItem("Thêm bàn mới");
        
        menu.add(menuThemBan);
        menu.add(menuDatBan);
        menu.add(menuQLSanPham);
        menu.add(menuQLNhapXuatKho);
        menu.add(menuQuanLyKho);
        menu.add(menuThongKeBaoCao);
        menuBarr.add(menu);
        setJMenuBar(menuBarr);
        
        // Event handlers
        menuThemBan.addActionListener(e -> {
            if (guestTableRepository != null && guestTable_Controller != null) {
                new AddGuestTable_Form(guestTableRepository, this::refreshTableList, guestTable_Controller)
                        .setVisible(true);
            }
        });
    }
    
    private void setupTitle() {
        JLabel title = new JLabel("Sơ đồ bàn quán bún bò Huế O Hà", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(109, 7, 7));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPannel.add(title, BorderLayout.NORTH);
    }
    
    private void setupTableGrid() {
        JPanel tableGrid = new JPanel(new GridLayout(3, 4, 20, 20));
        tableGrid.setBackground(new Color(245, 245, 245));
        tableGrid.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        mainPannel.add(tableGrid, BorderLayout.CENTER);
    }

    // ============== BUSINESS LOGIC ==============
    
    public void displayTables(List<GuestTable> tables) {
        JPanel tableGrid = new JPanel(new GridLayout(3, 4, 20, 20));
        tableGrid.setBackground(new Color(245, 245, 245));
        tableGrid.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        tableList.clear();

        for (GuestTable t : tables) {
            RoundedPanel panel = createTablePanel(t);
            tableList.add(panel);
            tableGrid.add(panel);
        }

        mainPannel.removeAll();
        setupTitle();
        mainPannel.add(tableGrid, BorderLayout.CENTER);
        mainPannel.revalidate();
        mainPannel.repaint();
    }
    
    private RoundedPanel createTablePanel(GuestTable table) {
        boolean available = table.is_available();
        Color color = available ? new Color(76, 175, 80) : new Color(229, 57, 53);

        RoundedPanel panel = new RoundedPanel(new BorderLayout(), 25, color);
        panel.setPreferredSize(new Dimension(150, 120));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblIcon = new JLabel("🍜", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        panel.add(lblIcon, BorderLayout.CENTER);

        JLabel lblName = new JLabel("Bàn " + table.getId_table(), SwingConstants.CENTER);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblName.setForeground(Color.WHITE);
        panel.add(lblName, BorderLayout.NORTH);

        JLabel lblStatus = new JLabel(available ? "Trống" : "Đang phục vụ", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblStatus.setForeground(Color.WHITE);
        panel.add(lblStatus, BorderLayout.SOUTH);

        panel.putClientProperty("baseColor", color);
        panel.putClientProperty("guestTable", table);
        
        return panel;
    }

    private void refreshTableList() {
        if (guestTable_Controller != null) {
            List<GuestTable> tables = guestTable_Controller.getAllGuestTables();
            displayTables(tables);
        }
    }
    
    // ============== SETTERS (cho dependency injection) ==============
    
    public void setGuestTableRepository(GuestTableRepository repository) {
        this.guestTableRepository = repository;
    }
    
    public void setGuestTable_Controller(GuestTable_Controller controller) {
        this.guestTable_Controller = controller;
    }

}
