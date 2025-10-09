package GUI.Interface;

import java.awt.event.ActionListener;
import java.util.List;
import model.WareHouse;

public interface IProfessionView {
    // Hiển thị các trang chính
    void showMainMenu();
    void showInputWarehousePanel();
    void showOutputWarehousePanel();
    void showStatisticalPanel();

    // Lấy/Set dữ liệu (controller thao tác qua interface, không biết nội bộ view)
    String getInputSearchText();
    String getOutputSearchText();
    void setInputWarehouses(List<WareHouse> list);
    void setOutputWarehouses(List<WareHouse> list);

    // Gắn sự kiện menu
    void addInputWarehouseListener(ActionListener listener);
    void addOutputWarehouseListener(ActionListener listener);
    void addInventoryListener(ActionListener listener);

    // Gắn sự kiện search (controller không cần biết txtSearch là gì)
    void addInputSearchListener(Runnable listener);
    void addOutputSearchListener(Runnable listener);

    // Cập nhật tiêu đề header
    void setHeaderTitle(String title);
}
