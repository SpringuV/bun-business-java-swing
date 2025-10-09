package controller;

import java.util.List;
import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import GUI.Interface.IProfessionView;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import model.WareHouse;
import service.WarehouseService;
import utils.SimpleDocumentListener;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfessionController {
    static Logger logger = LogManager.getLogger(ProfessionController.class);
    WarehouseService service;
    IProfessionView view;

    public ProfessionController(WarehouseService service, IProfessionView view) {
        this.service = service;
        this.view = view;
    }

    public void init() throws Exception {
        loadWarehouses();
        setupSearch();
        setupMenuActions();
    }

    // Load dữ liệu kho và đẩy vào view
    private void loadWarehouses() throws Exception {
        List<WareHouse> list = service.getAllWarehouses();
        view.setInputWarehouses(list);
        view.setOutputWarehouses(list);
    }

    // Thiết lập tìm kiếm với debounce
    private void setupSearch() {
        setupDebounceSearch(() -> view.getInputSearchText(), view::setInputWarehouses);
        setupDebounceSearch(() -> view.getOutputSearchText(), view::setOutputWarehouses);
    }

    private void setupDebounceSearch(SearchTextGetter getter, WareHouseListSetter setter) {
        Timer debounceTimer = new Timer(1500, e -> {
            try {
                String keyword = getter.getText();
                List<WareHouse> list = service.getAllWarehouses();
                String key = keyword == null ? "" : keyword.trim().toLowerCase();
                List<WareHouse> filtered = list.stream()
                        .filter(w -> w.getName_warehouse().toLowerCase().contains(key)
                                || w.getCode_warehouse().toLowerCase().contains(key))
                        .toList();
                setter.setWareHouses(filtered);
            } catch (Exception ex) {
                logger.error("Lỗi filterWarehouses: {}", ex.getMessage(), ex);
            }
        });
        debounceTimer.setRepeats(false);

        // Giả lập SimpleDocumentListener gắn vào getter (có thể bind từ view)
        getter.addListener(() -> debounceTimer.restart());
    }

    // Menu actions
    private void setupMenuActions() {
        view.addInputWarehouseListener(e -> view.showInputWarehousePanel());
        view.addOutputWarehouseListener(e -> view.showOutputWarehousePanel());
        view.addInventoryListener(e -> view.showStatisticalPanel());
    }

    // Functional interfaces để tách dữ liệu và setter
    @FunctionalInterface
    private interface SearchTextGetter {
        String getText();
        default void addListener(Runnable listener) {}
    }

    @FunctionalInterface
    private interface WareHouseListSetter {
        void setWareHouses(List<WareHouse> list);
    }
}
