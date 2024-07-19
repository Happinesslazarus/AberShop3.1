package com.example.abershop.controllers.dashboard;

import com.example.abershop.core.*;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public TableView<Repair> repairTableView;
    public TableColumn<Repair,Integer> repairIdColumn;
    public TableColumn<Repair,String> customerNameColumn;
    public TableColumn<Repair,Integer> customerNumColumn;
    public TableColumn<Repair,String> manufacturerColumn;
    public TableColumn<Repair,Integer> priceColumn;
    public TableColumn<Repair,String> dateColumn;
    public TableColumn<Repair,Void> statusColumn;
    public TableColumn <Repair,Void>actionColumn;
    public ComboBox<String> filterComboBox;
    public TextField searchField;
    public Label completedRepairCount;
    public Label completedRepairPercentage;
    public Label name_lbl;
    public ObservableList<Repair> list;
    public Label totalRepairCount;
    public Label totalRepairPecentage;
    public Label currentRepairPercentage;
    public Label currentRepairCount;
    public DatePicker filterDatePicker;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;


    private PrecentageCal precentageCal;
    protected RepairManager manager;
    protected List<Repair> repairs;
    public int currentDiffrence;
    public int completeDiffrence;

    public int currentTotal;
    public int completeTotal;
// Stack Overflow example for initialize method

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        filterComboBox.getItems().addAll("All","Progress","Complete");
        filterComboBox.setValue("All");
        manager = RepairManager.getInstance();

        filterComboBox.setOnAction(event -> {
            String item = filterComboBox.getValue().toString();
            if (item.equals("All")){
                    repairs = manager.getRepairs();
                    addTable(repairs);
            }
            else if (item.equals("Progress")){
                List<Repair> filterRepairs = Filters.progressFilter(repairs, Status.PROGRESS);
                addTable(filterRepairs);
            }
            else {
                List<Repair> filterRepairs = Filters.progressFilter(repairs, Status.COMPLETE);
                addTable(filterRepairs);
            }
        });
        searchFilter();
        repairs = manager.getRepairs();
        addTable(repairs);

        precentageCal = new PrecentageCal(this);
        precentageCal.initilize(manager.getRepairs());
        setPecentage();


    }

    private void addTable(List<Repair> repairs){

        repairIdColumn.setCellValueFactory(cell->(ObservableValue<Integer>) TableController.getRepairRow(cell.getValue(),"repairId"));
        customerNumColumn.setCellValueFactory(cell->(ObservableValue<Integer>) TableController.getRepairRow(cell.getValue(),"customerNum"));
        customerNameColumn.setCellValueFactory(cell->(ObservableValue<String>) TableController.getRepairRow(cell.getValue(),"customerName"));
        dateColumn.setCellValueFactory(cell->(ObservableValue<String>) TableController.getRepairRow(cell.getValue(),"date"));
        priceColumn.setCellValueFactory(cell->(ObservableValue<Integer>) TableController.getRepairRow(cell.getValue(),"price"));
        manufacturerColumn.setCellValueFactory(cell -> (ObservableValue<String>) TableController.getRepairRow(cell.getValue(),"manufacture"));

        statusColumn.setCellFactory(cell->new TableStatusCellButton(this));
        actionColumn.setCellFactory(cell -> new TableActionCellButton(this));

         list = FXCollections.observableArrayList(repairs);
        repairTableView.setItems(list);
    }


    public void searchFilter(){
        List<Repair> repairs = manager.getRepairs();
        searchField.textProperty().addListener((observableValue, s, newval) -> {
            if (!(newval.isEmpty()) ){
                textListnerSearch(newval);
            }
            else{
                addTable(repairs);
            }
        });
    }

    private void textListnerSearch(String newval) {
        Task<List<Repair> > textProcess = new Task<>() {
            @Override
            protected List<Repair> call() throws Exception {
                return Filters.cusotomerNameIdFilter(repairs, newval);
            }
        };
        textProcess.setOnSucceeded(event ->{
            Platform.runLater(()->addTable(textProcess.getValue()));
        });
        new Thread(textProcess).start();
    }


    public void refresh() {
        setPecentage();
        repairTableView.refresh();
        System.gc();



    }

    private void setPecentage() {

        precentageCal.initilize(repairs);
        precentageCal.currentRepairCalculate();
        precentageCal.complteRepairCalculate();


        currentRepairCount.setText("+"+String.valueOf(currentTotal));
        currentRepairPercentage.setText(String.valueOf(currentDiffrence)+"% more than yesterday");

        completedRepairCount.setText("+"+String.valueOf(completeTotal));
        completedRepairPercentage.setText(String.valueOf(completeDiffrence)+"% more than yesterday");

        totalRepairCount.setText("+"+String.valueOf(currentTotal+completeTotal));
        totalRepairPecentage.setText(String.valueOf(currentDiffrence+completeDiffrence)+"%  more than yesterday");

        //System.out.println("complete repair "+completeTotal);
    }

    public void deleteRefresh() {
        addTable(manager.getRepairs());
        refresh();
    }
}
