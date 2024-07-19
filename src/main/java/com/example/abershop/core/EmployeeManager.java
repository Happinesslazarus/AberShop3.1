package com.example.abershop.core;

import java.util.List;

public class EmployeeManager {

    private Employee employee;
    private List<Employee> employees;
    private static EmployeeManager employeeManager;
    private EmployeeCsvManager employeeCsvManager;

    private EmployeeManager() {
        employeeCsvManager = new EmployeeCsvManager();
    }
    public static EmployeeManager getInstance(){
        if (employeeManager == null){
           synchronized (EmployeeManager.class){
               if (employeeManager == null){
                   employeeManager = new EmployeeManager();
               }
           }
       }
       return employeeManager;
    }

    public List<Employee> getEmployees(){
        employees = employeeCsvManager.retriveEmployees();
        return employees;
    }

    public boolean addEmployee(List<String> details){
        boolean result =employeeCsvManager.saveEmployee(details);
        return result;
    }
    public void deleteEmployee(int employeeId){
        employees =employeeCsvManager.retriveEmployees();
        List<Employee> fileterEmployees = Filters.employeesFilter(employees,employeeId);
        employeeCsvManager.removeEmplyee(fileterEmployees);

    }
}
