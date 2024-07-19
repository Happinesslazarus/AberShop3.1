package com.example.abershop.controllers;

import com.example.abershop.controllers.dashboard.StageCloseCallback;
import com.example.abershop.core.Device;
import com.example.abershop.core.Filters;
import com.example.abershop.core.Phones.AndroidPhone;
import com.example.abershop.core.Phones.Iphone;
import com.example.abershop.core.Repair;
import com.example.abershop.core.RepairManager;
import com.example.abershop.core.Tablets.Ipad;
import com.example.abershop.core.Tablets.WindowsTablet;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ViewDetailController implements Initializable {

    // UI elements defined in FXML
    public Label customerNameLabel;
    public Label contactNumberLabel;
    public Label deviceTypeLabel;
    public Label manufacturerLabel;
    public Label networkLabel;
    public Label hasCellularLabel;
    public Label osVersionLabel;
    public Label screenSizeLabel;
    public TextField repairIdField;
    public Label messageLabel;
    public Button searchButton;
    public Label repairDateLabel;
    public Label statusLabel;
    public Label deviceModelLabel;
    public Label issueDescriptionLabel;
    public Label priceLabel;
    public Label repairDetail;
    public Label deleted_lbl;
    public Pane paneId;
    public Label viewRepairId;
    public Button deleteBtn;

    private StageCloseCallback callback; // Callback for closing the stage

    // Instance variables
    private List<Repair> repairs; // List to hold repairs
    public int repairId; // ID of the repair to delete
    private FadeTransition memessageFadeText; // Fade transition for message label
    private FadeTransition deleteFadeText; // Fade transition for deleted label
    private RepairManager manager; // Instance of RepairManager to manage repairs


    //Pro JavaFX 9" by Johan Vos, Weiqi Gao, James Weaver, Stephen Chin, and Dean Iverson

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = RepairManager.getInstance(); // Get instance of RepairManager
        repairs = manager.getRepairs(); // Get list of repairs from manager
        memessageFadeText = new FadeTransition(Duration.seconds(3), messageLabel); // Initialize fade transition for message label
        deleteFadeText = new FadeTransition((Duration.seconds(3)), deleted_lbl); // Initialize fade transition for deleted label
    }

    // Method to handle search button click
    public void searchRepair(ActionEvent actionEvent) {
        try {
            if (!repairIdField.getText().isEmpty()) {
                repairId = Integer.parseInt(repairIdField.getText()); // Parse repair ID from text field
            }
            repairs = manager.getRepairs(); // Refresh repairs list
            Repair filterRepair = Filters.repairIdFilter(repairs, repairId); // Filter repair based on ID
            setInformation(filterRepair); // Set information in UI based on filtered repair
        } catch (Exception e) {
            e.printStackTrace();
            setFadeText(memessageFadeText, messageLabel, "Invalid Repair ID !"); // Show message for invalid repair ID
        }
    }

    // Method to set information in UI based on filtered repair
    private void setInformation(Repair filterRepair) {
        repairDateLabel.setText(filterRepair.getRepairDate().toString()); // Set repair date in UI
        statusLabel.setText(filterRepair.getStatus().toString()); // Set status in UI
        customerNameLabel.setText(filterRepair.getCustomerName()); // Set customer name in UI
        contactNumberLabel.setText(String.valueOf(filterRepair.getCustomerPhone())); // Set contact number in UI
        issueDescriptionLabel.setText(filterRepair.getIssueDescription()); // Set issue description in UI
        priceLabel.setText(String.valueOf(filterRepair.getRepairAmount())); // Set repair amount in UI
        repairDetail.setText(filterRepair.getRepairDetails()); // Set repair details in UI
        setDeviceInfo(filterRepair.getDevice()); // Set device information in UI
    }

    // Method to set device-specific information in UI
    private void setDeviceInfo(Device device) {
        if (device instanceof Iphone) {
            deviceTypeLabel.setText("Phone");
            deviceModelLabel.setText("Iphone");
            manufacturerLabel.setText("None");
            networkLabel.setText(((Iphone) device).getNetwork().toString());
            hasCellularLabel.setText("false");
            osVersionLabel.setText(((Iphone) device).getIosVersion());
            screenSizeLabel.setText(String.valueOf(device.getScreenSize()));
        } else if (device instanceof AndroidPhone) {
            deviceTypeLabel.setText("Phone");
            deviceModelLabel.setText("AndroidPhone");
            manufacturerLabel.setText(((AndroidPhone) device).getManufacture());
            networkLabel.setText(((AndroidPhone) device).getNetwork().toString());
            hasCellularLabel.setText("false");
            osVersionLabel.setText(((AndroidPhone) device).getAndroidVersion());
            screenSizeLabel.setText(String.valueOf(device.getScreenSize()));
        } else if (device instanceof Ipad) {
            deviceTypeLabel.setText("Tablet");
            deviceModelLabel.setText("Ipad");
            manufacturerLabel.setText("None");
            networkLabel.setText("false");
            hasCellularLabel.setText(String.valueOf(((Ipad) device).hasCellular()));
            osVersionLabel.setText(((Ipad) device).getIpadOsVersion());
            screenSizeLabel.setText(String.valueOf(device.getScreenSize()));
        } else if (device instanceof WindowsTablet) {
            deviceTypeLabel.setText("Tablet");
            deviceModelLabel.setText("WindowsTablet");
            manufacturerLabel.setText("None");
            networkLabel.setText("false");
            hasCellularLabel.setText(String.valueOf(((WindowsTablet) device).hasCellular()));
            osVersionLabel.setText(((WindowsTablet) device).getWindowsVersion());
            screenSizeLabel.setText(String.valueOf(device.getScreenSize()));
        }
    }

    // Method to handle delete button click
    public void deleteRepair(ActionEvent actionEvent) {
        if (repairId != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Are you sure you want to delete this repair?");
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                List<Repair> updatedRepairs = repairs.stream()
                        .filter(repair -> repair.getRepairId() != repairId)
                        .collect(Collectors.toList());

                boolean deletionSuccess = manager.updateRepairs(updatedRepairs);

                if (deletionSuccess) {
                    repairs = updatedRepairs;
                    if (callback != null) {
                        callback.onStageClose(true);
                    }
                    setFadeText(deleteFadeText, deleted_lbl, "Successfully deleted repair !");
                    clear(); // Clear all UI fields including repair details
                } else {
                    setFadeText(deleteFadeText, deleted_lbl, "Failed to delete repair.");
                }
            }
        }
    }


    // Method to clear all UI fields
    private void clear() {
        customerNameLabel.setText("");
        contactNumberLabel.setText("");
        repairDateLabel.setText("");
        osVersionLabel.setText("");
        screenSizeLabel.setText("");
        issueDescriptionLabel.setText("");
        manufacturerLabel.setText("");
        hasCellularLabel.setText("");
        deviceTypeLabel.setText("");
        deviceModelLabel.setText("");
        networkLabel.setText("");
        priceLabel.setText("");
        statusLabel.setText(""); // Clear the status label
        repairDetail.setText(""); // Clear the repair detail
    }



    // Method to set fade effect on labels
    private void setFadeText(FadeTransition fadeTransition, Label label, String text) {
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        label.setText(text);
        fadeTransition.setOnFinished(event -> label.setVisible(false));
        fadeTransition.play();
        label.setVisible(true);
    }

    // Method to set callback for stage close event
    public void setCallback(StageCloseCallback callback) {
        this.callback = callback;
    }
}
