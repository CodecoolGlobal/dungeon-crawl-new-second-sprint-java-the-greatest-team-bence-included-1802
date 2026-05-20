package com.codecool.dungeoncrawl.data.items;

public class ShopItem {

    private final Item item;
    private final int price;

    public ShopItem(Item item, int price) {
        this.item = item;
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }
}
