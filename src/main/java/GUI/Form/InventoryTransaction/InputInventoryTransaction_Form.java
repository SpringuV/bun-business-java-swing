package GUI.Form.InventoryTransaction;

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

public class InputInventoryTransaction_Form extends BaseInputOutputInventory<WareHouse> {

	public InputInventoryTransaction_Form() {
        super();
        setTitle("Chọn kho nhập");
        lblTitle.setText("Chọn kho nhập");
        warehouseList.setCellRenderer(new WarehouseItemRender());
    }

    // Bạn vẫn có thể override hoặc thêm logic riêng nếu muốn
}
