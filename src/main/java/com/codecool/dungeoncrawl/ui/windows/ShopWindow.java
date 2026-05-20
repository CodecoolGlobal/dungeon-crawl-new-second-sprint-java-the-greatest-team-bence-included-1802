package com.codecool.dungeoncrawl.ui.windows;

import com.codecool.dungeoncrawl.data.items.ShopItem;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ShopWindow {

    private Stage stage;
    private List<ShopItem> shopItemList;

    public ShopWindow(List<ShopItem> shopItemList) {

        stage = new Stage();
        this.shopItemList = shopItemList;

        VBox root = new VBox();
        Label title = new Label("Szia Uram! parfüm érdekel?");
        root.getChildren().add(title);

        for (ShopItem item : shopItemList) {
            HBox itemRow = new HBox();

            Label itemLabel = new Label(
                    item.getItem().getDisplayName() + " ");

            Label priceLabel = new Label(
                    item.getPrice() + " gold "
            );

            Button buyButton = new Button("buy");

            itemRow.getChildren().addAll(itemLabel, priceLabel, buyButton);
            root.getChildren().add(itemRow);
        }


        Button closeButton = new Button("Kösz bástya!");
        closeButton.setOnAction(e -> stage.close());
        root.getChildren().add(closeButton);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }
}
