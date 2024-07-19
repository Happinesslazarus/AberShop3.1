package com.example.abershop.core.Phones;

import com.example.abershop.core.Device;

public class AndroidPhone extends Device implements Phone{
    private String manufacture;
    private  String androidVersion;
    private  Network network;

    public AndroidPhone(String manufacture, String androidVersion, Double screenSize){
        this.androidVersion = androidVersion;
        this.manufacture = manufacture;
        this.screenSize = screenSize;
        name = "AndroidPhone";
    }

    public String getManufacture() {
        return manufacture;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    @Override
    public void setNetwork(Network network) {
        this.network = network;
    }

    @Override
    public Network getNetwork() {
        return network;
    }
}
