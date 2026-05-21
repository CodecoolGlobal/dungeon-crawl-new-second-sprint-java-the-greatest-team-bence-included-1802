package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.items.Cigarette;
import com.codecool.dungeoncrawl.data.items.ShopItem;
import com.codecool.dungeoncrawl.data.items.SuccessfulPA;
import com.codecool.dungeoncrawl.data.items.Sword;

import java.util.ArrayList;
import java.util.List;

public class Merchant extends Actor{


    private List<ShopItem> wares;

    public Merchant(Cell cell) {
        super(cell, 1000000, 0);
        wares = new ArrayList<>();
        wares.add(new ShopItem(new Cigarette(null), 1));
        wares.add(new ShopItem(new SuccessfulPA(null), 3));
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
