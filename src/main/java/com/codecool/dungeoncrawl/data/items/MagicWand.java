package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Cell;

public class MagicWand extends Item{
    public MagicWand(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "magicWand";
    }

    public String getDisplayName() {
        return "Magic wand";
    }
}
