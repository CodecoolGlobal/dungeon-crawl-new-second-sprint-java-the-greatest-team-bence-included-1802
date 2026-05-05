package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Cell;

public class Health extends Item {

    public Health(Cell cell) {
        super(cell);
    }

    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public String getTileName() {
        return "health";
    }


}
