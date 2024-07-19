package com.example.abershop.controllers;

import com.example.abershop.controllers.dashboard.TableController;
import com.example.abershop.core.Filters;
import com.example.abershop.core.Repair;
import com.example.abershop.core.RepairManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CurrentRepairController implements Initializable {
    public TableView<Repair> repairTableView;
    public TableColumn<Repair,Integer> repairIdColumn;
    public TableColumn<Repair,String> customerNameColumn;
    public TableColumn<Repair,String> deviceTypeColumn;
    public TableColumn<Repair,String> deviceModelColumn;
    public TableColumn<Repair,String> manufacturerColumn;
    public TableColumn<Repair,Double> screenSizeColumn;
    public TableColumn<Repair,String> networkTypeColmun;
    public TableColumn<Repair,String> isssueDescriColumn;


    public ScrollPane scrollPane;
    public TextField searchField;
    public ComboBox<String> filterComboBox;

    private List<Repair> repairs;
    private RepairManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        manager = RepairManager.getInstance();

        filterComboBox.getItems().addAll("None","Iphone","AndroidPhone","Ipad","WindowsTablet");
        filterComboBox.setValue("None");
        repairs = manager.getRepairs();

        filterComboBox.setOnAction(event -> {
            String item = filterComboBox.getValue().toString();
            if (item.equals("Iphone")){
               addTable(Filters.deviceFilter(repairs,item));
            }
            else if (item.equals("AndroidPhone")){
                addTable(Filters.deviceFilter(repairs,item));
            }
            else if (item.equals("Ipad")){
                addTable(Filters.deviceFilter(repairs,item));
            }
            else if (item.equals("WindowsTablet")){
                addTable(Filters.deviceFilter(repairs,item));
            }

        });
        addTable(repairs);
        searchFiler();

    }


    protected void addTable(List<Repair> repairs){

        repairIdColumn.setCellValueFactory(cell->(ObservableValue<Integer>) TableController.getRepairRow(cell.getValue(),"repairId"));
        customerNameColumn.setCellValueFactory(cell-> (ObservableValue<String>) TableController.getRepairRow(cell.getValue(),"customerName"));
        deviceTypeColumn.setCellValueFactory(cell -> (ObservableValue<String>) TableController.getRepairRow(cell.getValue(),"deviceType"));
        deviceModelColumn.setCellValueFactory(cell -> (ObservableValue<String>) TableController.getRepairRow(cell.getValue(),"deviceModel"));
        manufacturerColumn.setCellValueFactory(cell -> (ObservableValue<String>) TableController.getRepairRow(cell.getValue(),"manufacture"));
        networkTypeColmun.setCellValueFactory(cell-> (ObservableValue<String>) TableController.getRepairRow(cell.getValue(),"networkType"));
        screenSizeColumn.setCellValueFactory(cell ->(ObservableValue<Double>) TableController.getRepairRow(cell.getValue(),"screenSize") );
        isssueDescriColumn.setCellValueFactory(cell ->(ObservableValue<String>) TableController.getRepairRow(cell.getValue(),"issueDescription"));

        ObservableList<Repair> list = FXCollections.observableArrayList(repairs);
        repairTableView.setItems(list);
    }


    protected void searchFiler(){
        List<Repair> repairs = manager.getRepairs();
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newval) {
                if (!(newval.isEmpty()) ){
                    List<Repair> searchByName = Filters.cusotomerNameIdFilter(repairs,newval);
                    addTable(searchByName);
                }
                else{
                    addTable(repairs);
                }
            }
        });
    }
}
