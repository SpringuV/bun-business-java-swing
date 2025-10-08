package GUI.Component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import model.WareHouse;

public class WarehouseItemRender extends JPanel implements ListCellRenderer<WareHouse>{
	private static final Color CARD_DIVIDER = new Color(245, 245, 245);
    private static final Color TEXT_MUTED = new Color(90, 90, 90);

    private final JLabel lbNameWareHouse = new JLabel();
    private final JLabel lbAddressWarehouse = new JLabel();
    
    public WarehouseItemRender() {
    	setLayout(new BorderLayout());

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBorder(new EmptyBorder(12, 12, 12, 12));

        lbNameWareHouse.setFont(lbNameWareHouse.getFont().deriveFont(Font.BOLD, 14f));
        lbAddressWarehouse.setFont(lbAddressWarehouse.getFont().deriveFont(Font.PLAIN, 12f));
        lbAddressWarehouse.setForeground(TEXT_MUTED);

        box.add(lbNameWareHouse);
        box.add(Box.createVerticalStrut(4));
        box.add(lbAddressWarehouse);
        add(box, BorderLayout.CENTER);

        setBorder(BorderFactory.createMatteBorder(0, 0, 8, 0, CARD_DIVIDER));
    }

	@Override
	public Component getListCellRendererComponent(JList<? extends WareHouse> list, WareHouse value, int index,
			boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		lbNameWareHouse.setText(value.getName());
		lbAddressWarehouse.setText(value.getAddress());
        setOpaque(true);
        setBackground(isSelected ? new Color(230, 243, 255) : Color.WHITE);
        return this;
	}
}
