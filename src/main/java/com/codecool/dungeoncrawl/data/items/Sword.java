package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Cell;

public class Sword extends Item {

    public Sword(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
       return "sword";
    }

    @Override
    public String getDisplayName() {
        return "Sword";
    }
}
