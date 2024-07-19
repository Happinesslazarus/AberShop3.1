package com.example.abershop.controllers.manager;

import com.example.abershop.core.EmployeeManager;
import com.example.abershop.core.PasswordHash;
import com.example.abershop.models.Model;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class AddNewEmployeeController implements Initializable {
    public TextField fullNameField;
    public TextField emailField;
    public TextField phoneNumberField;
    public Label messageLabel;
    public TextField passwordField;
    public TextArea addressArea;
    public DatePicker startDatePicker;
    public DatePicker dateOfBirthPicker;
    public TextField roleField;
    public ChoiceBox<String> genderComboBox;

    private EmployeeManager employeeManager;
    private FadeTransition messageFade;
    private Random rand;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rand = new Random();
        messageFade = new FadeTransition(Duration.seconds(3),messageLabel);
        employeeManager = EmployeeManager.getInstance();
        genderComboBox.getItems().addAll("Male","Female");
        genderComboBox.setValue("Male");


    }


//
    public void saveEmployee(ActionEvent actionEvent) {
        boolean isempty = validate();

        if (isempty) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save");
            alert.setHeaderText("Save Employee");
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                String hashPassword = PasswordHash.computeHash(passwordField.getText());
                List<String> employeeInfo = Arrays.asList(
                       String.valueOf( rand.nextInt((9999 - 1000) + 1) + 1000),fullNameField.getText(), genderComboBox.getValue(), dateOfBirthPicker.getValue().toString(),
                        phoneNumberField.getText(),roleField.getText(),
                        addressArea.getText(),startDatePicker.getValue().toString(),emailField.getText(),hashPassword
                        );
                if (employeeManager.addEmployee(employeeInfo)){
                    setFadeText(messageFade,messageLabel,"Employee Add Sucessfully .");
                    clear();
                }
                else {
                    setFadeText(messageFade,messageLabel,"Employee Add Unsucessfully .");
                }
            }
        }
    }


    public void currentEmployee(ActionEvent actionEvent) {
        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().set("CurrentEmployee");

    }

    private boolean validate() {
        if (fullNameField.getText().isEmpty()){
            setFadeText(messageFade, messageLabel,"Full name is required.");
            return false;
        }

        if (dateOfBirthPicker.getValue() == null) {
            setFadeText(messageFade,messageLabel,"Date of birth required.");
            return false;
        }
        if (emailField.getText().isEmpty()) {
            setFadeText(messageFade,messageLabel,"Email address is required.");
            return false;
        }
        if (passwordField.getText().isEmpty()){
            setFadeText(messageFade,messageLabel,"Password is required.");
            return false;
        }
        if (!phoneNumberField.getText().matches("\\d+")) {
            setFadeText(messageFade,messageLabel,"Phone number must contain only digits.");
            return false;
        }
        if (roleField.getText().trim().isEmpty()) {
            setFadeText(messageFade,messageLabel,"Job role is required.");
            return false;
        }
        if (addressArea.getText().trim().isEmpty()) {
            setFadeText(messageFade,messageLabel,"Address  are required.");
            return false;
        }
        if (startDatePicker.getValue() == null) {
            setFadeText(messageFade,messageLabel,"Start date is required.");
            return false;
        }
        return true;
    }

    private void setFadeText(FadeTransition fadeTransition, Label label , String text){
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        label.setText(text);
        fadeTransition.setOnFinished(event->label.setVisible(false));
        fadeTransition.play();
        label.setVisible(true);
    }
    public void clear (){
        fullNameField.setText("");
        dateOfBirthPicker.setValue(null);
        emailField.setText("");
        passwordField.setText("");
        phoneNumberField.setText("");
        roleField.setText("");
        addressArea.setText("");
        startDatePicker.setValue(null);

    }
}