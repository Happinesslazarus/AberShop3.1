package com.example.abershop.core.Builder;

import com.example.abershop.core.Device;
import com.example.abershop.core.Phones.AndroidPhone;
import com.example.abershop.core.Phones.Iphone;
import com.example.abershop.core.Phones.Network;
import com.example.abershop.core.Tablets.Ipad;
import com.example.abershop.core.Tablets.WindowsTablet;

public class DeviceBuilder {
    public static Device build (String deviceName, String manufracture, String os, String screen, String cell, String network){
        double screenSize = Double.parseDouble(screen);
        if (deviceName.equals("Iphone")){
            Device device = new Iphone(os,screenSize);
            Network net = Network.valueOf(network);
            ((Iphone)device).setNetwork(net);
            return device;
        }
        else if (deviceName.equals("AndroidPhone")){
            Device device = new AndroidPhone(manufracture,os,screenSize);
            Network net = Network.valueOf(network);
            ((AndroidPhone)device).setNetwork(net);

            return device;
        }
        else if (deviceName.equals("Ipad")){
            Device device = new Ipad(os,screenSize);
            boolean cellular = Boolean.parseBoolean(cell);
            ((Ipad)device).setCellular(cellular);
            return device;
        }
        else if (deviceName.equals("WindowsTablet")){
            Device device = new WindowsTablet(os,screenSize);
            boolean cellular = Boolean.parseBoolean(cell);
            ((WindowsTablet)device).setCellular(cellular);
            return  device;
        }
        else {
            return null;
        }
    }

}
