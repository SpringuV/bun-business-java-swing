package GUI.Form.GuestTable;

import controller.GuestTable_Controller;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import model.GuestTable.TypeTable;
import repository.interfaceRepo.GuestTableRepository;

import javax.swing.*;
import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddGuestTable_Form extends JFrame {
    JComboBox<String> cbType;
    JCheckBox chkAvailable;
    JButton btnSave, btnCancel;

    GuestTable_Controller guestTableController;
    Runnable onSuccessCallback; // ✅ callback để refresh form chính
    GuestTableRepository guestTableRepository;
    public AddGuestTable_Form(GuestTableRepository guestTableRepository,Runnable onSuccessCallback, GuestTable_Controller guestTableController) {
        this.onSuccessCallback = onSuccessCallback;
        this.guestTableRepository = guestTableRepository;
        this.guestTableController = guestTableController;


        setTitle("Thêm bàn mới");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("Thêm bàn mới", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(109, 7, 7));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblType = new JLabel("Loại bàn:");
        cbType = new JComboBox<>(new String[]{"NORMAL", "VIP"});

        JLabel lblAvailable = new JLabel("Trạng thái:");
        chkAvailable = new JCheckBox("Trống", true);

        formPanel.add(lblType);
        formPanel.add(cbType);
        formPanel.add(lblAvailable);
        formPanel.add(chkAvailable);
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnSave = new JButton("Lưu bàn");
        btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> saveTable());
        btnCancel.addActionListener(e -> dispose());
    }

    private void saveTable() {
        try {
            String typeStr = (String) cbType.getSelectedItem();
            boolean isAvailable = chkAvailable.isSelected();
            // create table to db
            boolean success = guestTableRepository.createGuestTable(TypeTable.valueOf(typeStr), isAvailable);

            if (success) {
            	JOptionPane.showMessageDialog(this, "Thêm bàn thành công!");
                if (onSuccessCallback != null) onSuccessCallback.run(); // ✅ refresh danh sách bàn
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thêm bàn!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}