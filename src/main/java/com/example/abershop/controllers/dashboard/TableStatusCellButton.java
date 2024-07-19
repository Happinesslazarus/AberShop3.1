package com.example.abershop.controllers.dashboard;

import com.example.abershop.core.Repair;
import com.example.abershop.core.RepairManager;
import com.example.abershop.core.Status;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TableStatusCellButton extends TableCell<Repair, Void> {
    private final Button button;
    private Repair repair;
    private List<Repair> repairs;

    public TableStatusCellButton(DashboardController controller) {
        button = new Button();
        RepairManager manager = RepairManager.getInstance();
        repairs = controller.repairs;

        button.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure the repair is complete?");
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                setButtonState(Status.COMPLETE);
                repair.setStatus(Status.COMPLETE);
                repair.setRepairDate(LocalDate.now());

                // Ensure persistent update
                manager.updateRepair(repair); // Assuming updateRepair handles a single repair object
                // Ensure repairs list is also updated
                manager.updateRepairs(repairs);

                controller.refresh();
            } else {
                alert.close();
            }
        });
    }

    @Override
    protected void updateItem(Void unused, boolean empty) {
        super.updateItem(unused, empty);
        if (empty) {
            setGraphic(null);
        } else {
            repair = getTableView().getItems().get(getIndex());
            setButtonState(repair.getStatus());
            setGraphic(button);
        }
    }

    private void setButtonState(Status status) {
        if (Status.COMPLETE == status) {
            button.setText("Complete");
            button.setStyle("-fx-background-color: Green; -fx-text-fill: #FFFFFF;");
            button.setDisable(true);
        } else {
            button.setText("Progress");
            button.setStyle("-fx-background-color: Red; -fx-text-fill: #FFFFFF;");
            button.setDisable(false);
        }
    }
}
