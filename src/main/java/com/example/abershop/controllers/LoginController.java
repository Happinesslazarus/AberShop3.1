package com.example.abershop.controllers;

import com.example.abershop.authenticate.PassEmail;
import com.example.abershop.models.Model;
import com.example.abershop.views.AccountType;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController  implements Initializable {
    public Label email_address_lbl;
    public TextField email_address_fld;
    public PasswordField password_fld;
    public Button login_btn;
    public Label error_lbl;
    public ChoiceBox<AccountType> acc_selector;
    public Label password_lbl;

    private PassEmail passEmail;
    private FadeTransition errorFade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.EMPLOYEE, AccountType.MANAGER));
        acc_selector.setValue(AccountType.EMPLOYEE);
        login_btn.setOnAction(event -> onLogin());

        passEmail =  new PassEmail();
        errorFade = new FadeTransition(Duration.seconds(3),error_lbl);
    }

    private void onLogin(){
//
        if (acc_selector.getValue() == AccountType.EMPLOYEE) {
            passEmail.checkPassEmail(password_fld.getText(), email_address_fld.getText());
            int state = passEmail.getState();
            switch (state) {
                case 0:
                    setFadeText(errorFade,error_lbl,"Email or Password can not null !");
                    break;
                case 1:
                    Stage stage = (Stage) error_lbl.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showEmployeeWindow();
                    break;
                case 2:
                    setFadeText(errorFade,error_lbl,"Incorrect Password or Email !");
                    break;
            }
        }
        else {
            if (password_fld.getText().equals("admin")  && email_address_fld.getText().equals("admin")){
                Stage stage = (Stage) error_lbl.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
                Model.getInstance().getViewFactory().showManagerWindow();
            }
            else {

                setFadeText(errorFade,error_lbl,"Incorrect Password or Email !");
            }
        }
    }
    private void setFadeText(FadeTransition fadeTransition, Label label , String text){
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        label.setText(text);
        fadeTransition.setOnFinished(event->label.setVisible(false));
        fadeTransition.play();
        label.setVisible(true);
    }



}
