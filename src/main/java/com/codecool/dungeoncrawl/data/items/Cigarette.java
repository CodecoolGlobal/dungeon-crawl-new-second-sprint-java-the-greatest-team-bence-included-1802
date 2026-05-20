package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Cell;

public class Cigarette extends Item{
    public Cigarette(Cell cell) {
        super(cell);
    }

    @Override
    public String getDisplayName() {
        return "Cigarette";
    }

    @Override
    public String getTileName() {
        return "cigarette";
    }
}
