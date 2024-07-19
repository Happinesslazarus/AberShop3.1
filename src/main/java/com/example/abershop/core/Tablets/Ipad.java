package com.example.abershop.core.Tablets;

import com.example.abershop.core.Device;

public class Ipad extends Device implements Tablet{
    private boolean cell;
    private String ipadOsVersion;

    public void setIpadOsVersion(String ipadOsVersion) {
        this.ipadOsVersion = ipadOsVersion;
    }

    public Ipad(String ipadOsVersion, Double scereenSize) {
        this.ipadOsVersion = ipadOsVersion;
        this.screenSize = scereenSize;
        name = "Ipad";
    }

    @Override
    public void setCellular(boolean cell) {
        this.cell = cell;
    }

    @Override
    public boolean hasCellular() {
        return cell;
    }

    public String getIpadOsVersion() {
        return ipadOsVersion;
    }
}
