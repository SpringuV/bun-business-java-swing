/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import GUI.Component.RoundedPanel;
import GUI.Form.GuestTable.GuestTable_Form;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import model.GuestTable;
import repository.interfaceRepo.GuestTableRepository;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class GuestTable_Controller {
	GuestTable_Form dBan_Form;
	GuestTableRepository guestTableRepository;

	// ============== STATIC FACTORY METHODS ==============

	/**
	 * Tạo controller với form và repository đầy đủ Khuyến nghị dùng method này
	 */
	public static GuestTable_Controller create(GuestTable_Form form, GuestTableRepository repository) {
		GuestTable_Controller controller = new GuestTable_Controller();
		controller.dBan_Form = form;
		controller.guestTableRepository = repository;
		controller.initialize();
		return controller;
	}

	/**
	 * Tạo controller chỉ với repository (form sẽ inject sau) Dùng khi muốn tạo
	 * controller trước, form sau
	 */
	public static GuestTable_Controller create(GuestTableRepository repository) {
		GuestTable_Controller controller = new GuestTable_Controller();
		controller.guestTableRepository = repository;
		return controller;
	}

	/**
	 * Tạo controller mặc định (không có dependencies) Dùng cho testing hoặc khi
	 * dependencies sẽ được inject sau
	 */
	public static GuestTable_Controller create() {
		return new GuestTable_Controller();
	}

	/**
	 * Factory method tiện lợi: Tạo cả Form và Controller cùng lúc
	 * 
	 * @param repository GuestTableRepository
	 * @return GuestTable_Form đã được wire với controller
	 */
	public static GuestTable_Form createFormWithController(GuestTableRepository repository) {
		// Tạo form trước
		GuestTable_Form form = GuestTable_Form.create(repository);

		// Tạo controller và wire vào form
		GuestTable_Controller controller = GuestTable_Controller.create(form, repository);

		// Inject controller vào form
		form.setGuestTable_Controller(controller);
		// ✅ Load data khi khởi tạo form
	    controller.initializeAfterInjection();
		return form;
	}

	// ============== PRIVATE CONSTRUCTOR ==============

	private GuestTable_Controller() {
		// Empty constructor - force sử dụng factory methods
	}

	// ============== INITIALIZATION ==============

	/**
	 * Khởi tạo controller: load data và setup listeners Chỉ gọi khi đã có đủ form
	 * và repository
	 */
	private void initialize() {
		if (dBan_Form != null && guestTableRepository != null) {
			List<GuestTable> table_list = getAllGuestTables();
			dBan_Form.displayTables(table_list);
	        listenActioner();
	    } else {
	        throw new IllegalStateException("Form hoặc Repository chưa được khởi tạo!");
	    }
	}

	/**
	 * Initialize sau khi inject dependencies Gọi method này nếu dùng create() rỗng
	 * rồi inject sau
	 */
	public void initializeAfterInjection() {
		initialize();
	}

	// ============== BUSINESS LOGIC ==============

	public List<GuestTable> getAllGuestTables() {
		try {
			return guestTableRepository.getListTable();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách bàn!");
			return List.of();
		}
	}

	// ============== EVENT LISTENERS ==============

	private void listenActioner() {
		setupMenuListeners();
		setupTablePanelListeners();
	}

	private void setupMenuListeners() {
		// Menu Đặt bàn
		dBan_Form.getMenuDatBan().addActionListener((e) -> {
			dBan_Form.getContentPane().removeAll();
			dBan_Form.add(dBan_Form.getMainPannel());
			dBan_Form.setJMenuBar(dBan_Form.getMenuBarr());
			dBan_Form.revalidate();
			dBan_Form.repaint();
		});

		// Có thể thêm các menu listener khác ở đây
	}

	private void setupTablePanelListeners() {
		for (RoundedPanel panel : dBan_Form.getTableList()) {
			panel.addMouseListener(new MouseAdapter() {

				// Hover effect
				@Override
				public void mouseEntered(MouseEvent e) {
					RoundedPanel p = (RoundedPanel) e.getSource();
					Color base = (Color) p.getClientProperty("baseColor");
					p.setPanelColor(base.darker());
				}

				@Override
				public void mouseExited(MouseEvent e) {
					RoundedPanel p = (RoundedPanel) e.getSource();
					Color base = (Color) p.getClientProperty("baseColor");
					p.setPanelColor(base);
				}

				// Click event
				@Override
				public void mouseClicked(MouseEvent e) {
					RoundedPanel clickedPanel = (RoundedPanel) e.getSource();
					GuestTable table = (GuestTable) clickedPanel.getClientProperty("guestTable");

					if (table != null) {
						handleTableClick(table);
					} else {
						// Fallback to old method
						JLabel lblName = (JLabel) clickedPanel.getComponent(1);
						String tableName = lblName.getText();
						JOptionPane.showMessageDialog(null, "Mở Order cho " + tableName);
					}
				}
			});
		}
	}

	private void handleTableClick(GuestTable table) {
		String message = String.format("Bàn: %s\nTrạng thái: %s\n\nBạn có muốn mở order?", table.getId_table(),
				table.is_available() ? "Trống" : "Đang phục vụ");

		int result = JOptionPane.showConfirmDialog(null, message, "Xác nhận", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			// TODO: Mở form order
			JOptionPane.showMessageDialog(null, "Mở Order cho bàn " + table.getId_table());
		}
	}

	// ============== SETTERS (cho dependency injection) ==============

	public void setDban_Form(GuestTable_Form form) {
		this.dBan_Form = form;
	}

	public void setGuestTableRepository(GuestTableRepository repository) {
		this.guestTableRepository = repository;
	}
}
