package com.example.abershop.controllers.manager;

import com.example.abershop.core.Employee;
import com.example.abershop.core.EmployeeManager;
import com.example.abershop.core.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CurrentEmployeeController implements Initializable {


    public TableView<Employee> employeeTableView;
    public TableColumn<Employee,Integer> employeeId;
    public TableColumn<Employee,String> fullname;
    public TableColumn<Employee,String>  gender;
    public TableColumn<Employee,Integer>  phoneNumber;
    public TableColumn<Employee,String>  email;
    public TableColumn<Employee,String>  pass;
    public TableColumn<Employee,String>  role;
    public TableColumn<Employee, Objects>  startDate;

    private List<Employee> employees;
    private EmployeeManager manager;
    private  int eId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = EmployeeManager.getInstance();
        employees = manager.getEmployees();
        addTable();
        employeeTableView.getSelectionModel().selectedItemProperty().addListener((observable , oldVal,newVal ) ->{
            if (newVal!=null){
                eId = newVal.getEmployeeId();
            }

        });
    }
    private void addTable(){
        ObservableList<Employee> items = FXCollections.observableArrayList(employees);
        employeeTableView.setItems(items);
    }
    public void deleteEmployee(ActionEvent actionEvent) {
        if (eId != 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Employee");
            alert.setHeaderText("Are you sure delete Employee :"+ eId);
            alert.getButtonTypes().setAll(ButtonType.CANCEL,ButtonType.OK);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                manager.deleteEmployee(eId);
            }
        }
        refresh();
    }

    public void viewDetails(ActionEvent actionEvent) {
        if (eId!=0){
            Employee employee = Filters.employeeFilter(employees, eId);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("View Details");
            alert.setHeaderText("Employee Id : " + eId +
                    "\nEmployee Name : " + employee.getName() +
                    "\nGender : " + employee.getGender() +
                    "\nDate of Birth : " + employee.getDateOfBirth() +
                    "\nPhone Number : " + employee.getPhoneNumber()+
                    "\nAddress : " + employee.getAddress() +
                    "\nStart Date : " + employee.getStartDate() +
                    "\nEmail : " + employee.getEmail() +
                    "\nPassword Hash : " + employee.getPass());
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
        }

    }
    public void refresh(){
        employees = manager.getEmployees();
        addTable();

    }

}
