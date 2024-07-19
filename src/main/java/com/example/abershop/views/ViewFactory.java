package com.example.abershop.views;

import com.example.abershop.controllers.employee.EmployeeController;
import com.example.abershop.controllers.manager.ManagerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class ViewFactory {



    //Manager view
    private final StringProperty managerSelectedMenuItem;
    private  final  StringProperty employeeSelectedMenuItem;
    private AnchorPane currentrepairView;
    private AnchorPane addnewrepairView;
    private AnchorPane viewDetailView;
    private AnchorPane dashBoardView;
    private AnchorPane editRepairView;
    private AnchorPane addnewrepairemployeeView;
    private AnchorPane currentEmployee;


    public ViewFactory() {
        this.employeeSelectedMenuItem = new SimpleStringProperty("");
        this.managerSelectedMenuItem = new SimpleStringProperty("");
    }


    public StringProperty getManagerSelectedMenuItem() {
        return managerSelectedMenuItem;
    }

    public StringProperty getEmployeeSelectedMenuItem() {
        return employeeSelectedMenuItem;
    }

    public AnchorPane getCurrentrepairView() {
            try {
                currentrepairView = new FXMLLoader(getClass().getResource("/Fxml/CurrentRepair.fxml")).load();

            } catch (Exception e) {
                e.printStackTrace();
            }

        return currentrepairView;
    }

    public AnchorPane getAddNewRepairView() {
            try {
                addnewrepairView = new FXMLLoader(getClass().getResource("/Fxml/AddNewRepair.fxml")).load();
            } catch (Exception e) {

                e.printStackTrace();
            }
        return addnewrepairView;
    }



    public AnchorPane getDashBoardView() {
            try {
                dashBoardView = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml")).load();
            } catch (Exception e) {

                e.printStackTrace();
            }


        return dashBoardView;
    }
    public AnchorPane getCurrentEmployee() {
        try {
            currentEmployee = new FXMLLoader(getClass().getResource("/Fxml/CurrentEmployee.fxml")).load();
        } catch (Exception e) {

            e.printStackTrace();
        }


        return currentEmployee;
    }


    public AnchorPane getAddnewrepairemployeeView() {
        try {
            // Load the FXML file using FXMLLoader
            addnewrepairemployeeView = new FXMLLoader(getClass().getResource("/Fxml/AddNewRepairEmployee.fxml")).load();
        } catch (IOException e) {
            // Handle any exceptions that occur during loading
            e.printStackTrace();
        }

        return addnewrepairemployeeView; // Return the loaded AnchorPane
    }



    public AnchorPane getViewDetailView() {

            try {
                viewDetailView = new FXMLLoader(getClass().getResource("/Fxml/ViewDetail.fxml")).load();
            } catch (Exception e) {

                e.printStackTrace();
            }

        return viewDetailView;
    }

    public AnchorPane getEditRepairViewiew() {
            try {
                editRepairView = new FXMLLoader(getClass().getResource("/Fxml/EditRepair.fxml")).load();
            } catch (Exception e) {

                e.printStackTrace();
            }

        return editRepairView;
    }


    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }



    public void showManagerWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Manager.fxml"));
        ManagerController managerController = new ManagerController();
        loader.setController(managerController);

        createStage(loader);
    }

    public void showEmployeeWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Employee.fxml"));
        EmployeeController employeeController = new EmployeeController();
        loader.setController(employeeController);

        createStage(loader);
    }





    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();

        }
        Stage stage = new Stage();
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/phonelogo.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("AberScreen");
        stage.show();
    }

    public void  closeStage(Stage stage) {
        stage.close();
    }
}