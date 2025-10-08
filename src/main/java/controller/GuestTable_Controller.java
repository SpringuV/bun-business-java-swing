/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;

import GUI.Component.RoundedPanel;
import GUI.Interface.IGuestTableView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import model.GuestTable;
import model.GuestTable.TypeTable;
import service.GuestTableService;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class GuestTable_Controller {
	final GuestTableService service;
	IGuestTableView view;

	public GuestTable_Controller(GuestTableService service, IGuestTableView view) {
        this.service = service;
        this.view = view;
    }

	
	public void loadTables() {
        List<GuestTable> tables = service.getAllTables();
        view.displayTables(tables);
    }

    public void onAddTableClicked() {
        view.openAddTableForm();
    }

    public void onAddTable(TypeTable type, boolean available) {
        boolean success = service.addTable(type, available);
        if (success) view.showMessage("Thêm bàn thành công!");
        else view.showMessage("Không thể thêm bàn!");
        loadTables();
    }
    
    private void handleTableClick(GuestTable table) {
        String message = String.format("Bàn: %s\nTrạng thái: %s\n\nBạn có muốn mở order?",
            table.getId_table(),
            table.is_available() ? "Trống" : "Đang phục vụ");

        int result = JOptionPane.showConfirmDialog(null, message, "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            view.openOrderForm(table);
        }
    }


    private void setupListeners() {
        view.addMenuDatBanListener(e -> view.openAddTableForm());

        view.addTablePanelMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
                RoundedPanel panel = (RoundedPanel) e.getSource();
                GuestTable table = (GuestTable) panel.getClientProperty("guestTable");
                handleTableClick(table);
            }
		});
    }
    
    public void onTableClicked(GuestTable table) {
        view.openOrderForm(table);
    }


	public void setView(IGuestTableView view) {
		this.view = view;
	}
    
}
