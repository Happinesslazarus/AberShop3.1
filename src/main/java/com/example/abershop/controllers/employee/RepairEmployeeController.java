package com.example.abershop.controllers.employee;

import com.example.abershop.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RepairEmployeeController implements Initializable {
    public Button logout_btn;
    public Button dashboard_btn;
    public Button view_detail_btn;
    public Button current_repair_btn;
    public Button add_new_repair_btn;
    public Button edit_repair_btn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        current_repair_btn.setOnAction(event -> onCurrentRepair());
        add_new_repair_btn.setOnAction(event -> onAddNewRepair());
        view_detail_btn.setOnAction(event->onViewDeatail());
        edit_repair_btn.setOnAction(event ->onEditRepair());
        dashboard_btn.setOnAction(event -> onDashboard());
        logout_btn.setOnAction(event -> logout());
    }



    private void onCurrentRepair() {
        Model.getInstance().getViewFactory().getEmployeeSelectedMenuItem().set("CurrentRepair");
    }

    private void onAddNewRepair() {
        Model.getInstance().getViewFactory().getEmployeeSelectedMenuItem().set("AddNewRepair");
    }



    private void onViewDeatail() {
        Model.getInstance().getViewFactory().getEmployeeSelectedMenuItem().set("ViewDetail");
    }

    private void onEditRepair() {
        Model.getInstance().getViewFactory().getEmployeeSelectedMenuItem().set("EditRepair");
    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().getEmployeeSelectedMenuItem().set("Dashboard");
    }

    private void logout() {
        Model.getInstance().getViewFactory().closeStage((Stage) logout_btn.getScene().getWindow());
        Model.getInstance().getViewFactory().showLoginWindow();
    }

}
