package com.example.abershop.core.Phones;

import com.example.abershop.core.Device;

public class Iphone extends Device implements Phone {

    private String iosVersion;
    private Network network;

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public Iphone(String iosVersion, Double screenSize) {
        this.iosVersion = iosVersion;
        this.screenSize = screenSize;
        name = "IPhone";
    }
    @Override
    public void setNetwork(Network network) {
        this.network = network;
    }

    @Override
    public Network getNetwork() {
        return network;
    }

    public String getIosVersion() {
        return iosVersion;
    }
}
