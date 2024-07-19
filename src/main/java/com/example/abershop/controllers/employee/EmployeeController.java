package com.example.abershop.controllers.employee;

import com.example.abershop.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    public BorderPane employee_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Model.getInstance().getViewFactory().getEmployeeSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {

            employee_parent.setCenter(Model.getInstance().getViewFactory().getDashBoardView());
            switch (newVal) {
                case "AddNewRepair" :
                    employee_parent.setCenter(Model.getInstance().getViewFactory().getAddNewRepairView());
                    break;
                case "ViewDetail" :
                    employee_parent.setCenter(Model.getInstance().getViewFactory().getViewDetailView());
                    break;
                case "EditRepair" :
                    employee_parent.setCenter(Model.getInstance().getViewFactory().getEditRepairViewiew());
                    break;
                case "CurrentRepair" :
                    employee_parent.setCenter(Model.getInstance().getViewFactory().getCurrentrepairView());
                    break;
                default :
                    employee_parent.setCenter(Model.getInstance().getViewFactory().getDashBoardView());
            }
        });
    }



}
