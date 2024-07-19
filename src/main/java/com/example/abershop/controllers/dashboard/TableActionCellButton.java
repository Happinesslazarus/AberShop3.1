package com.example.abershop.controllers.dashboard;

import com.example.abershop.controllers.EditRepairController;
import com.example.abershop.controllers.ViewDetailController;
import com.example.abershop.core.Repair;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TableActionCellButton extends TableCell<Repair, Void> {

    private final Button delete;
    private final Button edit;
    private final Button view;
    private final HBox hBox;
    private Repair repair;
    private List<Repair> repairs;

    public TableActionCellButton(DashboardController dashboardController) {
        Stage stage = new Stage();
        hBox = new HBox(5);
        view = makeIconButton("/Images/viewButton.png");
        view.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ViewDetail.fxml"));
            Scene scene;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ViewDetailController controller = loader.getController();
            controller.paneId.setVisible(false);
            controller.viewRepairId.setVisible(false);
            controller.deleteBtn.setVisible(false);
            controller.repairId = repair.getRepairId();
            controller.searchRepair(event);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        });

        edit = makeIconButton("/Images/editButton.png");
        edit.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/EditRepair.fxml"));
            Scene scene;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            EditRepairController controller = loader.getController();
            controller.hboxId.setVisible(false);
            controller.repairId = repair.getRepairId();
            controller.repair = repair;
            controller.repairs = repairs;
            controller.setCallback(result -> {
                if (result) {
                    stage.close();
                    dashboardController.refresh();
                }
            });
            controller.loadRepairData(repair); // New method to load data into the edit scene
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        });

        delete = makeIconButton("/Images/deleteButton.png");
        delete.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ViewDetail.fxml"));
            Scene scene;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ViewDetailController controller = loader.getController();
            controller.repairId = repair.getRepairId();
            controller.setCallback(result -> {
                if (result) {
                    dashboardController.deleteRefresh();
                }
            });
            controller.deleteRepair(event);
        });

        hBox.getChildren().addAll(view, edit, delete);
    }

    @Override
    protected void updateItem(Void item, boolean b) {
        super.updateItem(item, b);
        if (b) {
            setGraphic(null);
        } else {
            repair = getTableView().getItems().get(getIndex());
            repairs = getTableView().getItems();
            setGraphic(hBox);
        }
    }

    private Button makeIconButton(String filePath) {
        Button button = new Button();
        Image icon = new Image(getClass().getResourceAsStream(filePath));
        ImageView imageView = new ImageView(icon);
        imageView.setFitHeight(18);
        imageView.setFitWidth(18);
        button.setGraphic(imageView);
        button.setStyle("-fx-background-radius: 10px; -fx-background-margin: 10px");
        return button;
    }
}
