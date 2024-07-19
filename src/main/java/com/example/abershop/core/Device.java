package com.example.abershop.core;

public  abstract class Device {
    protected Double screenSize;
    protected String name;
    public void setScreenSize(Double screenSize){
        this.screenSize = screenSize;
    }

    public Double getScreenSize() {
        return screenSize;
    }

    public String getName() {
        return name;
    }
}
