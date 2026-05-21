package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Cell;

public class Gold extends Item{
    public Gold(Cell cell) {
        super(cell);
    }

    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public String getTileName() {
        return "gold";
    }
}
