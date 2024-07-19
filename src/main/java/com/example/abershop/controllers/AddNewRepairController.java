package com.example.abershop.controllers;

import com.example.abershop.core.Device;
import com.example.abershop.core.Phones.AndroidPhone;
import com.example.abershop.core.Phones.Iphone;
import com.example.abershop.core.Phones.Network;
import com.example.abershop.core.Repair;
import com.example.abershop.core.RepairManager;
import com.example.abershop.core.Status;
import com.example.abershop.core.Tablets.Ipad;
import com.example.abershop.core.Tablets.WindowsTablet;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class AddNewRepairController implements Initializable {
    @FXML public TextField customerNameField;
    @FXML public TextField contactNumberField;
    @FXML public ComboBox<String> deviceTypeComboBox;
    @FXML public TextField manufacturerField;
    @FXML public ComboBox<Network> networkComboBox;
    @FXML public ComboBox<String> deviceModelComboBox;
    @FXML public CheckBox hasCellularCheckBox;
    @FXML public TextField osVersionField;
    @FXML public TextField screenSizeField;
    @FXML public TextArea repairDetailsArea;
    @FXML public DatePicker repairDatePicker;
    @FXML public TextField priceField;
    @FXML public TextField briefIssueField;
    @FXML public Label new_repair_message_lbl;
    @FXML public Label repairMessagelbl;

    private Device device;
    private RepairManager manager;
    private Random rand;
    private FadeTransition fadeTransition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fadeTransition = new FadeTransition(Duration.seconds(2), new_repair_message_lbl);
        rand = new Random();
        manager = RepairManager.getInstance();

        deviceTypeComboBox.getItems().addAll("Phone", "Tablet");
        deviceTypeComboBox.setValue("Phone");

        deviceModelComboBox.getItems().addAll("IPhone", "Android");
        deviceModelComboBox.setValue("IPhone");

        hasCellularCheckBox.setDisable(true);
        manufacturerField.setDisable(true);

        deviceTypeComboBox.valueProperty().addListener((observableValue, o, t1) -> {
            if ("Phone".equals(deviceTypeComboBox.getValue())) {
                hasCellularCheckBox.setDisable(true);
                networkComboBox.setDisable(false);
                deviceModelComboBox.getItems().setAll("IPhone", "Android");
                deviceModelComboBox.setValue("IPhone");
            } else {
                networkComboBox.setDisable(true);
                hasCellularCheckBox.setDisable(false);
                deviceModelComboBox.getItems().setAll("IPad", "Windows");
                deviceModelComboBox.setValue("IPad");
            }
        });

        deviceModelComboBox.valueProperty().addListener((observableValue, o, t1) -> {
            manufacturerField.setDisable(!Objects.equals(deviceModelComboBox.getValue(), "Android"));
        });

        networkComboBox.getItems().addAll(Network.EE, Network.O2, Network.TESCO, Network.VODAFONE);
        networkComboBox.setValue(Network.EE);
    }

    @FXML
    public void SaveRepair(ActionEvent actionEvent) {
        StringBuilder errorMessage = new StringBuilder();

        // Validate common fields
        if (customerNameField.getText().trim().isEmpty()) {
            errorMessage.append("Customer name is required.\n");
        }
        if (contactNumberField.getText().trim().isEmpty()) {
            errorMessage.append("Contact number is required.\n");
        } else if (!contactNumberField.getText().matches("\\d+")) {
            errorMessage.append("Contact number must contain only digits.\n");
        }
        if (repairDatePicker.getValue() == null) {
            errorMessage.append("Repair date is required.\n");
        }
        if (repairDetailsArea.getText().trim().isEmpty()) {
            errorMessage.append("Repair details are required.\n");
        }
        if (osVersionField.getText().trim().isEmpty()) {
            errorMessage.append("OS version is required.\n");
        }
        if (screenSizeField.getText().trim().isEmpty()) {
            errorMessage.append("Screen size is required.\n");
        } else if (!screenSizeField.getText().matches("^[+]?[0-9]*\\.?[0-9]+")) {
            errorMessage.append("Screen size must be a number.\n");
        }
        if (briefIssueField.getText().trim().isEmpty()) {
            errorMessage.append("Brief issue description is required.\n");
        }
        if (priceField.getText().trim().isEmpty()) {
            errorMessage.append("Price is required.\n");
        } else if (!priceField.getText().matches("\\d+")) {
            errorMessage.append("Price must be a number.\n");
        }

        // Validate device-specific fields
        if ("Phone".equals(deviceTypeComboBox.getValue())) {
            if (networkComboBox.getValue() == null) {
                errorMessage.append("Network is required for phones.\n");
            }
            if ("Android".equals(deviceModelComboBox.getValue()) && manufacturerField.getText().trim().isEmpty()) {
                errorMessage.append("Manufacturer is required for Android phones.\n");
            }
        }

        if (!errorMessage.isEmpty()) {
            setLabel(errorMessage.toString());
            return;
        }

        try {
            if ("Phone".equals(deviceTypeComboBox.getValue())) {
                if ("IPhone".equals(deviceModelComboBox.getValue())) {
                    device = new Iphone(osVersionField.getText(),
                            Double.parseDouble(screenSizeField.getText()));
                    ((Iphone) device).setNetwork(networkComboBox.getValue());
                } else if ("Android".equals(deviceModelComboBox.getValue())) {
                    device = new AndroidPhone(manufacturerField.getText(),
                            osVersionField.getText(),
                            Double.parseDouble(screenSizeField.getText()));
                    ((AndroidPhone) device).setNetwork(networkComboBox.getValue());
                }
            } else {
                if ("IPad".equals(deviceModelComboBox.getValue())) {
                    device = new Ipad(osVersionField.getText(),
                            Double.parseDouble(screenSizeField.getText()));
                    ((Ipad) device).setCellular(hasCellularCheckBox.isSelected());
                } else if ("Windows".equals(deviceModelComboBox.getValue())) {
                    device = new WindowsTablet(osVersionField.getText(),
                            Double.parseDouble(screenSizeField.getText()));
                    ((WindowsTablet) device).setCellular(hasCellularCheckBox.isSelected());
                }
            }

            int repairId = generateRepairId();

            Repair repair = new Repair(device, customerNameField.getText(),
                    Integer.parseInt(contactNumberField.getText()),
                    repairDetailsArea.getText(), repairDatePicker.getValue(),
                    repairId,
                    Integer.parseInt(priceField.getText()),
                    briefIssueField.getText(),
                    Status.PROGRESS
            );

            boolean success = manager.addRepair(repair);
            if (success) {
                repairMessagelbl.setText("Your Repair ID is: " + repairId);
                setLabel("New repair added successfully.");
                clearFields();
            } else {
                setLabel("Failed to add new repair. Please try again.");
            }

        } catch (NumberFormatException e) {
            setLabel("Invalid number format. Please check your inputs.");
        } catch (Exception e) {
            setLabel("An error occurred: " + e.getMessage());
        }
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        customerNameField.clear();
        contactNumberField.clear();
        repairDatePicker.setValue(null);
        repairDetailsArea.clear();
        osVersionField.clear();
        screenSizeField.clear();
        briefIssueField.clear();
        priceField.clear();
        manufacturerField.clear();
        hasCellularCheckBox.setSelected(false);
        deviceTypeComboBox.getSelectionModel().clearSelection();
        deviceModelComboBox.getSelectionModel().clearSelection();
        networkComboBox.setValue(Network.EE);
        repairMessagelbl.setText("Your Repair ID is");
    }

    private void setLabel(String labelText) {
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        new_repair_message_lbl.setText(labelText);
        fadeTransition.setOnFinished(event -> new_repair_message_lbl.setVisible(false));
        fadeTransition.setDelay(Duration.seconds(1));
        fadeTransition.play();
        new_repair_message_lbl.setVisible(true);
    }

    private int generateRepairId() {
        return rand.nextInt((9999 - 1000) + 1) + 1000;
    }
}