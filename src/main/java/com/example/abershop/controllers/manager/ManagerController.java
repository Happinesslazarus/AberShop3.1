package com.example.abershop.controllers.manager;

import com.example.abershop.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {

    //Core Java Volume II - Advanced Features" by Cay S. Horstmann
    public BorderPane manager_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {

            manager_parent.setCenter(Model.getInstance().getViewFactory().getDashBoardView());
            switch (newVal) {
                case "AddNewRepair" :
                    manager_parent.setCenter(Model.getInstance().getViewFactory().getAddNewRepairView());
                    break;
                case "ViewDetail" :
                    manager_parent.setCenter(Model.getInstance().getViewFactory().getViewDetailView());
                    break;
                case "EditRepair" :
                    manager_parent.setCenter(Model.getInstance().getViewFactory().getEditRepairViewiew());
                    break;
                case "CurrentRepair" :
                    manager_parent.setCenter(Model.getInstance().getViewFactory().getCurrentrepairView());
                    break;
                case "AddNewRepairEmployee" :
                    manager_parent.setCenter(Model.getInstance().getViewFactory().getAddnewrepairemployeeView());
                    break;
                case "CurrentEmployee":
                    manager_parent.setCenter(Model.getInstance().getViewFactory().getCurrentEmployee());
                    break;
                default :
                    manager_parent.setCenter(Model.getInstance().getViewFactory().getDashBoardView());
            }
        });
    }
}
