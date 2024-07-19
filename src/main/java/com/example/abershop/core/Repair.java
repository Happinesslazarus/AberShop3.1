package com.example.abershop.core;

import java.time.LocalDate;

public class Repair {
    private Device device;
    private String customerName;
    private int customerPhone;
    private String repairDetails;
    private int repairId;
    private LocalDate repairDate;
    private int repairAmount;
    private String issueDescription;
    private Status status;

    public Repair(Device device, String customerName, int customerPhone, String repairDetails,LocalDate repairDate ,int repairId,int repairAmount,String issueDescription,Status status) {
        this.device = device;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.repairDetails = repairDetails;
        this.repairDate = repairDate;
        this.repairId = repairId;
        this.repairAmount = repairAmount;
        this.issueDescription = issueDescription;
        this.status = status;

    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhone(int customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setRepairDetails(String repairDetails) {
        this.repairDetails = repairDetails;
    }

    public int getRepairAmount() {
        return repairAmount;
    }


    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public int getRepairId() {
        return repairId;
    }


    public LocalDate getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(LocalDate repairDate) {
        this.repairDate = repairDate;
    }

    public Device getDevice() {
        return device;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getCustomerPhone() {
        return customerPhone;
    }

    public String getRepairDetails() {
        return repairDetails;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
