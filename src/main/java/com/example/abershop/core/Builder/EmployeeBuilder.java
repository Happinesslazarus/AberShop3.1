package com.example.abershop.core.Builder;

import com.example.abershop.core.Employee;

import java.time.LocalDate;

public class EmployeeBuilder {
   private static Employee employee;
    public static Employee build(String details){
        String[] employeeInfo = details.split("\\;");
        employee = new Employee(Integer.parseInt(employeeInfo[0]),employeeInfo[1],employeeInfo[2],
                LocalDate.parse( employeeInfo[3]),Integer.parseInt(employeeInfo[4]),employeeInfo[5],
                employeeInfo[6],LocalDate.parse(employeeInfo[7]),employeeInfo[8],employeeInfo[9] );

        return employee;
    }
}
