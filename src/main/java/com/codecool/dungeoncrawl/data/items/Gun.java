package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Cell;

public class Gun extends Item{

    public Gun(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "gun";
    }

    @Override
    public String getDisplayName() {
        return "Gun";
    }
}
