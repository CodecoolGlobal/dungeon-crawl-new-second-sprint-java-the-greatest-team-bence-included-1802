package com.codecool.dungeoncrawl.ui.elements;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

public class MainStage {
    private Canvas canvas;
    private Scene scene;
    private StatusPanel statusPanel;

    public MainStage(Canvas canvas) {
        this.canvas = canvas;
        statusPanel = new StatusPanel();
        scene = setUpScene();
    }

    private Scene setUpScene() {
        BorderPane borderPane = statusPanel.build();
        borderPane.setCenter(canvas);
        Scene scene = new Scene(borderPane);
        return scene;
    }

    public Scene getScene() {
        return scene;
    }

    public void setHealthLabelText(String text) {
        this.statusPanel.setHealthValue(text);
    }

    public void setInventoryLabelText(String text) {
        this.statusPanel.setInventoryLabel(text);
    }

    public void setAttackPowerLabel(String text) {
        this.statusPanel.setAttackPowerLabel(text);
    }

    public void setGoldLabelText(String text) {
        this.statusPanel.setGoldLabel(text);
    }


}
