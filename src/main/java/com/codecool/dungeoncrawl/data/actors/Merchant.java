package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.items.ShopItem;
import com.codecool.dungeoncrawl.data.items.Sword;

import java.util.ArrayList;
import java.util.List;

public class Merchant extends Actor{


    private List<ShopItem> wares;

    public Merchant(Cell cell) {
        super(cell, 1000000, 0);
        wares = new ArrayList<>();
        wares.add(new ShopItem(new Sword(null), 50));
        wares.add(new ShopItem(new Sword(null), 100));
        wares.add(new ShopItem(new Sword(null), 300));
    }

    @Override
    public void move(int dx, int dy) {

    }

    @Override
    public String getTileName() {
        return "merchant";
    }

    public List<ShopItem> getWares() {
        return List.copyOf(wares);
    }
}
