package com.example.abershop.core.Builder;

import com.example.abershop.core.Device;
import com.example.abershop.core.Repair;
import com.example.abershop.core.Status;

import java.time.LocalDate;
import java.util.HashMap;;

public class RepairBuilder  { ;
    private  HashMap<String, String> deviceInfo = new HashMap<String,String>(); ;
    private  HashMap<String, String> customerInfo = new HashMap<String,String>(); ;

    public static Repair build(String repairInfo){
        String[] detatils =  repairInfo.split("\\;");

        RepairBuilder repairBuilder= new RepairBuilder();

        repairBuilder.setDeviceInfo(detatils);
        repairBuilder.setCustomerInfo(detatils);
        Device device = DeviceBuilder.build(
                repairBuilder.deviceInfo.get("deviceName"),
                repairBuilder.deviceInfo.get("manufracture"),
                repairBuilder.deviceInfo.get("os"),
                repairBuilder.deviceInfo.get("screen"),
                repairBuilder.deviceInfo.get("cell"),
                repairBuilder.deviceInfo.get("network")
        );


        Repair repair = new Repair(device,
                       repairBuilder.customerInfo.get("customerName"),
                       Integer.parseInt(repairBuilder.customerInfo.get("contact")),
                       repairBuilder.deviceInfo.get("repairDetail"),
                       LocalDate.parse(repairBuilder.deviceInfo.get("repairDate")),
                       Integer.parseInt(repairBuilder.customerInfo.get("repairId")),
                        Integer.parseInt(repairBuilder.deviceInfo.get("repairAmount")),
                        repairBuilder.deviceInfo.get("repairIssue"),
                        Status.valueOf(repairBuilder.deviceInfo.get("repairStatus"))
                );
        return repair;
    }


    private  void setCustomerInfo(String [] details){
        customerInfo.put("repairId",details[0]);
        customerInfo.put("customerName",details[1]);
        customerInfo.put("contact",details[2]);

    }
    private   void setDeviceInfo(String[] details){
        deviceInfo.put("deviceName",details[3]);
        deviceInfo.put("manufracture",details[4]);
        deviceInfo.put("os",details[5]);
        deviceInfo.put("screen",details[6]);
        deviceInfo.put("cell",details[7]);
        deviceInfo.put("network",details[8]);
        deviceInfo.put("repairDate",details[9]);
        deviceInfo.put("repairDetail",details[10]);
        deviceInfo.put("repairAmount",details[11]);
        deviceInfo.put("repairIssue",details[12]);
        deviceInfo.put("repairStatus",details[13]);
    }






}
