package GUI.Form.Profession.OutputInput;

import javax.swing.*;
import javax.swing.border.*;

import GUI.Component.WarehouseItemRender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import model.WareHouse;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class InputInventoryTransaction_Panel extends BaseInputOutputInventory<WareHouse> {

	public InputInventoryTransaction_Panel() {
		super();
		lblTitle.setText("Chọn kho nhập");
		warehouseList.setCellRenderer(new WarehouseItemRender());
	}

	@Override
	protected void onContinue() {
		// TODO Auto-generated method stub
		WareHouse selected = getSelectedWarehouse();
		if (selected == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn kho để tiếp tục!");
			return;
		}
		// TODO: gửi dữ liệu cho controller xử lý tiếp
		// 💡 Ví dụ: lưu kho được chọn hoặc chuyển bước tiếp theo
		System.out.println("Kho được chọn để xuất: " + selected.getName_warehouse());

	}

	// Bạn vẫn có thể override hoặc thêm logic riêng nếu muốn
}
