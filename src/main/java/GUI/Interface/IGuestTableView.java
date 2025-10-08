package GUI.Interface;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;

import model.GuestTable;

public interface IGuestTableView {
	void displayTables(List<GuestTable> tables); // render bàn

	void showMessage(String message); // thông báo

	void openAddTableForm(); // mở form thêm bàn

	void openOrderForm(GuestTable table); // mở form order cho bàn
	
	// Cho phép Controller gắn sự kiện vào View
	void addMenuDatBanListener(ActionListener listener);

	void addTablePanelMouseListener(MouseAdapter listener);

}
