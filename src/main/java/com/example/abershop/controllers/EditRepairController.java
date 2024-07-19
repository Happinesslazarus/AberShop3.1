package com.example.abershop.controllers;

import com.example.abershop.controllers.dashboard.StageCloseCallback;
import com.example.abershop.core.*;
import com.example.abershop.core.Phones.AndroidPhone;
import com.example.abershop.core.Phones.Iphone;
import com.example.abershop.core.Phones.Network;
import com.example.abershop.core.Tablets.Ipad;
import com.example.abershop.core.Tablets.WindowsTablet;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditRepairController implements Initializable {
    @FXML
    public TextField customerNameField;
    @FXML
    public TextField contactNumberField;
    @FXML
    public ComboBox<String> deviceTypeComboBox;
    @FXML
    public TextField manufacturerField;
    @FXML
    public ComboBox<Network> networkComboBox;
    @FXML
    public CheckBox hasCellularCheckBox;
    @FXML
    public TextField osVersionField;
    @FXML
    public TextField screenSizeField;
    @FXML
    public TextArea repairDetailsArea;
    @FXML
    public ComboBox<String> deviceModelComboBox;
    @FXML
    public Label edit_lbl;
    @FXML
    public Label edit_repair_message_lbl;
    @FXML
    public TextField searchTextField;
    @FXML
    public TextField briefIssueField;
    @FXML
    public DatePicker repairDatePicker;
    @FXML
    public HBox hboxId;

    private RepairManager manager;
    public List<Repair> repairs;
    public int repairId;
    public Repair repair;
    private FadeTransition repairIdtext;
    private StageCloseCallback callback;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = RepairManager.getInstance();
        repairs = manager.getRepairs();
        repairIdtext = new FadeTransition(Duration.seconds(3), edit_repair_message_lbl);
        manufacturerField.setDisable(true);
        hasCellularCheckBox.setDisable(true);

        deviceTypeComboBox.getItems().addAll("Phone", "Tablet");
        deviceModelComboBox.getItems().addAll("Iphone", "AndroidPhone");
        deviceTypeComboBox.setValue("Phone");
        deviceModelComboBox.setValue("Iphone");

        deviceTypeComboBox.valueProperty().addListener((observableValue, o, t1) -> {
            if ("Phone".equals(deviceTypeComboBox.getValue())) {
                hasCellularCheckBox.setDisable(true);
                networkComboBox.setDisable(false);
                deviceModelComboBox.getItems().setAll("Iphone", "AndroidPhone");
                deviceModelComboBox.setValue("Iphone");
            } else {
                networkComboBox.setDisable(true);
                hasCellularCheckBox.setDisable(false);
                deviceModelComboBox.getItems().setAll("Ipad", "WindowsTablet");
                deviceModelComboBox.setValue("Ipad");
            }
        });

        deviceModelComboBox.valueProperty().addListener((observableValue, o, t1) -> {
            manufacturerField.setDisable(!deviceModelComboBox.getValue().equals("AndroidPhone"));
        });

        networkComboBox.getItems().addAll(Network.values());
        networkComboBox.setValue(Network.EE);
    }

    public void saveEditedRepair(ActionEvent actionEvent) {
        if (validate()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save");
            alert.setHeaderText("Apply changes");
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                changeRepairInfo();
                boolean state = manager.updateRepairs(repairs);
                setFadeText(repairIdtext, edit_repair_message_lbl, state ? "Successfully applied changes!" : "Unsuccessfully applied changes!");

                if (state && callback != null) {
                    callback.onStageClose(true);
                }
            }
        } else {
            setFadeText(repairIdtext, edit_repair_message_lbl, "Please ensure all fields are valid before saving.");
        }
    }

    public void setCallback(StageCloseCallback callback) {
        this.callback = callback;
    }

    private void changeRepairInfo() {
        if (repair == null) {
            setFadeText(repairIdtext, edit_repair_message_lbl, "No repair selected. Please search for a repair first.");
            return;
        }

        // Update common repair information
        repair.setRepairDetails(repairDetailsArea.getText());
        repair.setCustomerName(customerNameField.getText());
        repair.setCustomerPhone(Integer.parseInt(contactNumberField.getText()));
        repair.setIssueDescription(briefIssueField.getText());

        // Create a new device based on the selected type and model
        Device newDevice = createDeviceFromSelection();

        // Set device-specific properties
        if (newDevice instanceof Iphone) {
            Iphone iphone = (Iphone) newDevice;
            iphone.setNetwork(networkComboBox.getValue());
        } else if (newDevice instanceof AndroidPhone) {
            AndroidPhone androidPhone = (AndroidPhone) newDevice;
            androidPhone.setNetwork(networkComboBox.getValue());
        } else if (newDevice instanceof Ipad) {
            Ipad ipad = (Ipad) newDevice;
            ipad.setCellular(hasCellularCheckBox.isSelected());
        } else if (newDevice instanceof WindowsTablet) {
            WindowsTablet windowsTablet = (WindowsTablet) newDevice;
            windowsTablet.setCellular(hasCellularCheckBox.isSelected());
        }

        // Update the repair with the new device
        repair.setDevice(newDevice);
    }

    private Device createDeviceFromSelection() {
        String deviceType = deviceTypeComboBox.getValue();
        String deviceModel = deviceModelComboBox.getValue();
        double screenSize = parseScreenSize();

        if ("Phone".equals(deviceType)) {
            if ("Iphone".equals(deviceModel)) {
                return new Iphone(osVersionField.getText(), screenSize);
            } else if ("AndroidPhone".equals(deviceModel)) {
                return new AndroidPhone(osVersionField.getText(), manufacturerField.getText(), screenSize);
            }
        } else if ("Tablet".equals(deviceType)) {
            if ("Ipad".equals(deviceModel)) {
                return new Ipad(osVersionField.getText(), screenSize);
            } else if ("WindowsTablet".equals(deviceModel)) {
                return new WindowsTablet(osVersionField.getText(), screenSize);
            }
        }

        throw new IllegalStateException("Invalid device type or model selected");
    }

    private double parseScreenSize() {
        try {
            return Double.parseDouble(screenSizeField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Screen Size");
            alert.setContentText("Please enter a valid number for the screen size.");
            alert.showAndWait();
            throw new IllegalArgumentException("Invalid screen size", e);
        }
    }

    public void searchInDetails() {
        String idText = searchTextField.getText();
        try {
            repairId = Integer.parseInt(idText);
            repair = Filters.repairIdFilter(repairs, repairId);
            setFadeText(repairIdtext, edit_repair_message_lbl, repair.getCustomerName() + " repair selected.");
            populateFields();
        } catch (NumberFormatException e) {
            setFadeText(repairIdtext, edit_repair_message_lbl, "Please enter a valid Repair ID.");
        } catch (Exception e) {
            setFadeText(repairIdtext, edit_repair_message_lbl, "Invalid Repair ID.");
        }
    }

    private void populateFields() {
        customerNameField.setText(repair.getCustomerName());
        contactNumberField.setText(String.valueOf(repair.getCustomerPhone()));
        repairDetailsArea.setText(repair.getRepairDetails());
        briefIssueField.setText(repair.getIssueDescription());

        Device device = repair.getDevice();
        if (device != null) {
            screenSizeField.setText(String.valueOf(device.getScreenSize()));
            if (device instanceof Iphone) {
                deviceTypeComboBox.setValue("Phone");
                deviceModelComboBox.setValue("Iphone");
                Iphone iphone = (Iphone) device;
                osVersionField.setText(iphone.getIosVersion());
                networkComboBox.setValue(iphone.getNetwork());
            } else if (device instanceof AndroidPhone) {
                deviceTypeComboBox.setValue("Phone");
                deviceModelComboBox.setValue("AndroidPhone");
                AndroidPhone androidPhone = (AndroidPhone) device;
                osVersionField.setText(androidPhone.getAndroidVersion());
                manufacturerField.setText(androidPhone.getManufacture());
                networkComboBox.setValue(androidPhone.getNetwork());
            } else if (device instanceof Ipad) {
                deviceTypeComboBox.setValue("Tablet");
                deviceModelComboBox.setValue("Ipad");
                Ipad ipad = (Ipad) device;
                osVersionField.setText(ipad.getIpadOsVersion());
                hasCellularCheckBox.setSelected(ipad.hasCellular());
            } else if (device instanceof WindowsTablet) {
                deviceTypeComboBox.setValue("Tablet");
                deviceModelComboBox.setValue("WindowsTablet");
                WindowsTablet windowsTablet = (WindowsTablet) device;
                osVersionField.setText(windowsTablet.getWindowsVersion());
                hasCellularCheckBox.setSelected(windowsTablet.hasCellular());
            }
        }
    }

    private void setFadeText(FadeTransition fadeTransition, Label label, String text) {
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        label.setText(text);
        fadeTransition.setOnFinished(event -> label.setVisible(false));
        fadeTransition.play();
        label.setVisible(true);
    }

    public void cancelEdit(ActionEvent actionEvent) {
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
        manufacturerField.clear();
        hasCellularCheckBox.setSelected(false);
        deviceTypeComboBox.setValue("Phone");
        deviceModelComboBox.setValue("Iphone");
        networkComboBox.setValue(Network.EE);
    }

    private boolean validate() {
        if (repair == null) {
            setFadeText(repairIdtext, edit_repair_message_lbl, "Please search for a valid Repair ID first.");
            return false;
        }

        if (!customerNameField.getText().matches("[a-zA-Z\\s]*")) {
            setFadeText(repairIdtext, edit_repair_message_lbl, "Customer name must contain only alphabets and spaces.");
            return false;
        }

        if (!contactNumberField.getText().matches("\\d*")) {
            setFadeText(repairIdtext, edit_repair_message_lbl, "Contact number must contain only digits.");
            return false;
        }

        if (deviceTypeComboBox.getValue().equals("Phone")) {
            if (networkComboBox.getValue() == null) {
                setFadeText(repairIdtext, edit_repair_message_lbl, "Network is required for phones.");
                return false;
            }
            if (deviceModelComboBox.getValue().equals("AndroidPhone") && manufacturerField.getText().trim().isEmpty()) {
                setFadeText(repairIdtext, edit_repair_message_lbl, "Manufacturer is required for Android phones.");
                return false;
            }
        }

        try {
            parseScreenSize();
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    public void loadRepairData(Repair repair) {
        this.repair = repair;
        populateFields();
    }
}