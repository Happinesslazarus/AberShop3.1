package com.example.abershop.core;

import com.example.abershop.core.Builder.EmployeeBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * EmployeeCsvManager.java
 *
 * This class manages CSV operations for Employee data in the AberShop application.
 *
 * Sources for implementation ideas:
 * - CSV handling in Java: https://www.baeldung.com/java-csv-file-array
 * - File operations and handling in Java: https://docs.oracle.com/javase/tutorial/essential/io/
 * - Directory and file creation in Java: https://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java
 */





public class EmployeeCsvManager {
    List<Employee> employees = new ArrayList<>();
    Employee employee = null;
    private String filepath;

    public EmployeeCsvManager() {
        String os = System.getProperty("os.name");

        String dirPath;
        if (os.contains("win") || os.contains("Win") || os.contains("dows")){
            dirPath = "C:\\Abershoap";
        }
        else if (os.contains("nux") || os.contains("mac")){
            dirPath = System.getProperty("user.home").concat(File.separator+"Abershoap");
        }
        else {
            dirPath = System.getProperty("user.home").concat(File.separator+"Desktop");
        }
        File directory = new File(dirPath);
        filepath = dirPath+File.separator+"Employee.csv";
        File file = new File(filepath);

        try {
            if (!directory.exists()){
                boolean result = directory.mkdirs();
            }
            if(!file.exists()){
                boolean result = file.createNewFile();

            }
        } catch (IOException e) {
            System.out.println("In EmployeeCsvManager.java class found a error");
        }
    }
    public boolean saveEmployee(List<String> details){
        try (FileWriter writer = new FileWriter(filepath, StandardCharsets.UTF_8,true)){
            for (String detail :details){
                detail = detail.replace('\n',' ');
                writer.append(detail);
                writer.append(";");
            }
            writer.append("\n");
            writer.flush();

        } catch (IOException e) {
            return false;
        }
        return true;
    }


    public List<Employee> retriveEmployees(){
        employees.clear();
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))){
           List<String> employeesDetails = new ArrayList<>();
            reader.lines().forEach(employeesDetails::add);
            employeesDetails.forEach(info -> {
                this.employee = EmployeeBuilder.build(info);
                employees.add(employee);
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return employees;
    }

    public void removeEmplyee(List<Employee> fileterEmployees) {
        try (FileWriter writer = new FileWriter(filepath, StandardCharsets.UTF_8)){
            for (Employee employee: fileterEmployees){
                writer.append(String.valueOf(employee.getEmployeeId())+";"+
                        employee.getName() + ";" + employee.getGender() + ";"+
                        employee.getDateOfBirth().toString() + ";" +
                        String.valueOf(employee.getPhoneNumber()) + ";" +
                        employee.getRole() + ";" +employee.getAddress() +";"
                        + employee.getStartDate().toString() + ";" +
                        employee.getEmail() + ";" + employee.getPass() + ";");

                writer.append('\n');
                writer.flush();

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
