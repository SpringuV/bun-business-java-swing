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

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfessionController {
    final static Logger logger = LogManager.getLogger(ProfessionController.class);
    final WarehouseService service;
    final IProfessionView view;
    List<WareHouse> allWarehouses; // lưu tạm danh sách đầy đủ
    
    Timer inputDebounce;
    Timer outputDebounce;
    final int DEBOUNCE_DELAY = 500; // 300ms

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
    	allWarehouses = service.getAllWarehouses();
        view.setInputWarehouses(allWarehouses);
        view.setOutputWarehouses(allWarehouses);
    }

    // Thiết lập tìm kiếm với debounce
    private void setupSearch() {
        // Input search
        inputDebounce = new Timer(DEBOUNCE_DELAY, e -> filterInput());
        inputDebounce.setRepeats(false);
        view.addInputSearchListener(() -> {
            inputDebounce.restart();
        });

        // Output search
        outputDebounce = new Timer(DEBOUNCE_DELAY, e -> filterOutput());
        outputDebounce.setRepeats(false);
        view.addOutputSearchListener(() -> {
            outputDebounce.restart();
        });
    }

    private void filterInput() {
        String key = view.getInputSearchText();
        List<WareHouse> filtered = allWarehouses.stream()
                .filter(word -> word.getName_warehouse().toLowerCase().contains(key)
                        || word.getCode_warehouse().toLowerCase().contains(key))
                .toList();
        view.setInputWarehouses(filtered);
    }

    private void filterOutput() {
        String key = view.getOutputSearchText();
        List<WareHouse> filtered = allWarehouses.stream()
                .filter(word -> word.getName_warehouse().toLowerCase().contains(key)
                        || word.getCode_warehouse().toLowerCase().contains(key))
                .toList();
        view.setOutputWarehouses(filtered);
    }

    private void setupMenuActions() {
        view.addInputWarehouseListener(e -> view.showInputWarehousePanel());
        view.addOutputWarehouseListener(e -> view.showOutputWarehousePanel());
        view.addInventoryListener(e -> view.showStatisticalPanel());
    }
}
