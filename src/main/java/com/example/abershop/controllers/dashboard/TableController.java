package com.example.abershop.controllers.dashboard;

import com.example.abershop.core.Device;
import com.example.abershop.core.Phones.AndroidPhone;
import com.example.abershop.core.Phones.Iphone;
import com.example.abershop.core.Phones.Phone;
import com.example.abershop.core.Repair;
import com.example.abershop.core.Tablets.Ipad;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableController {

    public static Object getRepairRow  (Repair repair , String type){
        if (type.equals("repairId")){
                return new SimpleIntegerProperty(repair.getRepairId()).asObject();
        }

        else if (type.equals("customerName")){
                return new SimpleStringProperty(repair.getCustomerName());
        }

        else if (type.equals("deviceType")){
                // device type return for table
                Device device = repair.getDevice();
                if (device instanceof Phone){
                    return new SimpleStringProperty("Phone");
                }
                else {
                    return new SimpleStringProperty("Tablet");
                }

        }

        else if (type.equals("deviceModel")){
                Device device = repair.getDevice();
                if (device instanceof Iphone){
                    return new SimpleStringProperty("Iphone");
                }
                else if (device instanceof AndroidPhone){
                    return new SimpleStringProperty("AndroidPhone");
                }
                else if (device instanceof Ipad){
                    return new SimpleStringProperty("Ipad");
                }
                else {
                    return new SimpleStringProperty("WindowsTablet");
                }

        }
        else if (type.equals("manufacture")){
                Device device = repair.getDevice();
                if (device instanceof AndroidPhone){
                    return new SimpleStringProperty(((AndroidPhone)device).getManufacture());
                }
                else {
                    return new SimpleStringProperty(device.getName());
                }
        }

        else if (type.equals("networkType")){
                Device device = repair.getDevice();
                if (device instanceof AndroidPhone|| device instanceof Iphone){
                    return new SimpleStringProperty(((Phone)device).getNetwork().toString());
                }
                else {
                    return new SimpleStringProperty("Cellular");
                }
        }

        else if (type.equals("screenSize")){
                Device device = repair.getDevice();
                return new SimpleDoubleProperty(device.getScreenSize()).asObject();
        }

        else if (type.equals("issueDescription")){
                return new SimpleStringProperty(repair.getIssueDescription());
        }

        else if (type.equals("customerNum")){
                return new SimpleIntegerProperty(repair.getCustomerPhone()).asObject();
        }
        else if (type.equals("date")){
                return new SimpleStringProperty(repair.getRepairDate().toString());
        }
        else if (type.equals("price")){
                return new SimpleIntegerProperty(repair.getRepairAmount()).asObject();
        }
        else if(type.equals("status")){
                return new SimpleStringProperty(repair.getStatus().toString());
        }




        return null;
    }

}
