package com.example.abershop.core;

import com.example.abershop.core.Builder.RepairBuilder;
import com.example.abershop.core.Phones.AndroidPhone;
import com.example.abershop.core.Phones.Iphone;
import com.example.abershop.core.Tablets.Ipad;
import com.example.abershop.core.Tablets.WindowsTablet;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepairCsvManager {
    private List<String> information;
    private List<String> customerInfo;
    private List<Repair> repairs;

    private final String filepath;

    public RepairCsvManager() {
        repairs = new ArrayList<>();
        String os = System.getProperty("os.name");
        String dirPath;
        if (os.contains("win") || os.contains("Win") || os.contains("dows")) {
            dirPath = "C:\\Abershoap";
        } else if (os.contains("nux") || os.contains("mac")) {
            dirPath = System.getProperty("user.home").concat(File.separator + "Abershoap");
        } else {
            dirPath = System.getProperty("user.home").concat(File.separator + "Desktop");
        }
        File directory = new File(dirPath);
        filepath = dirPath + File.separator + "Information.csv";
        File file = new File(filepath);

        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error initializing RepairCsvManager: " + e.getMessage());
        }
    }

    private List<String> getInformation(Repair repair) {
        Device device = repair.getDevice();
        customerInfo = new ArrayList<>(Arrays.asList(
                String.valueOf(repair.getRepairId()),
                repair.getCustomerName(),
                String.valueOf(repair.getCustomerPhone())
        ));

        switch (device.name) {
            case "IPhone" -> information = new ArrayList<>(Arrays.asList(
                    "Iphone",
                    "None",
                    ((Iphone) device).getIosVersion(),
                    String.valueOf(device.screenSize),
                    "None",
                    String.valueOf(((Iphone) device).getNetwork())
            ));
            case "AndroidPhone" -> information = new ArrayList<>(Arrays.asList(
                    "AndroidPhone",
                    ((AndroidPhone) device).getManufacture(),
                    ((AndroidPhone) device).getAndroidVersion(),
                    String.valueOf(device.screenSize),
                    "None",
                    String.valueOf(((AndroidPhone) device).getNetwork())
            ));
            case "Ipad" -> information = new ArrayList<>(Arrays.asList(
                    "Ipad",
                    "None",
                    ((Ipad) device).getIpadOsVersion(),
                    String.valueOf(device.screenSize),
                    String.valueOf(((Ipad) device).hasCellular()),
                    "None"
            ));
            case "WindowsTablet" -> information = new ArrayList<>(Arrays.asList(
                    "WindowsTablet",
                    "None",
                    ((WindowsTablet) device).getWindowsVersion(),
                    String.valueOf(device.screenSize),
                    String.valueOf(((WindowsTablet) device).hasCellular()),
                    "None"
            ));
        }

        information.add(repair.getRepairDate().toString());
        information.add(repair.getRepairDetails());
        information.add(String.valueOf(repair.getRepairAmount()));
        information.add(repair.getIssueDescription());
        information.add(repair.getStatus().toString());
        customerInfo.addAll(information);

        return customerInfo;
    }

    public boolean saveRepairs(Repair repair, boolean setAppend) {
        try (FileWriter writer = new FileWriter(filepath, StandardCharsets.UTF_8, setAppend)) {
            List<String> infos = getInformation(repair);
            for (String info : infos) {
                writer.append(info);
                writer.append(';');
            }
            writer.append('\n');
            writer.flush();
        } catch (Exception e) {
            System.out.println("Error saving repair: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Repair> retrieveRepairs() {
        repairs.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Repair repair = RepairBuilder.build(line);
                repairs.add(repair);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: CSV file is missing expected columns.");
        } catch (Exception e) {
            System.out.println("Error retrieving data from CSV: " + e.getMessage());
        }
        return repairs;
    }

    public void clear() {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write("");
        } catch (Exception e) {
            System.out.println("Error clearing CSV file: " + e.getMessage());
        }
    }
}
