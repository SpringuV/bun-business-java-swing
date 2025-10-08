/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import GUI.Component.RoundedPanel;
import GUI.Form.GuestTable_Form;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import repository.interfaceRepo.GuestTableRepository;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuestTable_Controller {
	GuestTable_Form dBan_Form;
	GuestTableRepository guestTableRepository;

	public GuestTable_Controller(GuestTable_Form dBan_Form, GuestTableRepository guestTableRepository) {
		this.dBan_Form = dBan_Form;
		this.guestTableRepository = guestTableRepository;
		listenActioner();
	}

	private void listenActioner() {
//Menu------------------------------------------------------------------------
		dBan_Form.getMenuDatBan().addActionListener((e) -> {
			dBan_Form.getContentPane().removeAll();
			dBan_Form.add(dBan_Form.getMainPannel());
			dBan_Form.setJMenuBar(dBan_Form.getMenuBarr());
			dBan_Form.revalidate();
			dBan_Form.repaint();
		});

//---------------------------sự kiện của các thẻ bàn---------------------------
		for (RoundedPanel panel : dBan_Form.getTableList()) {
			panel.addMouseListener(new MouseAdapter() {
				// hover-------------------------
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
				// end hover-----------------------

				// sự kiện bấm vào thẻ bàn để mở menu
				@Override
				public void mouseClicked(MouseEvent e) {
					JLabel lblName = (JLabel) ((RoundedPanel) e.getSource()).getComponent(1);
					String tableName = lblName.getText();
					JOptionPane.showMessageDialog(null, "Mở Order cho " + tableName);
				}
			});
		}
	}

}
