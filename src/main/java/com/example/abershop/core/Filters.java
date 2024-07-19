package com.example.abershop.core;

import com.example.abershop.core.Phones.AndroidPhone;
import com.example.abershop.core.Phones.Iphone;
import com.example.abershop.core.Tablets.Ipad;
import com.example.abershop.core.Tablets.WindowsTablet;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public  class Filters {

    // device Model filter
    public static List<Repair> deviceFilter(List<Repair> repairs , String deviceType){

        if (deviceType.equals("Iphone")){
            List<Repair> filteredRepairs = repairs.stream()
                    .filter(repair -> {
                        Device device = repair.getDevice();
                        return device instanceof Iphone;
                    })
                    .collect(Collectors.toList());
            return filteredRepairs;

        }
        else if (deviceType.equals("AndroidPhone")){
            List<Repair> filteredRepairs = repairs.stream()
                    .filter(repair -> {
                        Device device = repair.getDevice();
                        return device instanceof AndroidPhone;
                    })
                    .collect(Collectors.toList());
            return filteredRepairs;

        }
        else if (deviceType.equals("Ipad")){
            List<Repair> filteredRepairs = repairs.stream()
                    .filter(repair -> {
                        Device device = repair.getDevice();
                        return device instanceof Ipad;
                    })
                    .collect(Collectors.toList());
            return filteredRepairs;

        }
        else if (deviceType.equals("WindowsTablet")){
            List<Repair> filteredRepairs = repairs.stream()
                    .filter(repair -> {
                        Device device = repair.getDevice();
                        return device instanceof WindowsTablet;
                    })
                    .collect(Collectors.toList());
            return filteredRepairs;

        }
        return null;
    }

    // Status filter
    public static List<Repair> progressFilter(List<Repair> repairs, Status status){
            List<Repair> filterRepairs = repairs.stream()
                    .filter(repair ->   repair.getStatus() == status)
                    .collect(Collectors.toList());
            return filterRepairs;

    }


    //RepairId filter
    public static Repair repairIdFilter(List<Repair> repairs,int repairId){

        Repair filterRepair = repairs.stream()
                .filter(repair -> repair.getRepairId() == repairId)
                .findAny()
                .orElseThrow();
        return filterRepair;

    }


    //CustomerName filter
    public static List<Repair> cusotomerNameIdFilter(List<Repair> repairs,String customerNameId){
        List<Repair> filterRepairs = repairs.stream()
                .filter(repair -> {
                    if (customerNameId.matches("\\d+")){
                      return repair.getRepairId() == Integer.parseInt(customerNameId);
                    }
                    else {
                        return  repair.getCustomerName().equals(customerNameId);
                    }
                })
                .collect(Collectors.toList());
        return filterRepairs;
    }


    // Date filter
    public static List<Repair> dateFilter(List<Repair> repairs, LocalDate date){
         List<Repair> filterRepairs = repairs.stream()
                 .filter(repair -> repair.getRepairDate().isEqual(date))
                 .collect(Collectors.toList());

         return filterRepairs;
    }

    //employee filter
    public static Employee employeeFilter(List<Employee> employees , int employeeId){
        Employee filterEmployee =employees.stream()
                .filter(employee -> employee.getEmployeeId() == employeeId)
                .findAny().get();

        return filterEmployee;
    }


    public static List<Employee> employeesFilter(List<Employee> employees, int employeeId) {
        List<Employee> filterEmployees = employees.stream()
                .filter(employee -> employee.getEmployeeId() != employeeId )
                .collect(Collectors.toList());
        return filterEmployees;
    }

}

