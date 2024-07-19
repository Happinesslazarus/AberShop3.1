package com.example.abershop.core.Tablets;

import com.example.abershop.core.Device;

public class WindowsTablet extends Device implements Tablet {
    private String windowsVersion;
    private boolean cell;

    public void setWindowsVersion(String windowsVersion) {
        this.windowsVersion = windowsVersion;
    }

    public WindowsTablet(String windowsVersion, Double scereenSize) {
        this.windowsVersion = windowsVersion;
        this.screenSize = scereenSize;
        name = "WindowsTablet";
    }


    @Override
    public void setCellular(boolean cell) {
        this.cell = cell;
    }

    @Override
    public boolean hasCellular() {
        return  cell;
    }

    public String getWindowsVersion() {
        return windowsVersion;
    }
}
