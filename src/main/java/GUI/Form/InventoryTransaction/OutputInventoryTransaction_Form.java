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

public class OutputInventoryTransaction_Form extends BaseInputOutputInventory<WareHouse> {

	public OutputInventoryTransaction_Form() {
        super();
        setTitle("Chọn kho xuất");
        lblTitle.setText("Chọn kho xuất");
        warehouseList.setCellRenderer(new WarehouseItemRender());
    }
}
