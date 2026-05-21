package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Cell;

public class SuccessfulPA extends Item{
    public SuccessfulPA(Cell cell) {
        super(cell);
    }

    @Override
    public String getDisplayName() {
        return "Successful PA";
    }

    @Override
    public String getTileName() {
        return "Successful PA";
    }
}
