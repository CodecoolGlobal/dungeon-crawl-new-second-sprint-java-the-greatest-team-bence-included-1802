package com.codecool.dungeoncrawl.ui.windows;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShopWindow {

    private Stage stage;

    public ShopWindow() {

        stage = new Stage();
        VBox root = new VBox();
        Label title = new Label("Szia Uram! parfüm érdekel?");
        Button closeButton = new Button("Kösz bástya!");
        closeButton.setOnAction(e -> stage.close());
        root.getChildren().addAll(title, closeButton);
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }
}
