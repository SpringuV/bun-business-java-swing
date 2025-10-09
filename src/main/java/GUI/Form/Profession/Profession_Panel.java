package GUI.Form.Profession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import GUI.Component.CardButton;
import GUI.Form.Profession.OutputInput.InputInventoryTransaction_Panel;
import GUI.Form.Profession.OutputInput.OutputInventoryTransaction_Panel;
import GUI.Interface.IProfessionView;
import app.AppRouter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import model.WareHouse;
import utils.SimpleDocumentListener;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Profession_Panel extends JPanel implements IProfessionView {

	static final Color MAROON = new Color(120, 0, 0);
	static final Color LIGHT_BG = new Color(250, 250, 250);

	JLabel lbTitle;
	CardButton btnInputWarehouse;
	CardButton btnOutputWarehouse;
	CardButton btnInventory;

	CardLayout cardLayout;
	JPanel pnMainContent;
	OutputInventoryTransaction_Panel outputInventoryPanel;
	InputInventoryTransaction_Panel inputInventoryPanel;

	public Profession_Panel() {
		setLayout(new BorderLayout());
		setBackground(LIGHT_BG);

		initPanels();
		initHeader();
		initCardLayout();
		initMainMenu();
		initActions(); // chỉ cho menu, search gắn từ controller, mặc định
	}

	private void initHeader() {
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(MAROON);
		header.setBorder(new EmptyBorder(10, 16, 10, 16));

		lbTitle = new JLabel("Nghiệp vụ");
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setFont(lbTitle.getFont().deriveFont(Font.BOLD, 16f));

		header.add(lbTitle, BorderLayout.WEST);
		add(header, BorderLayout.NORTH);
	}

	private void initPanels() {
		outputInventoryPanel = AppRouter.goToOutputInventoryTransactionPanel();
		inputInventoryPanel = AppRouter.goToInputInventoryTransactionPanel();
	}

	private void initCardLayout() {
		cardLayout = new CardLayout();
		pnMainContent = new JPanel(cardLayout);

		pnMainContent.add(createMainMenuPanel(), "MAIN");
		pnMainContent.add(inputInventoryPanel, "INPUT");
		pnMainContent.add(outputInventoryPanel, "OUTPUT");
		pnMainContent.add(new JLabel("Trang kiểm kê đang phát triển...", SwingConstants.CENTER), "STATISTICAL");

		add(pnMainContent, BorderLayout.CENTER);
	}

	private JPanel createMainMenuPanel() {
		JPanel center = new JPanel(new GridBagLayout());
		center.setBackground(LIGHT_BG);
		center.setBorder(new EmptyBorder(24, 24, 24, 24));
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 12, 0, 12);

		btnInputWarehouse = new CardButton("Nhập kho", "\u21B0");
		btnOutputWarehouse = new CardButton("Xuất kho", "\u21B1");
		btnInventory = new CardButton("Kiểm kê", "\u25A6\u25A6");

		c.gridx = 0;
		center.add(btnInputWarehouse, c);
		c.gridx = 1;
		center.add(btnOutputWarehouse, c);
		c.gridx = 2;
		center.add(btnInventory, c);

		return center;
	}

	private void initMainMenu() {
		cardLayout.show(pnMainContent, "MAIN");
	}

	private void initActions() {
		// Menu actions gốc, controller có thể gắn thêm
		btnInputWarehouse.addActionListener(e -> showInputWarehousePanel());
		btnOutputWarehouse.addActionListener(e -> showOutputWarehousePanel());
		btnInventory.addActionListener(e -> showStatisticalPanel());
	}

	// =================== Implement IProfessionView ===================
	@Override
	public void showMainMenu() {
		cardLayout.show(pnMainContent, "MAIN");
	}

	@Override
	public void showInputWarehousePanel() {
		cardLayout.show(pnMainContent, "INPUT");
	}

	@Override
	public void showOutputWarehousePanel() {
		cardLayout.show(pnMainContent, "OUTPUT");
	}

	@Override
	public void showStatisticalPanel() {
		cardLayout.show(pnMainContent, "STATISTICAL");
	}

	@Override
	public String getInputSearchText() {
		return inputInventoryPanel.getSearchText();
	}

	@Override
	public String getOutputSearchText() {
		return outputInventoryPanel.getSearchText();
	}

	@Override
	public void setInputWarehouses(List<WareHouse> list) {
		inputInventoryPanel.setWarehouses(list);
	}

	@Override
	public void setOutputWarehouses(List<WareHouse> list) {
		outputInventoryPanel.setWarehouses(list);
	}

	@Override
	public void addInputWarehouseListener(ActionListener listener) {
		btnInputWarehouse.addActionListener(listener);
	}

	@Override
	public void addOutputWarehouseListener(ActionListener listener) {
		btnOutputWarehouse.addActionListener(listener);
	}

	@Override
	public void addInventoryListener(ActionListener listener) {
		btnInventory.addActionListener(listener);
	}

	@Override
	public void addInputSearchListener(Runnable listener) {
		inputInventoryPanel.addSearchListener(listener);
	}

	@Override
	public void addOutputSearchListener(Runnable listener) {
		outputInventoryPanel.addSearchListener(listener);
	}

	@Override
	public void setHeaderTitle(String title) {
		lbTitle.setText(title);
	}
}
